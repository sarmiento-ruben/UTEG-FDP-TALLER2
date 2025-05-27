package flight.TicketSystem;

import java.util.Scanner;

import flight.config.FlightConfigSchema;
import flight.CancelException;
import flight.InputHelper;

public class BuyTicket {


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
        String ticketId = TicketUtils.generateTicketId();

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
        String ticketType = TicketUtils.getType(indexTicketType);
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
        float[] casesWeight = TicketUtils.askForCasesWeight(sc, numCases, 0, maxWeight, mWeightRangeErrorMessage);
        

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
