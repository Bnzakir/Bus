/*
 * A very simple application illustrating how to use the interface.
 * Prints the names of all the drivers in the database.
 * @author John Sargeant
 */
import java.util.*;
import static java.util.Calendar.*;
public class test {
  public enum timetableKind {weekday, saturday, sunday};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        database.openBusDatabase();
	   
        Service services[] = getServiceTimesRoute(65, TimetableInfo.timetableKind.weekday);

        printServiceTimes(services);
       // printServiceTimes(service_times);
    }



    public static Service[] getServiceTimesRoute(int route, TimetableInfo.timetableKind day)
    {
        
        int noservices = TimetableInfo.getNumberOfServices(route, day);
        int[] timingpoints = TimetableInfo.getTimingPoints(route);

        int notimingpoints = TimetableInfo.getTimingPoints(route).length;

        ServiceTime route_times[][] = new ServiceTime[noservices][notimingpoints];

        int services[] = TimetableInfo.getServices(route, day);

        Service list_services[] = new Service[services.length];

        for(int i=0; i < services.length; i++)
        {
            int[] serviceTimes = TimetableInfo.getServiceTimes(route, day, i);

            for(int j=0; j < serviceTimes.length; j++)
            {
                route_times[i][j] = new ServiceTime(route, timingpoints[j], serviceTimes[j], services[i]);
            }

            for(int j=0; j < serviceTimes.length; j++)
            {
                list_services[i] = new Service(serviceTimes.length, route_times[i]);
            }
        }

        return list_services;
    }

    public static void printServiceTimes(Service[] services)
    {
        for(int i=0; i < services.length; i++)
        {
            System.out.print("Service number " + services[i].getServiceNumber() + ", ");
            System.out.print("start: " + services[i].getStart() + ", ");
            System.out.print("end: " + services[i].getEnd() + ", ");
            System.out.print("final stop: " + services[i].getFinalStop() + ", ");
            System.out.println("Duration: " +services[i].getDuration() + " minutes");
        }
        System.out.println();

    } 


}