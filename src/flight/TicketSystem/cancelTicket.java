package flight.TicketSystem;

import java.util.Scanner;

import flight.Utils;
import flight.config.FlightConfigSchema;

public class cancelTicket {
    public static void cancel(Scanner sc, FlightConfigSchema flight_config){
        TicketSchema ticket;
        String ticketId;
        float ticketDevolution;
        float ticketPrice;

        ticketId = Utils.askForTicketId(sc, flight_config);

        ticket = flight_config.getTicket(ticketId);
        if (ticket == null){
            return;
        }

        

        ticketPrice = ticket.getPrice();
        
        if (Utils.hasDeadlinePassed(ticket.getBuyDate(), flight_config.getMaxTime())){
            ticketDevolution = ticketPrice - (ticketPrice * 0.20f);
            System.out.println("Ticket cancelado con multa del 20%, valor a devolver $" + ticketDevolution);
        } else {
            System.out.println("Ticket cancelado sin multa, valor a devolver $" + ticketPrice);
        }
        flight_config.removePassenger(ticketId);
        System.out.println("Ticket cancelado correctamente.");
    }
}
