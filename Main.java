package mm1;

import mm1.Client.Client_Class;
import mm1.Event.Event_type;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Main {
    
	public static int queue_size;

    public enum Server_state {busy, idle} ;
    
    public static LinkedList<Client> clients_queue ;

    public static PriorityQueue<Event> events_queue ;

    public static int  service_time, arrival_time;
    public static float Tnow;
    public static float mean_waiting_time,mean_response_time ;
    public static long clients_served;
    public static long new_client_id;
    public static float number_of_clients;

    public static Server_state server;

    public static  void init() {
        
        server = Server_state.idle;
        
        clients_queue = new LinkedList<Client>();
        queue_size = 0;
        
        
        events_queue= new PriorityQueue<Event>(100, new EventComparator() );
        
        Tnow = 0;
        service_time = 5; // chosen arbitrarily
        arrival_time = 6; // chosen arbitrarily
        number_of_clients = 10000000; // chosen arbitrarily
        
        mean_response_time = 0;
        mean_waiting_time = 0;
        clients_served = 0;
        new_client_id = 0;
        

        Create_Event( Event_type.arrival, Tnow);
    }

    public static void main( String[] args){
        init();
        
        Event event;
        Client client = null;
        
        while (clients_served < number_of_clients) {

            event = events_queue.remove();
            Tnow = event.time;

            switch (event.type) {
                case arrival: 
                    process_arrival(); 
                break;
                case start_service: 
                     client = process_start_service(); 
                break;
                case departure: 
                    process_departure(client); 
                break;
            }

            delete_event(event);

        }

        print_statistics();
    }

    public static void process_arrival() {
        Client client = new Client( 0, Client_Class.C1, Tnow, 0,service_time);
        
        clients_queue.addLast(client);	
    
        if (server == Server_state.idle)
            Create_Event( Event_type.start_service, Tnow);

        Create_Event( Event_type.arrival, Tnow + arrival_time);
    }
    
    public static Client process_start_service() {
        server = Server_state.busy;

        Client client = clients_queue.removeFirst();
        
        client.t_start_service = Tnow;
        
        Create_Event( Event_type.departure, Tnow+ client.service_time);
        return client;
    }
    
    public static void process_departure(Client client ) {
        server = Server_state.idle;
        mean_response_time += Tnow - client.t_arrival;
        mean_waiting_time += client.t_start_service - client.t_arrival;
        clients_served++;
        delete_client(client);
    
        if (clients_queue.size() > 0)
            Create_Event(Event_type.start_service, Tnow);
        
    }

    public static void Create_Event(Event_type type, float time){

        Event event = new Event(  type,  time);
        events_queue.add(event);
        

    }
    
    public static void delete_event(Event event ){
        event = null ;

    }
	public static void delete_client(Client client){
        client = null;

    }
    
    private static void print_statistics() {
        System.out.printf("number of clients served: %d\n", clients_served);
        System.out.printf("mean response time: %f\n", mean_response_time/clients_served);
        System.out.printf("mean waiting time: %f\n", mean_waiting_time/clients_served);
        System.out.printf("throughput: %f\n", (float)clients_served/Tnow);
    }

 
}
