import java.util.*;

public class temp
{
  public static void main(String args[])
  {
    database.openBusDatabase();
    Date tempDate = new Date();
    ScheduleDrivers(tempDate);
  }

  public static void ScheduleDrivers(Date requiredDate)
  {
  int temp = 0;
  boolean busAssigned = false;
  Date date = requiredDate;
  int busCount = 0;
  //if driver is available add him to the arraylist
  ArrayList<Driver> drivers = new ArrayList();
  ArrayList<Driver> assignedDrivers = new ArrayList();
  int[] allDrivers = DriverInfo.getDrivers();


  int routes[] = {67};
  ArrayList<Service> services = new ArrayList();
  ArrayList<Service> assignedServices = new ArrayList();

  for (int i = 0; i < allDrivers.length; i++)
  {
    if (DriverInfo.isAvailable(allDrivers[i], date))
    {
      Driver driver = new Driver(allDrivers[i]);
      drivers.add(driver);
    }
  }

  ArrayList<Bus> buses = new ArrayList();
  int[] allBuses = BusInfo.getBuses();  

  for (int i = 0; i < allBuses.length; i++)
  {
    if (BusInfo.isAvailable(allBuses[i], date))
    {
      Bus bus = new Bus(allBuses[i]);
      buses.add(bus);
    }
  }

  // for each driver, based on the fair scheduling algorithm, assign him to a service
  if(date.getDay() == 0 )
    Roster.getServicesSunday(routes, services);
  else if(date.getDay() == 6)
    Roster.getServicesSaturday(routes, services);
  else
    Roster.getServicesWeekday(routes, services);

  while (!services.isEmpty())
  {
      sortDrivers(drivers);
      //go through each driver trying to assign as many services to him
      for(int i = 0; i < drivers.size(); i++)
      {
        //loop through all services, trying to assign driver
        for(int j = 0; j < services.size(); j++)
        {
          if (!drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() <= 300)
          {
            if (drivers.get(i).getMinutesForToday() + services.get(j).getDuration() > 300)
            //give him a 1 hour break
            {
              drivers.get(i).setBreak();
              drivers.get(i).setUnavailableDriverStartTime(drivers.get(i).getUnavailableDriverEndTime());
              drivers.get(i).setUnavailableDriverEndTime(drivers.get(i).getUnavailableDriverStartTime()+60);
            }
            else
            // assign
            {
              //assign a driver
              services.get(j).setDriver(drivers.get(i));
              drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());
              drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
              drivers.get(i).addMinutesForToday(services.get(j).getDuration());

              //assign a bus
              while (!busAssigned)
              {
                if(buses.get(busCount).isAvailable(services.get(j).getStart()))
                {
                  services.get(j).setBus(buses.get(busCount).getID());
                  buses.get(busCount).setUnavailableBusStartTime(services.get(j).getStart());
                  buses.get(busCount).setUnavailableBusEndTime(services.get(j).getEnd());
                  busAssigned = true;
                }
                busCount++;
              }
              busAssigned = false;

              assignedServices.add(services.get(j));
              //remove the service from the array list
              services.remove(services.get(j));
            }
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + services.get(j).getDuration() <= 600)
          {
            //assign
            services.get(j).setDriver(drivers.get(i));
            drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());
            drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
            drivers.get(i).addMinutesForToday(services.get(j).getDuration());

            //assign a bus
            while (!busAssigned)
            {
              if(buses.get(busCount).isAvailable(services.get(j).getStart()))
              {
                services.get(j).setBus(buses.get(busCount).getID());
                buses.get(busCount).setUnavailableBusStartTime(services.get(j).getStart());
                buses.get(busCount).setUnavailableBusEndTime(services.get(j).getEnd());
                busAssigned = true;
              }
              busCount++;
            }
            busAssigned = false;

            assignedServices.add(services.get(j));
            //remove the service from the array list
            services.remove(services.get(j));
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + services.get(j).getDuration() > 600)
          {
            assignedDrivers.add(drivers.get(i));
            //worked enough so remove him
            drivers.remove(drivers.get(i));
          }
        }//for services
      }//for drivers
  }//while services not empty
  for (Driver driver : assignedDrivers)
  {
    driver.setHoursForToday();
    DriverInfo.setHoursThisWeek(driver.getDriverID(), (DriverInfo.getHoursThisWeek(driver.getDriverID()) + driver.getHoursForToday()));
    DriverInfo.setHoursThisYear(driver.getDriverID(), (DriverInfo.getHoursThisYear(driver.getDriverID()) + driver.getHoursForToday()));
    System.out.println(" " + temp);
    temp++;
  }
}//ScheduleDrivers()


  public static void sortDrivers(ArrayList<Driver> drivers)
  {
    Collections.sort(drivers, new Comparator<Driver>()
    {
      @Override
       public int compare(Driver  driver1, Driver  driver2)
       {
          return  driver1.compareTo(driver2, 0);
        }
    }
    );
    Collections.sort(drivers, new Comparator<Driver>()
    {
      @Override
      public int compare(Driver  driver1, Driver  driver2)
      {
        return  driver1.compareTo(driver2, 1);
      }
    }
    );        
    Collections.sort(drivers, new Comparator<Driver>()
    {
      @Override
      public int compare(Driver  driver1, Driver  driver2)
      {
        return  driver1.compareTo(driver2, 2);
      }
    }
    );
  }//sortDrivers
}//class Scheduling