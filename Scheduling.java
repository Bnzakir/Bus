import java.util.*;

public class Scheduling
{
  public static void main(String args[])
  {
    database.openBusDatabase();

  }

  public static ArrayList<Service> Schedule(Date date)
  {

    int routes[] = {65,66,67,68};

    ArrayList<Service> assigned_driver_services = ScheduleDrivers(date, routes);

    return ScheduleBuses(date, assigned_driver_services, routes);

  }


  public static ArrayList<Service> ScheduleDrivers(Date requiredDate, int routes[])
  {
  int wastedTime = 0;
  int totalTime = 0;
  Date date = requiredDate;

  //if driver is available add him to the arraylist
  ArrayList<Driver> drivers = new ArrayList();
  ArrayList<Driver> assignedDrivers = new ArrayList();
  int[] allDrivers = DriverInfo.getDrivers();
  ArrayList<Service> services = new ArrayList();
  ArrayList<Service> assignedServices = new ArrayList();

  if(date.getDay() == 0 )
    {
      for (int driverID : allDrivers)
        DriverInfo.setHoursThisWeek(driverID, 0);
    }

  for (int i = 0; i < allDrivers.length; i++)
  {
    if (DriverInfo.isAvailable(allDrivers[i], date))
    {
      Driver driver = new Driver(allDrivers[i]);
      drivers.add(driver);
    }
  }

  // for each driver, based on the fair scheduling algorithm, assign him to a service
  if(date.getDay() == 0 )
    Roster.getServicesSaturday(routes, services);
  else if(date.getDay() == 6)
    Roster.getServicesSunday(routes, services);
  else
    Roster.getServicesWeekday(routes, services);

  sortServices(services);

  for (Service serv: services)
    totalTime += serv.getDuration();

  while (!services.isEmpty())
  {
      sortDrivers(drivers);
      //go through each driver trying to assign as many services to him
      for(int i = 0; i < drivers.size(); i++)
      {
        wastedTime = 0;
       // System.out.println("Driver " + i);

        //loop through all services, trying to assign driver
        for(int j = 0; j < services.size(); j++)
        {
          if (!drivers.get(i).isFirstShift())
          {
            wastedTime = services.get(j).getStart() - drivers.get(i).getUnavailableDriverEndTime();
          }

        //  System.out.println("Service starts at: " + services.get(j).getStart());
          if (!drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + wastedTime <= 300)
          {
           // System.out.println("a");
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
              if (!assignedDrivers.contains(drivers.get(i)))
                assignedDrivers.add(drivers.get(i));
              services.get(j).setDriver(drivers.get(i));
              drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());

              if (drivers.get(i).isFirstShift())
              {
                drivers.get(i).setFirstShift();
              }
              else
              {
                wastedTime = drivers.get(i).getUnavailableDriverStartTime() - drivers.get(i).getUnavailableDriverEndTime();
                drivers.get(i).addMinutesForToday(wastedTime);
              }
              
              drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
              drivers.get(i).addMinutesForToday(services.get(j).getDuration());

              assignedServices.add(services.get(j));
              //remove the service from the array list
              services.remove(services.get(j));
            }
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + wastedTime + services.get(j).getDuration() <= 600)
          {
            //assign
            if (!assignedDrivers.contains(drivers.get(i)))
              assignedDrivers.add(drivers.get(i));
            services.get(j).setDriver(drivers.get(i));
            drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());

            if (drivers.get(i).isFirstShift())
            {
              drivers.get(i).setFirstShift();
            }
            else
            {
              wastedTime = drivers.get(i).getUnavailableDriverStartTime() - drivers.get(i).getUnavailableDriverEndTime();
              drivers.get(i).addMinutesForToday(wastedTime);
            }

            drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
            drivers.get(i).addMinutesForToday(services.get(j).getDuration());

            assignedServices.add(services.get(j));
            //remove the service from the array list
            services.remove(services.get(j));
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + wastedTime + services.get(j).getDuration() > 600)
          {
            //assignedDrivers.add(drivers.get(i));
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
   // System.out.println("DRIVER " + driver.getDriverID() + " " + (DriverInfo.getHoursThisWeek(driver.getDriverID()) + " "+  driver.getMinutesForToday()));
  }


 // System.out.println("TOTAL TIME : " + totalTime);

  return assignedServices;
}//ScheduleDrivers()




  public static ArrayList<Service> ScheduleBuses(Date requiredDate, ArrayList<Service> services, int routes[])
  {
    int totalTime = 0;
    boolean busAssigned = false;
    int busCount = 0;
    Date date = requiredDate;
  
    int[] allDrivers = DriverInfo.getDrivers();

  
    ArrayList<Bus> buses = new ArrayList();
    int[] allBuses = BusInfo.getBuses();  
  
    for (int i = 0; i < allBuses.length; i++)
    {
      if (BusInfo.isAvailable(allBuses[i], date))
      {
        Bus bus = new Bus(allBuses[i]);
        buses.add(bus);
        //System.out.println(i);
      }
    }


  /*for(Service service : services)
  {
    System.out.print("service no: " + service.getServiceNumber());
    System.out.println(", driver id: " + service.getDriver().getDriverID());
    //System.out.println(", bus id: " + service.getBus());
  }  */

  // for each driver, based on the fair scheduling algorithm, assign him to a service

  sortServices(services);

        //loop through all services, trying to assign driver
        for(int j = 0; j < services.size(); j++)
        {
          //System.out.println(" TIME OF SERVICE: " + services.get(j).getStart());

          //assign a bus
          while (!busAssigned)
          {
            if(buses.get(busCount).isAvailable(services.get(j).getStart()))
            {
              //System.out.println("BUS ID: " + buses.get(busCount).getID() + " TIME OF SERVICE: " + services.get(j).getStart() + " service no: " + services.get(j). );
              services.get(j).setBus(buses.get(busCount).getID());
              buses.get(busCount).setUnavailableBusStartTime(services.get(j).getStart());
              buses.get(busCount).setUnavailableBusEndTime(services.get(j).getEnd());
              buses.get(busCount).addBusMinutesForToday(services.get(j).getDuration());
              busAssigned = true;
            }
            busCount++;
          }
          busAssigned = false;
          busCount = 0;

          //remove the service from the array list
          //services.remove(services.get(j));
        }//for services

        for (Bus bus : buses)
          {
            if (bus.getHoursDriven() > 0)
            {
              int idOfBus =bus.getID();
              BusInfo.setAvailable(idOfBus, date, false);
           //   System.out.println("BUS " + bus.getHoursDriven());
            }
          }

  for(Service service : services)
  {
    addRosterToDatabase(service.getServiceNumber(), service.getDriver().getDriverID(), service.getBus(), date);
  }  

  return services;
}//ScheduleBuses()


  public static void addRosterToDatabase(int service, int driver, int bus, Date date)
  {
    database.busDatabase.new_record("roster", new Object[][]{{"service", service}, {"driver", driver}, {"bus", bus}, {"day", date}});

  }

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

  public static void sortServices(ArrayList<Service> services)
  {
    Collections.sort(services, new Comparator<Service>()
    {
      @Override
       public int compare(Service  service1, Service  service2)
       {
          return  service1.compareTo(service2);
        }
    }
    );
  }//sortDrivers
}//class Scheduling