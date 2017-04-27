import java.util.*;

public class Scheduling
{
  public static void main(String args[])
  {
    database.openBusDatabase();
    Date tempDate = new Date(115,7,13);
    ScheduleDrivers(tempDate);
  }
  public static void ScheduleDrivers(Date requiredDate)
  {
  boolean busAssigned = false;
  int busCount = 0;
  Date date = requiredDate;
  //if driver is available add him to the arraylist
  ArrayList<Driver> drivers = new ArrayList();
  ArrayList<Driver> assignedDrivers = new ArrayList();
  int[] allDrivers = DriverInfo.getDrivers();
  int routes[] = {65,66,67,68};
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
    Roster.getServicesSaturday(routes, services);
  else if(date.getDay() == 6)
    Roster.getServicesSunday(routes, services);
  else
    Roster.getServicesWeekday(routes, services);

  while (!services.isEmpty())
  {
      sortDrivers(drivers);
      //go through each driver trying to assign as many services to him
      for(int i = 0; i < drivers.size(); i++)
      {
        System.out.println("Driver " + i);

        //loop through all services, trying to assign driver
        for(int j = 0; j < services.size(); j++)
        {
          System.out.println("Service " + j);
          if (!drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() <= 300)
          {
            System.out.println("a");
            if (drivers.get(i).getMinutesForToday() + services.get(j).getDuration() > 300)
            //give him a 1 hour break
            {
              System.out.println("b");
              drivers.get(i).setBreak();
              drivers.get(i).setUnavailableDriverStartTime(drivers.get(i).getUnavailableDriverEndTime());
              drivers.get(i).setUnavailableDriverEndTime(drivers.get(i).getUnavailableDriverStartTime()+60);
            }
            else
            // assign
            {
              System.out.println("c");
              services.get(j).setDriver(drivers.get(i));
              drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());
              drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
              drivers.get(i).addMinutesForToday(services.get(j).getDuration());
              System.out.println("Driver assigned");

              //assign a bus
              while (!busAssigned)
              {
                if(buses.get(busCount).isAvailable(services.get(j).getStart()))
                {
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

              assignedServices.add(services.get(j));
              //remove the service from the array list
              services.remove(services.get(j));
              System.out.println("Service removed");
            }
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + services.get(j).getDuration() <= 600)
          {
            System.out.println("d");
            //assign
            services.get(j).setDriver(drivers.get(i));
            drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());
            drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
            drivers.get(i).addMinutesForToday(services.get(j).getDuration());
            System.out.println("Driver assigned");

              //assign a bus
              while (!busAssigned)
              {
                if(buses.get(busCount).isAvailable(services.get(j).getStart()))
                {
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

            assignedServices.add(services.get(j));
            //remove the service from the array list
            services.remove(services.get(j));
            System.out.println("Service removed");
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + services.get(j).getDuration() > 600)
          {
            System.out.println("e");
            assignedDrivers.add(drivers.get(i));
            //worked enough so remove him
            drivers.remove(drivers.get(i));
            System.out.println("Driver removed");
          }
        }//for services
      }//for drivers
  }//while services not empty
  for (Driver driver : assignedDrivers)
  {

    driver.setHoursForToday();
    DriverInfo.setHoursThisWeek(driver.getDriverID(), (DriverInfo.getHoursThisWeek(driver.getDriverID()) + driver.getHoursForToday()));
    DriverInfo.setHoursThisYear(driver.getDriverID(), (DriverInfo.getHoursThisYear(driver.getDriverID()) + driver.getHoursForToday()));
    System.out.println("herro" + driver.getDriverID() + " " + (DriverInfo.getHoursThisWeek(driver.getDriverID()) + " "+  driver.getMinutesForToday()));
  }
  for (Bus bus : buses)
  {
    if (bus.getHoursDriven() > 0)
    {
      int idOfBus =bus.getID();
      BusInfo.setAvailable(idOfBus, date, false);
      System.out.println("busss" + bus.getHoursDriven());
    }
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

































































import java.util.*;

public class Scheduling
{
  public static void main(String args[])
  {
    database.openBusDatabase();
    Date tempDate = new Date(115,8,21);
    ScheduleDrivers(tempDate);



  }
  public static void ScheduleDrivers(Date requiredDate)
  {
  int wastedTime = 0;
  boolean busAssigned = false;
  int busCount = 0;
  Date date = requiredDate;
  //if driver is available add him to the arraylist
  ArrayList<Driver> drivers = new ArrayList();
  ArrayList<Driver> assignedDrivers = new ArrayList();
  int[] allDrivers = DriverInfo.getDrivers();
  int routes[] = {65,66,67,68};
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
    Roster.getServicesSaturday(routes, services);
  else if(date.getDay() == 6)
    Roster.getServicesSunday(routes, services);
  else
    Roster.getServicesWeekday(routes, services);

  while (!services.isEmpty())
  {
      sortDrivers(drivers);
      //go through each driver trying to assign as many services to him
      for(int i = 0; i < drivers.size(); i++)
      {
        System.out.println("Driver " + i);

        //loop through all services, trying to assign driver
        for(int j = 0; j < services.size(); j++)
        {
          if (drivers.get(i).isFirstShift())
          {
            drivers.get(i).setFinishTime(services.get(j).getStart() + 660);
            //drivers.get(i).setFirstShift();
            //workingTime = services.get(j).getStart() + 660;
          }
          /*else
          {
            wastedTime = services.get(j).getStart() - drivers.get(i).getUnavailableDriverEndTime();
            //drivers.get(i).addMinutesForToday(wastedTime);
          }*/

          System.out.println("Service starts at: " + services.get(j).getStart());
          if (!drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() <= 300 && services.get(j).getEnd() <= drivers.get(i).getFinishTime() )
          {
            System.out.println("a");
            if (drivers.get(i).getMinutesForToday() + services.get(j).getDuration() > 300)
            //give him a 1 hour break
            {
              System.out.println("b");
              drivers.get(i).setBreak();
              drivers.get(i).setUnavailableDriverStartTime(drivers.get(i).getUnavailableDriverEndTime());
              drivers.get(i).setUnavailableDriverEndTime(drivers.get(i).getUnavailableDriverStartTime()+60);
            }
            else
            // assign
            {
              System.out.println("c");
              if (!assignedDrivers.contains(drivers.get(i)))
                assignedDrivers.add(drivers.get(i));
              services.get(j).setDriver(drivers.get(i));
              drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());

              if (drivers.get(i).isFirstShift())
              {
                drivers.get(i).setFirstShift();
                drivers.get(i).setStartingTime(services.get(j).getStart());
                drivers.get(i).setFinishTime(services.get(j).getStart() + 660);
              }
              else
              {
                wastedTime = drivers.get(i).getUnavailableDriverStartTime() - drivers.get(i).getUnavailableDriverEndTime();
               // drivers.get(i).addMinutesForToday(wastedTime);
              }
              
              drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
              drivers.get(i).addMinutesForToday(services.get(j).getDuration());
              System.out.println("Driver assigned");

              //assign a bus
              while (!busAssigned)
              {
                if(buses.get(busCount).isAvailable(services.get(j).getStart()))
                {
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

              assignedServices.add(services.get(j));
              //remove the service from the array list
              services.remove(services.get(j));
              System.out.println("Service removed");
            }
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + services.get(j).getDuration() <= 600 && services.get(j).getEnd() <= drivers.get(i).getFinishTime())
          {
            System.out.println("d");
            //assign
            if (!assignedDrivers.contains(drivers.get(i)))
              assignedDrivers.add(drivers.get(i));
            services.get(j).setDriver(drivers.get(i));
            drivers.get(i).setUnavailableDriverStartTime(services.get(j).getStart());

            if (drivers.get(i).isFirstShift())
            {
              drivers.get(i).setFirstShift();
              drivers.get(i).setStartingTime(services.get(j).getStart());
              drivers.get(i).setFinishTime(services.get(j).getStart() + 660);
            }
            else
            {
              wastedTime = drivers.get(i).getUnavailableDriverStartTime() - drivers.get(i).getUnavailableDriverEndTime();
              //drivers.get(i).addMinutesForToday(wastedTime);
            }

            drivers.get(i).setUnavailableDriverEndTime(services.get(j).getEnd());
            drivers.get(i).addMinutesForToday(services.get(j).getDuration());
            System.out.println("Driver assigned");

              //assign a bus
              while (!busAssigned)
              {
                if(buses.get(busCount).isAvailable(services.get(j).getStart()))
                {
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

            assignedServices.add(services.get(j));
            //remove the service from the array list
            services.remove(services.get(j));
            System.out.println("Service removed");
          }
          else if (drivers.get(i).hasTakenBreak() && drivers.get(i).isAvailable(services.get(j).getStart()) && drivers.get(i).getMinutesForToday() + services.get(j).getDuration() > 600 && services.get(j).getEnd() <= drivers.get(i).getFinishTime())
          {
            System.out.println("e");
            //assignedDrivers.add(drivers.get(i));
            //worked enough so remove him
            drivers.remove(drivers.get(i));
            System.out.println("Driver removed");
          }
        }//for services
      }//for drivers
  }//while services not empty
  for (Driver driver : assignedDrivers)
  {
    driver.setHoursForToday();
    DriverInfo.setHoursThisWeek(driver.getDriverID(), (DriverInfo.getHoursThisWeek(driver.getDriverID()) + driver.getHoursForToday()));
    DriverInfo.setHoursThisYear(driver.getDriverID(), (DriverInfo.getHoursThisYear(driver.getDriverID()) + driver.getHoursForToday()));
    System.out.println("herro" + driver.getDriverID() + " " + (DriverInfo.getHoursThisWeek(driver.getDriverID()) + " "+  driver.getMinutesForToday()));
  }
  for (Bus bus : buses)
  {
    if (bus.getHoursDriven() > 0)
    {
      int idOfBus =bus.getID();
      BusInfo.setAvailable(idOfBus, date, false);
      System.out.println("busss" + bus.getHoursDriven());
    }
  }

    for(Service service : assignedServices)
    {
      System.out.print("service no: " + service.getServiceNumber());
      System.out.print(", driver id: " + service.getDriver().getDriverID());
      System.out.println(", bus id: " + service.getBus());
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