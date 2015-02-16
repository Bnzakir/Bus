import java.util.Date;

public class RequestHoliday 
{

    public static void main(String[] args) 
    {
    
      database.openBusDatabase();
    }
    
    
    /**
	 	* Computes the length in number of days of a holiday between two dates
	 	* @param start_date start date
	 	* @param end_date end date
   	*/
    public static long findLength(Date start_date, Date end_date)
    {

			//in milliseconds
			long difference = end_date.getTime() - start_date.getTime();
			
			return difference / (24 * 60 * 60 * 1000) + 1;
    }
    
    /**
   	* Find the number of drivers unavailable on a given date
   	* @param date the date you wish to check for unavailability
   	*/
    public static int driversUnavailable(Date date)
    {
    	int[] driverIDs = DriverInfo.getDrivers();
    	int unavailableCount = 0;
    	
    	for(int driverID: driverIDs)
    	{
    		if(!DriverInfo.isAvailable(driverID, date))
    			unavailableCount++;
    	}
    
    	return unavailableCount;    
    }
    
    /**
   	* Requesting a holiday and checking if it meets rostering rules 
   	* before granting it
   	* @param driverID driver ID of the driver requestion the holiday
   	* @param start_date start date of holiday request
   	* @param end_date end date of holiday request
   	*/    
    // 
    public static boolean requestHoliday(int driverID, Date start_date, Date end_date)
    {
            System.out.println(driverID);
            System.out.println(start_date);
            System.out.println(end_date);
    	if(start_date.after(end_date))
    		throw new InvalidQueryException("start_date is before end_date");
    	
    	long length = findLength(start_date, end_date);
    	int taken = DriverInfo.getHolidaysTaken(driverID);
    	int holidaysTaken = 0;   
    	int holidaysTakenNextYear = 0; 	
    	Date current_date = start_date;
    	
    	if(start_date.getYear() == end_date.getYear())
    	{    	
    		if(length + taken > 25)
    			return false; // request denied
    	}
	
			if(end_date.getYear() > start_date.getYear())
			{
				Date end_of_year = new Date(start_date.getYear(), 11, 31);
				Date start_of_next_year = new Date(end_date.getYear(), 0, 1);
				
				int taken_from_next_year = DriverInfo.getNextHolidaysTaken(driverID);
				long length_in_current_year = findLength(start_date, end_of_year);
				long length_in_next_year = findLength(start_of_next_year, end_date);
				
    		if(length_in_current_year + taken > 25)
    			return false; // request denied
    		if(length_in_next_year + taken_from_next_year > 25)
    			return false; // request denied	
			}

    	
    	// check availabilty	
  		for(int i=0; i < length; i++)
  		{
  			// if weekend 0=sunday, 6=saturday
  			if(current_date.getDay() == 0 || current_date.getDay() == 6)
  			{
  				if(driversUnavailable(current_date) >= 15)
    				return false; // request denied
    		}
    		else
    		{
					if(driversUnavailable(current_date) >= 10)
    				return false; // request denied
				}
				current_date.setDate(current_date.getDate()+1);
  		}

			current_date.setDate(current_date.getDate()-(int)length);
			

    	
    	
  		for(int i=0; i < length; i++)
  		{
  			if(DriverInfo.isAvailable(driverID, current_date))
  			{
  			  
  				DriverInfo.setAvailable(driverID, current_date, false);
  				if(current_date.getYear() != end_date.getYear())
  					holidaysTaken++;
					else
						holidaysTakenNextYear++;
				}
				
				current_date.setDate(current_date.getDate()+1);
  		}

  		int newHolidaysTaken = DriverInfo.getHolidaysTaken(driverID) + holidaysTaken;
    	DriverInfo.setHolidaysTaken(driverID, newHolidaysTaken);

  		int newHolidaysTakenNextYear = DriverInfo.getNextHolidaysTaken(driverID) + holidaysTakenNextYear;
    	DriverInfo.setNextHolidaysTaken(driverID, newHolidaysTakenNextYear); 
    	   	
    	return true; // holiday granted
    }
}
