package mm1;

public class Client {

	    public enum Client_Class {C1, C2, C3} ;

	    public int id; 
	    public Client_Class class_type ;
	    public Float t_arrival; 
	    public Float t_start_service; 
	    public int service_time;


	    
	    public Client(int id, Client_Class c1, float t_arrival, float i, int service_time) {
			this.id = id;
			this.class_type = c1;
			this.t_arrival = t_arrival;
			this.t_start_service = i;
			this.service_time = service_time;
		}


	    
	


}
