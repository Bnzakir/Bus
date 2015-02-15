import java.util.Date;

public class RequestHoliday 
{

    public static void main(String[] args) 
    {
    
      database.openBusDatabase();
            
    	Date test1 = new Date(115, 1, 23);
			Date test2 = new Date(115, 1, 25);

			requestHoliday(2012, test1, test2);
    }
    
    // computes the length of a holiday between two dates
    public static long findLength(Date start_date, Date end_date)
    {

			//in milliseconds
			long difference = end_date.getTime() - start_date.getTime();
			
			return difference / (24 * 60 * 60 * 1000) + 1;
    }
    
    // checks how many drivers are unavailable on a given date
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
    
    // requesting a holiday and checking if it meets rostering rules before granting it
    public static boolean requestHoliday(int driverID, Date start_date, Date end_date)
    {
    	if(start_date.after(end_date)
    		throw new InvalidQueryException("start_date is before end_date");
    	
    	long length = findLength(start_date, end_date);
    	int taken = DriverInfo.getHolidaysTaken(driverID);
    	int holidaysTaken = 0;    	
    	Date current_date = start_date;
    	    	
    	if(length + taken > 25){System.out.println("no holiday");
    		return false; // request denied
    	}

    		
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
  		  //System.out.println("fdgfdg");
  			if(DriverInfo.isAvailable(driverID, current_date))
  			{
  			  
  				DriverInfo.setAvailable(driverID, current_date, false);
  				holidaysTaken++;
				}
				current_date.setDate(current_date.getDate()+1);
  		}
  		   System.out.println(current_date);
  		int newHolidaysTaken = DriverInfo.getHolidaysTaken(driverID) + holidaysTaken;
    	DriverInfo.setHolidaysTaken(driverID, newHolidaysTaken);
    	return true; // holiday granted
    }
}
