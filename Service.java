public class Service
{
	private ServiceTime service[];

	private int duration_in_minutes;
	private int start_time_in_minutes;
	private int end_time_in_minutes;

	private int service_number;
	private Driver driver;
	private int bus_id;

	private int route;

	Service(int size, ServiceTime[] data, int requiredRoute)
	{
		service = new ServiceTime[size];

		for(int i=0; i < size; i++)
			service[i] = data[i];

		route = requiredRoute;
		service_number = service[0].getService();
		start_time_in_minutes = service[0].getTime();
		end_time_in_minutes = service[size - 1].getTime();

		if(end_time_in_minutes < start_time_in_minutes)
			duration_in_minutes = 1440 - start_time_in_minutes + end_time_in_minutes;
		else
			duration_in_minutes = end_time_in_minutes - start_time_in_minutes;
	}


	public void setDriver(Driver requiredDriver)
	{
		driver = requiredDriver;
	}

	public int getServiceNumber()
	{
		return service_number;
	}

	public int getStart()
	{
		return start_time_in_minutes;
	}

	public int getEnd()
	{
		return end_time_in_minutes;
	}

	public int getDuration()
	{
		return duration_in_minutes;
	}

	public Driver getDriver()
	{
		return driver;
	}

	public void setBus(int id)
	{
		bus_id = id;
	}

	public int getBus()
	{
		return bus_id;
	}

	public int getFinalStop()
	{
		int index = service.length - 1;
		return service[index].getStop();
	}

	public int getRoute()
	{
		return route;
	}

	//public String toString()
	//{

  		//return service;

	//} // toString
}
