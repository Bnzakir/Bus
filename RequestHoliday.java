import java.util.Date;

public class RequestHoliday 
{

    public static void main(String[] args) 
    {
    
      database.openBusDatabase();
            
    	Date test1 = new Date(115, 0, 29);
			Date test2 = new Date(115, 1, 1);
	
			System.out.println(findLength(test1, test2));
    }
    
    public static long findLength(Date start_date, Date end_date)
    {

			//in milliseconds
			long difference = end_date.getTime() - start_date.getTime();
			
			return difference / (24 * 60 * 60 * 1000) + 1;
    }
    
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
    
    public static boolean requestHoliday(int driverID, Date start_date, Date end_date)
    {
    	int length = (int)findLength(start_date, end_date);
    	int taken = DriverInfo.getHolidaysTaken(driverID);
    	
    	if(length + taken > 25)
    		return false; // request denied
    	
    	Date current_date = new Date();
    	current_date = start_date;
    		
  		for(int i=0; i < length; i++)
  		{
  			if(driversUnavailable(current_date) >= 10)
  				return false; // request denied
				current_date.setDate(current_date.getDate()+1);
 		
  		}
    	
    	int holidaysTaken = 0;
    	
  		for(int i=0; i < length; i++)
  		{
  			if(DriverInfo.isAvailable(driverID, current_date))
  			{
  				DriverInfo.setAvailable(driverID, current_date, false);
  				holidaysTaken++;
				}
				current_date.setDate(current_date.getDate()+1);
  		}   
  		int newHolidaysTaken = DriverInfo.getHolidaysTaken(driverID) + holidaysTaken;
    	DriverInfo.setHolidaysTaken(driverID, newHolidaysTaken);
    	return true; // holiday granted
    }
}
