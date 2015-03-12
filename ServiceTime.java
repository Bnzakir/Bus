public class ServiceTime
{
	
   private int route;
   private int timingPoint;
   private int time;
   private int serviceNumber;
   
	public ServiceTime(int requiredRoute, int requiredTimingPoint, int requiredTime, int requiredServiceNumber)
	{
		route = requiredRoute;
		timingPoint = requiredTimingPoint;
		time = requiredTime; 
		serviceNumber = requiredServiceNumber;    
	}

	public String toString()
	{

  		return "Route: " + route + " Service Number: " + serviceNumber + " Timing point: " + timingPoint + " time: " + time; 

	} // toString

	public int getTime()
	{
		return time;
	}

	public int getRoute()
	{
		return route;
	}

	public int getService()
	{
		return serviceNumber;
	}

	public int getStop()
	{
		return timingPoint;
	}
}	 