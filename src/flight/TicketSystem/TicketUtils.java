package flight.TicketSystem;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import flight.CancelException;
import flight.InputHelper;
import flight.config.FlightConfigSchema;

public class TicketUtils {
    // Function to ask for the weight of each case
    public static float[] askForCasesWeight(Scanner sc, int casesNum, float minWeight, float maxWeight, String rangeErrorMessage) {
        String input;
        float weight;
        float[] casesWeight = new float[casesNum];
        int i = 0;
        System.out.println("Ingrese el peso de cada maleta (EN KILOS)");

        while (i < casesNum){
            System.out.print("Ingrese el peso de la maleta (kg) #" + (i + 1) + ":");
            input = sc.nextLine().trim();

            if (input.isBlank()){
                System.err.println("El valor no puede ser un valor vacío (maleta " +  (i + 1) + ")");
                continue;
            }

            if (input.equalsIgnoreCase("Q")) {
                throw new CancelException("Ejecución finalizada");
            }

            try {
                weight = Float.parseFloat(input);
            } catch (NumberFormatException e){
                System.err.println("Ingrese un valor valido (maleta " +  (i + 1) + ")");
                continue;
            }
                
            if (weight < minWeight || weight > maxWeight){
                System.err.println(rangeErrorMessage);
                continue;
            }

            casesWeight[i] = weight;
            ++i;
        }
        return casesWeight;
    }

    // Function to generate ticket ID
    public static String generateTicketId(){
        // Variable to build ID
        StringBuilder id = new StringBuilder();
        
        for (int i = 0; i < 15; i++){
            int digit = ThreadLocalRandom.current().nextInt(10);
            id.append(digit);
        }
        return id.toString();
    }

    public static String getType(int type){
        if (type == 0){
            return "Nacional";
        } else {
            return "Internacional";            
        }
    }

    public static boolean hasDeadlinePassed(LocalDateTime buyDate, float maxTime){
        int maxMinutes = (int) maxTime;
        int maxSeconds = (int) ((maxTime - maxMinutes) * 60);
        
        LocalDateTime deadline = buyDate.plusMinutes(maxMinutes).plusSeconds(maxSeconds);
        
        return deadline.isBefore(LocalDateTime.now()); 
    }

    public static String askForTicketId(Scanner sc, FlightConfigSchema flight_config){
        String ticketId;
        try{
            // Ticket id
            String tIdMessage = "Escriba el id de su ticket: ";
            String tIdBlankMessage = "El id de su ticket no puede se un espacio en blanco";
            
            ticketId = InputHelper.askForString(sc, tIdMessage, tIdBlankMessage);

            if (!flight_config.existsPassenger(ticketId)){
                System.out.println("El ticket no existe, intente nuevamente!");
                return null;
            }        
            } catch (CancelException e){
            System.err.println(e.getMessage());
            return null;
        }
        return ticketId;
    }

}
