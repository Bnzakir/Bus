package journeyplanner;

import java.util.*;

public class JourneyPlanner
{

  public static ArrayList<String>[] table;
  public static int[] routes;
  public static ArrayList<String> resultJourney;

  public static void main() 
  { 

  database.openBusDatabase();
  
  table = new ArrayList[4];
  routes = new int[4];
  resultJourney = new ArrayList<String>();
  
  String[][] busStopsNames = new String[4][100]; 
  int[][] busStops = new int[4][100]; 
  

  for (int i = 0; i < 4; i++) 
    table[i] = new ArrayList<String>();

  routes = BusStopInfo.getRoutes();

  // initialize bus stop numbers to 0 
  for (int i = 0; i < 4; i++) 
    for (int j = 0; j < 100; j++) 
    { 
      busStops[i][j] = 0; 
    } 

  for (int route = 0; route < 4; route++) 
    busStops[route] = BusStopInfo.getBusStops(routes[route]); 

  for (int route = 0; route < 4; route++) 
    for (int j = 0; j < busStops[route].length; j++) 
      busStopsNames[route][j] = BusStopInfo.getFullName(busStops[route][j]);

  // puts bus stops in arraylists for each route 
  for (int i = 0; i < 4; i++) 
    for (int j = 0; j < busStops[i].length; j++) 
      table[i].add(busStopsNames[i][j]); 

  calculateJourney(JourneyPlannerGUI.startStop, JourneyPlannerGUI.endStop);
} 

public static void calculateJourney(String startStop, String endStop) 
{

  ArrayList<String>[] routePlanner = new ArrayList[10];
  ArrayList<String>[] routeNames = new ArrayList[10];

  for (int i = 0; i < 10; i++) 
  {
    routePlanner[i] = new ArrayList<String>();
    routeNames[i] = new ArrayList<String>();
  }


  int routeIndex = 0;
  // if it cotains both stops then done 
  // else check for start then find common station and check for end stop in that route 
  // finally if nothing can be found, return null 
  outerloop: 
  for (int i = 0; i < 4; i++) 
  { //for (String stop : table[i]) 
        //System.out.println(table[i].contains(startStop) + " " + table[i].contains(endStop) + " " + table[i].indexOf(startStop) + " " + table[i].indexOf(endStop));

      if (table[i].contains(startStop) && table[i].contains(endStop)) 
      { 
        //System.out.println("table[i].get(indexOfEndStop)");

        int indexOfStartStop = table[i].indexOf(startStop); 
        int indexOfEndStop = table[i].indexOf(endStop);

        if (endStop.equals(table[i].get(0)))
          indexOfEndStop = table[i].size()-1;

        // if start index is greater than end index, swap them
        if((routes[i] == 67 || routes[i] == 68) && indexOfStartStop > indexOfEndStop)
        {
          int temp = indexOfStartStop;
          indexOfStartStop = indexOfEndStop;
          indexOfEndStop = temp;
        }



        for (int index = indexOfStartStop; index <= indexOfEndStop; index++) 
        {
       // System.out.println("success");

          routePlanner[routeIndex].add(table[i].get(index)); 
          routeNames[routeIndex].add(BusStopInfo.getRouteName(routes[i]));
        }

        routeIndex++;

        //break outerloop; 
      } 
      else if (table[i].contains(startStop)) 
      { 
        
        for (int index = 0; index < 4; index++) 
        {

          if (i == index) 
            continue; 
          else 
          { 
            int busStopIndex = 0;
            for (String bus_stop : table[i]) 
            { 
              //System.out.println(table[i].indexOf(bus_stop) + " " + tempIndex);
              if (busStopIndex > table[i].indexOf(startStop) && table[index].contains(bus_stop) && !bus_stop.equals(startStop) && table[index].contains(endStop)) 
              { 
                if (table[index].indexOf(bus_stop) < table[index].indexOf(endStop))
                {
                System.out.println(BusStopInfo.getRouteName(routes[i]) + " " + BusStopInfo.getRouteName(routes[index]));

                   System.out.println("FOUND COMMON BUS STOP: " + bus_stop);              
                  for (int count = table[i].indexOf(startStop); count < busStopIndex; count++) 
                  {
                    routePlanner[routeIndex].add(table[i].get(count)); //found a common stop 
                    routeNames[routeIndex].add(BusStopInfo.getRouteName(routes[i]));
                  }
                  for (int count = table[index].indexOf(bus_stop); count <= table[index].indexOf(endStop); count++) 
                  {
                    routePlanner[routeIndex].add(table[index].get(count));
                    routeNames[routeIndex].add(BusStopInfo.getRouteName(routes[index]));
                  }

                routeIndex++;
                } 
              }
              busStopIndex++; 
            } 
          }
        } 
      }
      }
 
      int min = routePlanner[0].size();
      routeIndex = 0;

      for (int i = 1; i < 10; i++)
        if(min > routePlanner[i].size() && routePlanner[i].size() != 0)
        {
          min = routePlanner[i].size();
          routeIndex = i;
        }

      /*for (int i = 0; i < routePlanner[routeIndex].size(); i++) 
          resultJourney.add(routeNames[routeIndex].get(i) + "   " + routePlanner[routeIndex].get(i));
      
      if (routePlanner[routeIndex].size() == 0)
          resultJourney.add("No route found");
                  
      for (int i = 0; i < routePlanner[routeIndex].size(); i++)
      {
        System.out.println(routeNames[routeIndex].get(i) + "\t" + routePlanner[routeIndex].get(i));
      }*/
      
      String startRouteName = routeNames[routeIndex].get(0);
      String endRouteName;
      int changeRouteIndex = 0;
      while (changeRouteIndex < routePlanner[routeIndex].size() && startRouteName.equals(routeNames[routeIndex].get(changeRouteIndex)))
        changeRouteIndex++;

      //System.out.println(endRouteName); 
      int startRoute = BusStopInfo.findRoute(startRouteName);
      
//System.out.println(startRoute + " " + endRoute);
      int startBusStops[] = BusStopInfo.getBusStops(startRoute);
      
      String startNames[] = new String[startBusStops.length];

      for (int i = 0; i < startBusStops.length; i++)
        startNames[i] = BusStopInfo.getFullName(startBusStops[i]);

      int startBusStopNumberIndex = 0;
      for (int i = 0; i < startNames.length-1; i++)
      {
          //System.out.println(startNames[i] + " " + routePlanner[routeIndex].get(0)+ " " + startNames[i+1] + " " + routePlanner[routeIndex].get(1)); 

        if(startNames[i].equals(routePlanner[routeIndex].get(0)) && startNames[i+1].equals(routePlanner[routeIndex].get(1)))
          startBusStopNumberIndex = i;
      }

      ArrayList<Integer> journeyStops = new ArrayList<Integer>();
      for (int i = 0; i < changeRouteIndex; i++)
        journeyStops.add(startBusStops[startBusStopNumberIndex+i]);

      int endRoute = 0;
      if (changeRouteIndex != routePlanner[routeIndex].size())
      {
        endRouteName = routeNames[routeIndex].get(changeRouteIndex);
        endRoute = BusStopInfo.findRoute(endRouteName);
        int endBusStops[] = BusStopInfo.getBusStops(endRoute);
        String endNames[] = new String[endBusStops.length];

        for (int i = 0; i < endBusStops.length; i++)
          endNames[i] = BusStopInfo.getFullName(endBusStops[i]);

        int endBusStopNumberIndex = 0;
        for (int i = 0; i < endNames.length-1; i++)
          if(endNames[i].equals(routePlanner[routeIndex].get(changeRouteIndex)) && endNames[i+1].equals(routePlanner[routeIndex].get(changeRouteIndex+1)))
            endBusStopNumberIndex = i;
       
        for (int i = 0; i < routePlanner[routeIndex].size()-changeRouteIndex; i++)
          journeyStops.add(endBusStops[endBusStopNumberIndex+i]);
      }

      /*for (int af : journeyStops)
        System.out.println(af);*/

      ArrayList<Integer> journeyTimes = new ArrayList<Integer>();
      Date today = new Date();
      int minutesToday = (today.getHours() * 60) + today.getMinutes();


      //System.out.println(getTimes(journeyStops.get(0), today, startRoute, minutesToday));
      journeyTimes.add(getTimes(journeyStops.get(0), today, startRoute, minutesToday));
      for (int i = 1; i < changeRouteIndex; i++)
      {
        journeyTimes.add(getTimes(journeyStops.get(i), today, startRoute, journeyTimes.get(i-1)));
        //System.out.println(getTimes(journeyStops.get(i), today, startRoute, journeyTimes.get(0)));
      }
  
      if (changeRouteIndex != routePlanner[routeIndex].size())
      {
        //System.out.println(journeyTimes.get(changeRouteIndex-1));
        journeyTimes.add(getTimes(journeyStops.get(changeRouteIndex), today, endRoute, journeyTimes.get(changeRouteIndex-1)));
        //System.out.println(journeyTimes.get(changeRouteIndex));
  
        for (int i = changeRouteIndex+1; i < journeyStops.size(); i++)
        {
          journeyTimes.add(getTimes(journeyStops.get(i), today, endRoute, journeyTimes.get(i-1)));
          //System.out.println(getTimes(journeyStops.get(i), today, endRoute, minutesToday));
        }
      }

      if (routePlanner[routeIndex].size() == 0)
        resultJourney.add("No route found");
      
      String out = "";
      for (int i = 0; i < routePlanner[routeIndex].size(); i++)
      {
        out = out.format("%s\t%-40s\t%02d:%02d", 
                         routeNames[routeIndex].get(i).substring(0, 3), 
                         routePlanner[routeIndex].get(i), 
                         (journeyTimes.get(i)/60), 
                         (journeyTimes.get(i)%60));
        System.out.println(out);
        resultJourney.add(out);
      }
      
      /*for (int i = 0; i < routePlanner[routeIndex].size(); i++) 
          resultJourney.add("%s   %-40s   %s\n", routeNames[routeIndex].get(i), routePlanner[routeIndex].get(i), (journeyTimes.get(i)/60) + ":" + (journeyTimes.get(i)%60));*/      
  }

