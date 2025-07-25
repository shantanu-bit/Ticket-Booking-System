package ticket.booking.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // We use this because we have used different Naming convetions in JSON and in java code.
public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String Destination;
    private String dateOfTravel;
    private Train train;
    private int row;
    private int col;


    public String getTicketId() {
        return ticketId;
    }
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public int getRow(){
        return row;
    }
    public void setRow(int row){
        this.row = row;
    }

    public int getCol(){
        return col;
    }
    public void setCol(int column){
        this.col = column;
    }


    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return Destination;
    }
    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDateOfTravel() {
        return dateOfTravel;
    }
    public void setDateOfTravel(String dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain() {
        return train;
    }
    public void setTrain(Train train) {
        this.train = train;
    }

    public Ticket() {} // default constructor

    public String getTicketInfo(){ // used in fetchBookings functionality
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, Destination, dateOfTravel);
    }
}
