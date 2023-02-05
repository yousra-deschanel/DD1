package mm1;

public class Event {
    enum Event_type {arrival, start_service, departure} ;

    public  Event_type type; 
    public  float time;

    public Event(Event_type type, float time){  
        this.type= type; 
        this.time = time;
    }





}
