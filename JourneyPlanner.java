import java.util.*;

public class JourneyPlanner
{

  public static ArrayList<String>[] table = new ArrayList[4];

  public static void main(String[] args)
  {
    database.openBusDatabase();

    String[][] busStopsNames = new String[4][100];
    int[][] busStops = new int[4][100];
    int[] routes = new int[4];

    for (int i = 0; i < 4; i++)
      table[i] = new ArrayList<String>();

    routes = BusStopInfo.getRoutes();

    // initialize bus stop numbers to 0
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 100; j++)
        {
          busStops[i][j] = 0;
          //busStopsNames[i][j] = "NULL";
        }
    for (int route = 0; route < 4; route++)
    {
      busStops[route] = BusStopInfo.getBusStops(routes[route]);
    }

    for (int route = 0; route < 4; route++)
    {
      for (int j = 0; j < busStops[route].length; j++)
        busStopsNames[route][j] = BusStopInfo.getName(busStops[route][j]);
    }

    // puts bus stops in arraylists for each route
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < busStops[i].length; j++)
          {
            table[i].add(busStopsNames[i][j]);
            //System.out.println(busStopsNames[i][j]);
          }

     calculateJourney(BusStopInfo.getName(805), BusStopInfo.getName(770));
  }
  public static void calculateJourney(String startStop, String endStop)
  {

    ArrayList<String> routePlanner = new ArrayList<String>();

    // if it cotains both stops then done
    // else check for start then find common station and check for end stop in that route
    // finally if nothing can be found, return null
    outerloop:
    for (int i = 0; i < 4; i++)
      for (String stop : table[i])
        if (table[i].contains(startStop) && table[i].contains(endStop))
        {
          int indexOfStartStop = table[i].indexOf(startStop);
          int indexOfEndStop = table[i].indexOf(endStop);

          for (int index = indexOfStartStop; index <= indexOfEndStop; index++)
            routePlanner.add(table[i].get(index));
          break outerloop;
        }
        else if (table[i].contains(startStop))
        {
          for (int index = 0; index < 4; index++)
          {
            if (i == index)
              continue;
            else
            {
              for (String bus_stop : table[i])
              {
                if (table[index].contains(bus_stop) && !bus_stop.equals(startStop) && table[index].contains(endStop))
                 {
                   for (int count = table[i].indexOf(startStop); count < table[i].indexOf(bus_stop); count++)
                      routePlanner.add(table[i].get(count));
                   //found a common stop
                   for (int count = table[index].indexOf(bus_stop); count <= table[index].indexOf(endStop); count++)
                      routePlanner.add(table[index].get(count));
                 }
              }
            }
          }
        }
        for (String index : routePlanner)
          System.out.println(index);
      }
    }