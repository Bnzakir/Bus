/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package delay;

import java.util.*;
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


    /* Select times for all busses at a certain bus stop AND checks for delays/cancellations and displays accordingly */
    public static String getServiceTimesRoutePassenger(int bus_stop, Date date)
    {
        String output = "";
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
        int[] daily_timetable = database.busDatabase.select_times("daily_timetable", "timetable_line", bus_stop, day_type, "");
        
        for(int da : daily_timetable)
            System.out.println(da);
        
        int[] route_numbers = new int[daily_timetable.length];
        
        // Checking for route number based on the day timetable number Id
        for(int i=0; i < daily_timetable.length; i++)
        {
            if(daily_timetable[i] == 193 || daily_timetable[i] == 194 || daily_timetable[i] == 195)
                route_numbers[i]= 65;
            else if(daily_timetable[i] == 196 || daily_timetable[i] == 197 || daily_timetable[i] == 198)
                route_numbers[i]= 66;        
            else if(daily_timetable[i] == 199 || daily_timetable[i] == 200 || daily_timetable[i] == 201)
                route_numbers[i]= 67; 
            else if(daily_timetable[i] == 202 || daily_timetable[i] == 203 || daily_timetable[i] == 204)
                route_numbers[i]= 68; 
        }

        // Check through all times and check if service is delayed
        for(int i=0; i < times.length; i++)
        {
            // Number of minutes delayed
            if(Delay.getDelay(services[i], date) > 0)
            {
                times[i] += Delay.getDelay(services[i], date);
                output+=("Delayed service: " + times[i]/60 + ":" + times[i]%60 + " ( " + Delay.getDelay(services[i], date) + "minute delay), " + route_numbers[i] + "\n");
            }
            // If negative delay time is entered then service is canceled 
            else if(Delay.getDelay(services[i], date) < 0)
            {
                output+=("Cancelled service: " + route_numbers[i]+ "\n");
            }   
            else 
                // Else just print out normally
                output+=("On time: " + times[i]/60 + ":" + times[i]%60 + ", " + route_numbers[i] + "\n");         
        }
        
        return output;
        
    }


}