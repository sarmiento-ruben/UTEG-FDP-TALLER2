package flight.TicketSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class TicketSchema {
    String ticketNum;
    String nid;
    String firstName;
    String lastName;
    String type;
    float price;
    int numCases;
    LocalDateTime date;
    float[] casesWeight;
    
    public TicketSchema(String ticketNum, String nid, String firstName, String lastName, String type, float price, int numCases, float[] casesWeight){
        this.ticketNum = ticketNum;
        this.nid = nid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.price = price;
        this.numCases = numCases;
        this.casesWeight = casesWeight;
        this.date = LocalDateTime.now();
    }

    public void showTicket() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("---- TICKET DE VUELO ----");
        System.out.println("Ticket ID: " + ticketNum);
        System.out.println("NID: " + nid);
        System.out.println("Nombre: " + firstName + " " + lastName);
        System.out.println("Tipo: " + type);
        System.out.println("Precio: $" + price);
        System.out.println("Fecha de compra: " + date.toLocalDate().format(dateFormatter));
        System.out.println("Hora de compra: " + date.toLocalTime().format(timeFormatter));
        System.out.println("Número de maletas: " + numCases);
        System.out.print("Pesos de maletas: ");
        System.out.println(Arrays.toString(this.casesWeight));   
        System.out.println("-------------------------");
    }

    public LocalDateTime getBuyDate(){
        return date;
    }

    public float getPrice(){
        return price;
    }
}
