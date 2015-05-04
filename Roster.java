import java.util.*;
import static java.util.Calendar.*;
public class Roster {
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


    public static ArrayList<Service> getAllServices(int routes[], ArrayList<Service> services)
    {
        
        for(int route : routes)
        {
            Service services1[] = getServiceTimesRoute(route, TimetableInfo.timetableKind.weekday);
            for(Service service: services1)
            {
                services.add(service);
            }
            Service services2[] = getServiceTimesRoute(route, TimetableInfo.timetableKind.saturday);
            for(Service service: services2)
            {
                services.add(service);
            }
            Service services3[] = getServiceTimesRoute(route, TimetableInfo.timetableKind.sunday);
            for(Service service: services3)
            {
                services.add(service);
            }
        }

        return services;
    }

    public static void getServicesWeekday(int routes[], ArrayList<Service> services)
    {
        for(int route : routes)
        {
            Service services1[] = getServiceTimesRoute(route, TimetableInfo.timetableKind.weekday);
            for(Service service: services1)
            {
                services.add(service);
            }
        }
    }    

    public static void getServicesSaturday(int routes[], ArrayList<Service> services)
    {
        for(int route : routes)
        {
            Service services1[] = getServiceTimesRoute(route, TimetableInfo.timetableKind.saturday);
            for(Service service: services1)
            {
                services.add(service);
            }
        }
    }    

    public static void getServicesSunday(int routes[], ArrayList<Service> services)
    {
        for(int route : routes)
        {
            Service services1[] = getServiceTimesRoute(route, TimetableInfo.timetableKind.sunday);
            for(Service service: services1)
            {
                services.add(service);
            }
        }
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
                list_services[i] = new Service(serviceTimes.length, route_times[i], route);
            }
        }

        return list_services;
    }


    public static void getServiceTimesRoutePassenger(int bus_stop, Date date)
    {
        int day_type;
        // if weekend 0=sunday, 6=saturday
        if(date.getDay() == 0)
            day_type = 2;
        if(date.getDay() == 6)
            day_type = 1;
        else
            day_type = 0;


        int[] times = database.busDatabase.select_times("time", "timetable_line", bus_stop, day_type, "");
        int[] services = database.busDatabase.select_times("service", "timetable_line", bus_stop, day_type, "");


        for(int i=0; i < times.length; i++)
        {
            if(Delay.getDelay(services[i], date) > 0)
            {
                times[i] += Delay.getDelay(services[i], date);
                System.out.println("Delayed service: " + times[i] + ", " + services[i]);
            }
            else if(Delay.getDelay(services[i], date) < 0)
            {
                System.out.println("Cancelled service: " + services[i]);
            }   
            else 
                System.out.println("On time: " + times[i] + ", " + services[i]);         
        }
    }



    public static void printServiceTimes(ArrayList<Service> services)
    {
        for(int i=0; i < services.size(); i++)
        {
            System.out.print("Service number " + services.get(i).getServiceNumber() + ", ");
            System.out.print("Route " + services.get(i).getRoute() + ", ");
            System.out.print("start: " + services.get(i).getStart() + ", ");
            System.out.print("end: " + services.get(i).getEnd() + ", ");
            System.out.print("final stop: " + services.get(i).getFinalStop() + ", ");
            System.out.println("Duration: " +services.get(i).getDuration() + " minutes");
        }
        System.out.println();

    } 


}