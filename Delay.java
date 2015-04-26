import java.util.*;
import static java.util.Calendar.*;
public class Delay {
  public enum timetableKind {weekday, saturday, sunday};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        database.openBusDatabase();
/*
        int routes[] = {65,66,67,68};
        ArrayList<Service> services = new ArrayList();

        getAllServices(routes, services);

        printServiceTimes(services);
       // printServiceTimes(service_times); */
    }

    // -1 if no entry, 0 if cancelled, else returns delay time
    public static int getDelay(int service_number, Date date)
    {
        return database.busDatabase.get_int_delay("delays", service_number, date, delay);
    }

    // if you want to cancel, set delay time to 0, else set delay time in minutes (delay)
    public static void setDelay(int service_number, int bus_number, int delay, String message, Date date)
    {
        addDelayToDatabase(service_number, bus_number, delay, message, date);
    }

    public static void addDelayToDatabase(int service_number, int bus_number, int delay, String message, Date date)
    {
        database.busDatabase.new_record("delays", new Object[][]{{"service_number", service_number}, {"bus_number", bus_number}, {"delay", delay}, {"message", message}, {"date", date}});
    }





    

}