// Delay class for delays
import java.util.*;
import static java.util.Calendar.*;
public class Delay {
  public enum timetableKind {weekday, saturday, sunday};
    /**
     * @param args the command line arguments
     */

    // Get value of certain delay when given  sevice number and date
    // -1 if no entry, 0 if cancelled, else returns delay time
    public static int getDelay(int service_number, Date date)
    {
        return database.busDatabase.get_int_delay("delays", service_number, date, "delay");
    } 

    // Add new delay record into table
    public static void addDelayToDatabase(int service_number, int bus_number, int delay, String message, Date date)
    {
        database.busDatabase.new_record("delays", new Object[][]{{"service_number", service_number}, {"bus_number", bus_number}, {"delay", delay}, {"message", message}, {"date", date}});
    }



    

}