  public static int getTimes(int busStop, Date today, int route, int time)
  {
    //String resultTime;
    int resultTime;

    TimetableInfo.timetableKind todayKind = TimetableInfo.timetableKind(today);
    int timetableID = TimetableInfo.getTimetableID(todayKind, route);
    //int busStopTimes[];
    if (BusStopInfo.isTimingPointOnRoute(busStop, route))
    {
      //System.out.println(BusStopInfo.isTimingPointOnRoute(busStop, route));

      int busStopTimes[] = database.busDatabase.select_ids("time", "timetable_line","timing_point", busStop, "daily_timetable", timetableID,"time");

    /*for (int i = 0; i < busStopTimes.length; i++)
      System.out.println(busStopTimes[i]);*/

      int index = 0;
      while (index != busStopTimes.length && (busStopTimes[index]-time) < 0)
        index++;
      
      if (index == busStopTimes.length)
          resultTime = 0;
      else
          resultTime = busStopTimes[index];

    //TimetableInfo.getTimetableID(todayKind, route);
    //System.out.println(busStopTimes[index]);
      //System.out.println(busStopTimes[index]);
      //if (busStopTimes.length > 0)
        
     // else
       //   return 0;
    }
    else
    {
      int prevStop = BusStopInfo.getPreviousStop(busStop, route);
      int nextStop = BusStopInfo.getNextStop(busStop, route);

      int busStopTimesOne[] = database.busDatabase.select_ids("time", "timetable_line","timing_point", nextStop, "daily_timetable", timetableID,"time");

      int indexOne = 0;
      while (indexOne != busStopTimesOne.length && (busStopTimesOne[indexOne]-time) < 0)
        indexOne++;

      int serviceNumber = database.busDatabase.find_id("service", "timetable_line", 
                                                        "time", busStopTimesOne[indexOne], 
                                                        "daily_timetable", timetableID, 
                                                        "timing_point", nextStop);

      int timeAtPrevStop = database.busDatabase.find_id("time", "timetable_line", 
                                                        "service", serviceNumber, 
                                                        "timing_point", prevStop);

      resultTime = (busStopTimesOne[indexOne] + timeAtPrevStop)/2;
    }

    return resultTime;
  } 
}

