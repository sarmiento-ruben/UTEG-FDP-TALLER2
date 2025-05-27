package flight.config;

import java.util.HashMap;
import java.util.Map;

import flight.TicketSystem.TicketSchema;

public class FlightConfigSchema{
    int availableSeats;
    int remainingSeats;
    float maxTime;
    int maxCases;
    float maxWeight;
    Map<String, TicketSchema> passengers;
    Map<String, Float> ticketTypes = new HashMap<>();

    public FlightConfigSchema(int availableSeats, int maxCases, float maxWeight, float maxTime){
        this.availableSeats = availableSeats;
        this.maxCases = maxCases;
        this.maxWeight = maxWeight;
        this.maxTime = maxTime;
        this.remainingSeats = availableSeats;
        this.passengers = new HashMap<>();

    }
    
    public void showConfig(){
        System.out.println("Asientos disponibles: " + availableSeats + " asientos");
        System.out.println("Tiempo máximo antes de recargo adicional: " + maxTime + " minutos");
        System.out.println("Máximo número de maletas por pasajero: " + maxCases + " maletas");
        System.out.println("Máximo peso por maleta: " + maxWeight + " kg");
        System.out.println("Precio tickets nacionales: $" + getTicketPrice("Nacional"));
        System.out.println("Precio tickets internacionales: $" + getTicketPrice("Internacional"));
    }

    public void ticketTypeAdd(String type, Float price){
        ticketTypes.put(type, price);
    }

    public Float getTicketPrice(String Type){
        return ticketTypes.get(Type);
    }

    public void subtractSeat(){
        if (remainingSeats > 0){
            remainingSeats -= 1;
        }
    }

    public int getRemainingSeats(){
        return remainingSeats;
    }

    public int getMaxCases(){
        return maxCases;
    }

    public float getMaxWeight(){
        return maxWeight;
    }

    public float getMaxTime(){
        return maxTime;
    }

    public void addPasseger(String ticketId, TicketSchema ticketInfo){
        passengers.put(ticketId, ticketInfo);
        subtractSeat();
        System.out.println("Registrado correctamente, por favor guardé el ID de su vuelvo, será requerido para cambios o cancelaciones");
        ticketInfo.showTicket();
    }

    public void removePassenger(String ticketId){
        passengers.remove(ticketId);
        remainingSeats += 1;
    }

    public TicketSchema getTicket(String ticketId){
        return passengers.get(ticketId);
    }


    public Boolean existsPassenger(String ticketId){
        return passengers.containsKey(ticketId);
    }
    
}