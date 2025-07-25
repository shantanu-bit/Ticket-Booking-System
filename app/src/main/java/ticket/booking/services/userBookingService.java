package ticket.booking.services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ticket.booking.Util.userServiceUtil;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class userBookingService {
    private User user;
    private Train train;
    private List<User> userList;
    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDb/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    public List<User> loadUsers() {
        try {
            File users = new File(USERS_PATH);
            return userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            userList = new ArrayList<>();
            return userList;
        }
    }

    public userBookingService() throws IOException{
        objectMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);// for pretty-print the JSON output
        loadUsers();
    }
    public userBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public Boolean loginUser(){
        Optional<User> founduser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && userServiceUtil.checkPass(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if (founduser.isPresent()) {
            this.user = founduser.get(); //  Set the actual logged in user
            return true;
        }
        return false;
    }

    public Boolean signUp(User user1)  {// add user object in userList and adding in JSON file
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch(IOException e){
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public  void fetchBookings(){
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) &&  userServiceUtil.checkPass(user.getPassword(), user1.getHashedPassword() );
        }).findFirst();
        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
    }

    public  List<List<Integer>> fetchSeats(Train train){ // prints 2D matrix as visual diagram of seats
        return train.getSeats();
    }

    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public Boolean bookTrainSeat(Train train, int row, int col) {
        try {
            List<List<Integer>> seat = train.getSeats();
            TrainService trainService = new TrainService();
            if (row < seat.size() && row >= 0 && col < seat.get(row).size() && col >= 0) {
                if (seat.get(row).get(col) == 0) {
                    seat.get(row).set(col, 1);
                    train.setSeats(seat);
                    trainService.updateSeat(train);

                    //adding ticket data as to show in bookedTickets
                    Ticket ticket = new Ticket();
                    ticket.setSource(train.getStations().getFirst());
                    ticket.setDestination(train.getStations().getLast());
                    ticket.setTrain(train);
                    ticket.setUserId(user.getUserId());
                    ticket.setDateOfTravel("2025-07-22");
                    ticket.setTicketId(userServiceUtil.generateTicketId());
                    ticket.setRow(row);
                    ticket.setCol(col);
                    user.getTicketsBooked().add(ticket);
                    System.out.println("Seat booked successfully...");
                    saveUserListToFile();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException ex) {
            return Boolean.FALSE;
        }
    }

    public Boolean cancelBooking(String ticketId) throws IOException {

        if(ticketId.equals(null) || ticketId.isEmpty()){
            System.out.println("Ticket id cannot be null or empty. ");
            return Boolean.FALSE;
        }

        Ticket ticketToCancel = null;
        for(Ticket t: user.getTicketsBooked()){
            if(t.getTicketId().equals(ticketId)){
                ticketToCancel = t;
            }
        }

        ticketToCancel.getTrain().getSeats().get(ticketToCancel.getRow()).set(ticketToCancel.getCol(), 0);

        boolean checkTicket = user.getTicketsBooked().removeIf(
                ticket1 -> ticket1.getTicketId().equalsIgnoreCase(ticketId)
        );

        if(checkTicket){
            saveUserListToFile();
            new TrainService().saveTrainListToFile(); // for saving seats data in json
            System.out.println("Ticket booking with Id: "+ticketId+" is canceled");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket is found with id: "+ticketId);
            return Boolean.FALSE;
        }
    }

}
