package flight.TicketSystem;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import flight.config.FlightConfigSchema;
import flight.CancelException;
import flight.InputHelper;

public class BuyTicket {

    // Function to ask for the weight of each case
    private static float[] askForCasesWeight(Scanner sc, int casesNum, float minWeight, float maxWeight, String rangeErrorMessage) {
        String input;
        float weight;
        float[] casesWeight = new float[casesNum];
        int i = 0;
        System.out.println("Ingrese el peso de cada maleta");

        System.out.print("Ingrese el peso de la maleta #" + (i + 1) + ":");
        while (i < casesNum){
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
    private static String generateTicketId(){
        // Variable to build ID
        StringBuilder id = new StringBuilder();
        
        for (int i = 0; i < 15; i++){
            int digit = ThreadLocalRandom.current().nextInt(10);
            id.append(digit);
        }
        return id.toString();
    }

    private static String getType(int type){
        if (type == 0){
            return "Nacional";
        } else {
            return "Internacional";            
        }
    }

    //  Function to process ticket buy
    public static TicketSchema buy(Scanner sc, FlightConfigSchema flight_config){
        /*
         *  Función para procesar las compras de tickets
         * @param sc: Objeto tipo Scanner
         * @param flight_config: Objeto que contiene la configuración del vuelo
         */
        int availableSeats = flight_config.getRemainingSeats();
        if (availableSeats <= 0){
            System.out.println("Todos los vuelos han sido vendidos!");
            return null; 
        } else {
            System.out.println("Quedan " + availableSeats + " asientos restantes" );
        }

        try {
        System.out.println("Bienvenido al servicio de compra, para cancelar solo envíe \"Q\"");

        // Ticket ID
        String ticketId = generateTicketId();

        // National Identifier Document
        String nidMessage = "Ingrese su número de identificación: ";
        String nidBlankMessage = "Su identificación no puede estar vacía";
        String nid =  InputHelper.askForString(sc, nidMessage, nidBlankMessage);

        // First Name
        String fNameMessage = "Ingrese su primer nombre: ";
        String fNameBlankMessage = "Su nombre no puede estar vacío";
        String firstName = InputHelper.askForString(sc, fNameMessage, fNameBlankMessage);
        
        // Last Name
        String lNameMessage = "Ingrese su apellido: ";
        String lNameBlankMessage = "Su apellido no puede estar vacío";
        String lastName =  InputHelper.askForString(sc, lNameMessage, lNameBlankMessage);
        
        // Ticket Type && Price
        Float nationalPrice = flight_config.getTicketPrice("Nacional");
        Float internationalPrice = flight_config.getTicketPrice("Internacional");
        String tTypeMessage = String.format("""
            Tipos de vuelvo 
            0. Nacional $%.2f
            1. Internacional $%.2f
            Escriba el número del tipo de ticket: """, nationalPrice, internationalPrice);
        String tTypeBlankMessage = "El tipo de ticket no puede estar vacío";
        String tTypeNoNumberMessage = "Ingrese un valor numérico";
        String tTypeRangeErrorMessage = String.format("El valor debe estar entre, %d y %d", 0, 1);
        int indexTicketType = InputHelper.askForInt(sc, tTypeMessage, tTypeBlankMessage, tTypeNoNumberMessage, 0, 1, tTypeRangeErrorMessage);
        String ticketType = getType(indexTicketType);
        Float ticketPrice = flight_config.getTicketPrice(ticketType);
        
        
        // Num Cases
        int maxCases = flight_config.getMaxCases();
        String  nCasesMessage = "Ingrese el número de maletas que lleva: ";
        String  nCasesBlankMessage = "EL número de maletas no puede estar vacío";
        String  nCasesNoNumberMessage = "Ingrese un valor numérico!";
        String nCasesRangeErrorMessage = String.format("El valor debe estar entre, %d y %d", 0, maxCases);
        int numCases = InputHelper.askForInt(sc, nCasesMessage, nCasesBlankMessage, nCasesNoNumberMessage, 0, maxCases, nCasesRangeErrorMessage);
        
        // CasesWeight
        float maxWeight = flight_config.getMaxWeight();
        String mWeightRangeErrorMessage = String.format("El valor debe estar entre, %.2f y %.2f", 0.0, maxWeight);
        float[] casesWeight = askForCasesWeight(sc, numCases, 0, maxWeight, mWeightRangeErrorMessage);
        

        // Ticket generation
        TicketSchema ticket = new TicketSchema(ticketId, nid, firstName, lastName, ticketType, ticketPrice, numCases, casesWeight);

        // Save ticket
        flight_config.addPasseger(ticketId, ticket);
        return ticket;

        } catch (CancelException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
}
