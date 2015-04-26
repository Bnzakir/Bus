public class Driver implements Comparable<Driver>
{
  private int driverID;
  private int unavailableStartTime;
  private int unavailableEndTime;
  private int minutesToday;
  private boolean breakTaken;
  private int hoursToday;
  private boolean firstShift;
  private int startingTime;
  private int finishTime;

  public Driver(int requiredID)
  {
    driverID = requiredID;
    minutesToday = 0;
    hoursToday = 0;
    breakTaken = false;
    unavailableEndTime = 0;
    unavailableStartTime = 0;
    firstShift = true;
    startingTime = 0;
  }

  public int getDriverID()
  {
    return driverID;
  }

  public void setStartingTime(int time)
  {
    startingTime = time;
  }

  public void setFinishTime(int time)
  {
    finishTime = time;
  }

  public int getFinishTime()
  {
    return finishTime;
  }

  public boolean isFirstShift()
  {
    if (firstShift == true)
      return true;
    else return false;
  }

  public void setFirstShift()
  {
    firstShift = false;
  }

  public int getHoursWeek()
  {
    return DriverInfo.getHoursThisWeek(driverID);
  }
  public void setHoursWeek(int hours)
  {
    DriverInfo.setHoursThisWeek(driverID, hours);
  }
  public int getHoursYear()
  {
    return DriverInfo.getHoursThisYear(driverID);
  }
  public void setHoursYear(int hours)
  {
    DriverInfo.setHoursThisYear(driverID, hours);
  }
  public int getUnavailableDriverStartTime()
  {
    return unavailableStartTime;
  }

  public int getUnavailableDriverEndTime()
  {
    return unavailableEndTime;
  }

  public void setUnavailableDriverStartTime(int reqUnavailableStartTime)
  {
    unavailableStartTime = reqUnavailableStartTime;
  }

  public void setUnavailableDriverEndTime(int reqUnavailableEndTime)
  {
    unavailableEndTime = reqUnavailableEndTime;
  }
  public void addMinutesForToday(int nrMinutes)
  {
    minutesToday += nrMinutes;
  }

  public int getMinutesForToday()
  {
    return minutesToday;
  }

  public void setHoursForToday()
  {
    if(minutesToday%60 == 0)
      hoursToday = minutesToday/60;
    else
      hoursToday = minutesToday/60 + 1;
    if(driverID == 2016)
    System.out.println("Driver: " + driverID + " no. hours: " + hoursToday + " no. mins: " +minutesToday);
  }

  public int getHoursForToday()
  {
    return hoursToday;
  }


  public boolean hasTakenBreak()
  {
    if(breakTaken)
      return true;
    else return false;
  }

  public void setBreak()
  {
    breakTaken = true;
  }

  public boolean isAvailable(int serviceStartTime)
  {
    if (unavailableEndTime <= serviceStartTime)
      return true;
    else return false;
  }

  @Override
    public  int compareTo(Driver otherDriver)
    {
         if(this.driverID > otherDriver.driverID)
           return 1;
         else if(this.driverID < otherDriver.driverID)
             return -1;
         else
             return 0;       
   }

    public int compareTo(Driver otherDriver, int type)
    {
        if(type == 0)
        {
            if(this.minutesToday > otherDriver.minutesToday)
                return 1;
            else if(this.minutesToday < otherDriver.minutesToday)
                return -1;
            else
                return 0;           
        }
        else if(type == 1)
        {
            if(this.getHoursWeek() > otherDriver.getHoursWeek())
                return 1;
            else if(this.getHoursWeek() < otherDriver.getHoursWeek())
                return -1;
            else
                return 0;           
        }
        else
        {
            if(this.getHoursYear() > otherDriver.getHoursYear())
                return 1;
            else if(this.getHoursYear() < otherDriver.getHoursYear())
                return -1;
            else
                return 0;           
        }
  }

}