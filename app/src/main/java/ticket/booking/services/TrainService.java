package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private List<Train> trainList;
    private static final String TRAIN_PATH = "app/src/main/java/ticket/booking/localDb/trains.json";
    ObjectMapper objectMapper = new ObjectMapper();

    public void loadTrains() throws IOException {// reads values of JSON into trainList to process data
        File trains = new File(TRAIN_PATH);
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public TrainService() throws IOException {
        objectMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT); // for pretty-print the JSON data
        loadTrains();
    }

    public List<Train> searchTrains(String source, String destination){
        if (trainList == null) return List.of();
        return trainList.stream().filter(
                train -> validTrain(train, source, destination)).collect(Collectors.toList()
        );
    }

    // We will check the stations in order by creating a list of stations that is sorted in order of arrival
    // then we will check if the source station is coming before the destination station or not
    public Boolean validTrain(Train train, String source, String destination){
        List<String> stationOrder = train.getStations();
        int sourceIndx = stationOrder.indexOf(source.toLowerCase());
        int destinationIndx = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndx != -1 && destinationIndx != -1 && sourceIndx < destinationIndx;
    }

    public void updateSeat(Train train){
        for (int i = 0; i < trainList.size(); i++) {
            if (trainList.get(i).getTrainId().equals(train.getTrainId())) {
                trainList.set(i, train); // Directly update the matched train
                saveTrainListToFile();   // Save after update
                return;
            }
        }
        System.out.println("Train not found. Cannot update seat.");
    }

    public void saveTrainListToFile(){
        try{
            objectMapper.writeValue(new File(TRAIN_PATH), trainList);
        }catch (IOException ex){
            return;
        }
    }






}
