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
}


