package flight;

import java.util.Scanner;

import flight.TicketSystem.TicketSchema;
import flight.TicketSystem.TicketUtils;
import flight.config.FlightConfigSchema;

public class InputHelper{
    
    public static String askForString(Scanner sc, String askMessage, String blankMessage){
        String input;
        System.out.print(askMessage);
        while (true) {
            
            input = sc.nextLine().trim();
            
            if (input.isBlank()){
                System.err.println(blankMessage);
                continue;
            }

            if (input.equalsIgnoreCase("Q")){
                throw new CancelException("Ejecución cancelada");
            }

            break;
        }
        return input;
    }

    public static Integer askForInt(Scanner sc, String askMessage, String blankMessage, String noNumberMessage, int minInt, int maxInt, String rangeErrorMessage){
        String input;
        int output;
        System.out.print(askMessage);
        while (true) {
            
            input = sc.nextLine().trim();
            
            if (input.isBlank()){
                System.err.println(blankMessage);
                continue;
            }

            if (input.equalsIgnoreCase("Q")){
                throw new CancelException("Ejecución cancelada");
            }

            try{
                output = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println(noNumberMessage);
                continue;
            }
            
            if ((output < minInt || output > maxInt)){
                System.err.println(rangeErrorMessage);
                continue;
            }
            break;
        }
        return output;
    }

    public static Float askForFloat(Scanner sc, String askMessage, String blankMessage, String noNumberMessage, float minFloat, float maxFloat,  String rangeErrorMessage){
        String input;
        float output;
        System.out.print(askMessage);
        while (true) {
            
            input = sc.nextLine().trim();
            
            if (input.isBlank()){
                System.err.println(blankMessage);
                continue;
            }

            if (input.equalsIgnoreCase("Q")){
                throw new CancelException("Ejecución cancelada");
            }

            try{
                output = Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.err.println(noNumberMessage);
                continue;
            }
            if ((output < minFloat || output > maxFloat)){
                System.err.println(rangeErrorMessage);
                continue;
            }
            break;
        }
        return output;
    }

}