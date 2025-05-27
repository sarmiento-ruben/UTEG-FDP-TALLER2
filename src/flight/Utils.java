package flight;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;

import flight.TicketSystem.BuyTicket;
import flight.TicketSystem.ModifyTicket;
import flight.TicketSystem.cancelTicket;
import flight.config.FlightConfigSchema;

public class Utils {

    private static void printLoop(){
        String systemTest = """
                === Menú del Sistema ===
                1. Comprar ticket
                2. Modificar Ticket
                3. Cancelar ticket
                4. Salir
                Elige una opción: """;
        System.out.print(systemTest);
    }

    public static void systemLoop(Scanner sc, FlightConfigSchema flight_config){
        Set<Integer> validOptions = Set.of(1,2,3,4);
        while(true){
            printLoop();

            String selectedOption = sc.nextLine();
            int selectedOpt;

            try {
                selectedOpt = Integer.parseInt(selectedOption);
            } catch (NumberFormatException e) {
                System.err.println("Ingresa una opción valida!");
                continue;
            }
            
            if (validOptions.contains(selectedOpt)){
                switch (selectedOpt) {
                    case 1:
                        BuyTicket.buy(sc, flight_config);
                        break;
                    case 2:
                        ModifyTicket.modifyTicket(sc, flight_config);
                        break;
                    case 3:
                        cancelTicket.cancel(sc, flight_config);;
                        break;
                    case 4:
                        System.out.println("Saliendo del sistema!");
                        return; 
                    default:
                        break;
                } 
            } else {
                System.err.println("Opción invalida!");
            }
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


