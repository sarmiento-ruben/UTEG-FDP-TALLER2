import java.util.Scanner;

import flight.config.FlightConfigSchema;
import flight.config.FlightConfigurer;
import flight.Utils;
public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        FlightConfigSchema config_data = FlightConfigurer.config(sc);
        if (config_data == null){
            return;
        }
        config_data.showConfig();
        Utils.systemLoop(sc, config_data);
    }

}


