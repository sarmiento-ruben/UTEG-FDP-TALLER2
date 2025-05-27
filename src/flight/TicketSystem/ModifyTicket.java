package flight.TicketSystem;

import java.util.Scanner;

import flight.CancelException;
import flight.InputHelper;
import flight.config.FlightConfigSchema;

public class ModifyTicket {


    public static void modifyTicket(Scanner sc, FlightConfigSchema flight_config) {
        System.out.println("Sistema de modificación de vuelos, escriba \"Q\" para salir en cualquier momento");
        
        String ticketID = TicketUtils.askForTicketId(sc, flight_config);
        TicketSchema ticket = flight_config.getTicket(ticketID);
        
        if (ticket == null){
            return;
        }

        if (TicketUtils.hasDeadlinePassed(ticket.getBuyDate(), flight_config.getMaxTime())) {
            System.out.println("Este ticket no se puede modificar, ha excedido el tiempo máximo de espera");
            return;
        }
        
        try {
            
        System.out.println("Presione enter/envíe el texto vacío si quiere dejar el valor antiguo.");
        // National Identifier Document
        String nidMessage = String.format("Ingrese su número de identificación (Anterior: %s ): ", ticket.getNid());
        String nidBlankMessage = "Su identificación no puede estar vacía";
        String nid =  InputHelper.askForString(sc, nidMessage, nidBlankMessage);

        // First Name
        String fNameMessage =  String.format("Ingrese su primer nombre (Anterior: %s): ", ticket.getFirstName());
        String fNameBlankMessage = "Su nombre no puede estar vacío";
        String firstName = InputHelper.askForString(sc, fNameMessage, fNameBlankMessage);
        
        // Last Name
        String lNameMessage =  String.format("Ingrese su apellido (Anterior: %s): ", ticket.getLastName());
        String lNameBlankMessage = "Su apellido no puede estar vacío";
        String lastName =  InputHelper.askForString(sc, lNameMessage, lNameBlankMessage);
        
        // Ticket Type && Price
        float nationalPrice = flight_config.getTicketPrice("Nacional");
        float internationalPrice = flight_config.getTicketPrice("Internacional");
        String tTypeMessage = String.format("""
            Tipos de vuelvo 
            0. Nacional $%.2f
            1. Internacional $%.2f
            Escriba el número del tipo de ticket (Anterior: %s): """, nationalPrice, internationalPrice, ticket.getType());
        String tTypeBlankMessage = "El tipo de ticket no puede estar vacío";
        String tTypeNoNumberMessage = "Ingrese un valor numérico";
        String tTypeRangeErrorMessage = String.format("El valor debe estar entre, %d y %d", 0, 1);
        int indexTicketType = InputHelper.askForInt(sc, tTypeMessage, tTypeBlankMessage, tTypeNoNumberMessage, 0, 1, tTypeRangeErrorMessage);
        String ticketType = TicketUtils.getType(indexTicketType);
        Float ticketPrice = flight_config.getTicketPrice(ticketType);

        
        // Num Cases
        int maxCases = flight_config.getMaxCases();
        String  nCasesMessage =  String.format("Ingrese el número de maletas que lleva (Anterior: %d): ", ticket.getNumCases());
        String  nCasesBlankMessage = "EL número de maletas no puede estar vacío";
        String  nCasesNoNumberMessage = "Ingrese un valor numérico!";
        String nCasesRangeErrorMessage = String.format("El valor debe estar entre, %d y %d", 0, maxCases);
        int numCases = InputHelper.askForInt(sc, nCasesMessage, nCasesBlankMessage, nCasesNoNumberMessage, 0, maxCases, nCasesRangeErrorMessage);
        
        // CasesWeight
        float maxWeight = flight_config.getMaxWeight();
        String mWeightRangeErrorMessage = String.format("El valor debe estar entre, %.2f y %.2f", 0.0, maxWeight);
        float[] casesWeight = TicketUtils.askForCasesWeight(sc, numCases, 0, maxWeight, mWeightRangeErrorMessage);
        
        ticket.setNid(nid);
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setType(ticketType);
        ticket.setPrice(ticketPrice);
        ticket.setNumCases(numCases);
        ticket.setCasesWeight(casesWeight);

        System.out.println("Ticket modificado exitosamente:");
        ticket.showTicket();

        } catch (CancelException e) {
            System.out.println("Modificación cancelada por usuario.");
        }
    }
}