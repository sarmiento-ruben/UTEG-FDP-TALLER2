package flight.config;

import java.util.Scanner;

import flight.CancelException;
import flight.InputHelper;

public class FlightConfigurer {

    public static FlightConfigSchema config(Scanner sc){
        try {
        System.out.println("Bienvenido a la configuración del vuelo, para cancelar la ejecución envíe \"Q\" ");
        // Available Seats
        String aSeatsMessage = "Escriba el número de asientos disponibles: ";
        String aSeatBlankMessage = "El número de asientos no puede estar vacío";
        String aSeatNoNumberMessage = "El valor debe ser numérico!";
        int aSeatMax = 200;
        String nCasesRangeErrorMessage = String.format("El valor debe estar entre, %d y %d", 0, aSeatMax);
        int availableSeats = InputHelper.askForInt(sc, aSeatsMessage, aSeatBlankMessage, aSeatNoNumberMessage, 0, aSeatMax, nCasesRangeErrorMessage);
        
        // Max Cases
        String mCasesMessage = "Escriba el número de maletas permitido: ";
        String mCasesBlankMessage = "El número máximo de maletas no puede estar vacío";
        String mCasesNoNumberMessage = "El valor debe ser numérico!";
        int mCasesMax = 1000;
        String mCasesRangeErrorMessage = String.format("El valor debe estar entre, %d y %d", 0, mCasesMax);
        int maxCases = InputHelper.askForInt(sc, mCasesMessage, mCasesBlankMessage, mCasesNoNumberMessage, 0, mCasesMax, mCasesRangeErrorMessage);
        
        // Max weight per case
        String mWeightMessage = "Escriba el peso máximo (EN KILOS) por maleta permitido: ";
        String mWeightBlankMessage = "El peso máximo por maleta no puede estar vacío";
        String mWeightNoNumberMessage = "El valor debe ser numérico!";
        float mWeightMax = 1000.00f;
        String mWeightRangeErrorMessage = String.format("El valor debe estar entre, %f y %f", 0.00, mWeightMax);
        float maxWeight = InputHelper.askForFloat(sc, mWeightMessage, mWeightBlankMessage, mWeightNoNumberMessage, 0, mWeightMax, mWeightRangeErrorMessage);
        
        // Max time in minutes before additional charges
        String mTimeMessage = "Escriba el tiempo máximo (EN MINUTOS) antes de realizar un cobro extra por cambios o cancelaciones: ";
        String mTimeBlankMessage = "El tiempo máximo no puede estar vacío";
        String mTimeNoNumberMessage = "El valor debe ser numérico!";
        float mTimeMax = 40320.00f;
        String mTimeRangeErrorMessage = String.format("El tiempo debe estar entre, %f minutos y %f minutos", 0.00, mTimeMax);
        float maxTime = InputHelper.askForFloat(sc, mTimeMessage, mTimeBlankMessage, mTimeNoNumberMessage, 0, mTimeMax, mTimeRangeErrorMessage);
        
        FlightConfigSchema out = new FlightConfigSchema(availableSeats, maxCases, maxWeight, maxTime);

        // National Price
        String nPriceMessage = "Escriba el precio en dolares para los boletos nacionales: ";
        String nPriceTimeBlankMessage = "El precio no puede estar vacío";
        String nPriceNoNumberMessage = "El valor debe ser numérico!";
        float nPriceMax = 50000.00f;
        String nPriceRangeErrorMessage = String.format("El precio debe estar entre, $%f y $%f", 0.00, nPriceMax);
        float nationalPrice = InputHelper.askForFloat(sc, nPriceMessage, nPriceTimeBlankMessage, nPriceNoNumberMessage, 0, nPriceMax, nPriceRangeErrorMessage);
        out.ticketTypeAdd("Nacional", nationalPrice);
        
        
        // International Price
        String inPriceMessage = "Escriba el precio en dolares para los boletos internacionales: ";
        String inPriceTimeBlankMessage = "El precio no puede estar vacío";
        String inPriceNoNumberMessage = "El valor debe ser numérico!";
        float inPriceMax = 100000.00f;
        String inPriceRangeErrorMessage = String.format("El precio debe estar entre, $%f y $%f", 0.00, inPriceMax);
        float internationalPrice = InputHelper.askForFloat(sc, inPriceMessage, inPriceTimeBlankMessage, inPriceNoNumberMessage, 0, inPriceMax, inPriceRangeErrorMessage);
        out.ticketTypeAdd("Internacional", internationalPrice);
        
        
        System.out.println("Configuración exitosa!");
        return  out;

        } catch (CancelException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
