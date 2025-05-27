package flight.TicketSystem;

import java.util.Scanner;

import flight.CancelException;
import flight.InputHelper;
import flight.Utils;
import flight.config.FlightConfigSchema;

public class ModifyTicket {
    public static void modifyTicket(Scanner sc, FlightConfigSchema flight_config) {
        System.out.println("Sistema de modificación de vuelos, escriba \"Q\" para salir en cualquier momento");
        
        String ticketID = Utils.askForTicketId(sc, flight_config);
        TicketSchema ticket = flight_config.getTicket(ticketID);
        if (Utils.hasDeadlinePassed(ticket.getBuyDate(), flight_config.getMaxTime())) {
            System.out.println("Este ticket no se puede modificar, ha excedido el tiempo máximo de espera");
            return;
        }

        try {
            // Modificar Nombre
            String newFirstName = InputHelper.askForString(sc, "Ingrese nuevo primer nombre (actual: " + ticket.getFirstName() + "): ", null);
            if (!newFirstName.equalsIgnoreCase("Q") && !newFirstName.isBlank()) {
                ticket.setFirstName(newFirstName);
            }

            // Modificar Apellido
            String newLastName = InputHelper.askForString(sc, "Ingrese nuevo apellido (actual: " + ticket.getLastName() + "): ", null);
            if (!newLastName.equalsIgnoreCase("Q") && !newLastName.isBlank()) {
                ticket.setLastName(newLastName);
            }

            // Modificar Tipo de ticket y precio
            Float nationalPrice = flight_config.getTicketPrice("Nacional");
            Float internationalPrice = flight_config.getTicketPrice("Internacional");
            String tTypeMessage = String.format("""
                Tipos de vuelo 
                0. Nacional $%.2f
                1. Internacional $%.2f
                Ingrese nuevo tipo de ticket (actual: %s): """, nationalPrice, internationalPrice, ticket.getTicketType());
            int newType = InputHelper.askForInt(sc, tTypeMessage, null, "Ingrese un valor numérico", 0, 1, "Valor inválido");
            if (newType == 0) {
                ticket.setTicketType("Nacional");
                ticket.setTicketPrice(nationalPrice);
            } else if (newType == 1) {
                ticket.setTicketType("Internacional");
                ticket.setTicketPrice(internationalPrice);
            }

            // Modificar número de maletas
            int maxCases = flight_config.getMaxCases();
            String nCasesMessage = "Ingrese nuevo número de maletas (actual: " + ticket.getNumCases() + "): ";
            int newNumCases = InputHelper.askForInt(sc, nCasesMessage, null, "Ingrese un valor numérico", 0, maxCases, "Número fuera de rango");
            ticket.setNumCases(newNumCases);

            // Modificar peso de cada maleta
            float maxWeight = flight_config.getMaxWeight();
            System.out.println("Ingrese nuevo peso para cada maleta:");
            float[] newCasesWeight = new float[newNumCases];
            for (int i = 0; i < newNumCases; i++) {
                String msg = "Peso maleta #" + (i + 1) + " (máximo " + maxWeight + " kg): ";
                float w = InputHelper.askForFloat(sc, msg, null, "Ingrese un número válido", 0f, maxWeight, "Peso fuera de rango");
                newCasesWeight[i] = w;
            }
            ticket.setCasesWeight(newCasesWeight);

            System.out.println("Ticket modificado exitosamente:");
            ticket.showTicket();

        } catch (CancelException e) {
            System.out.println("Modificación cancelada por usuario.");
        }
    }
}