import java.util.*;

public class Bus implements Comparable<Bus>
{
  private int busID;
  private int unavailableStartTime;
  private int unavailableEndTime;
  private int hoursDriven;
  private int busMinutes;

  Bus(int requiredID)
  { 
    busID = requiredID;
    unavailableEndTime = 0;
    unavailableStartTime = 0;  
    hoursDriven = 0;
    busMinutes = 0;
  }
  public int getID()
  {
    return busID;
  }
  public boolean getAvailable(Date date)
  {
    return BusInfo.isAvailable(busID, date);
  }
  public void setAvailable(Date date, boolean available)
  {
    BusInfo.setAvailable(busID, date, available);
  }

  public int getHoursDriven()
  {
    if((busMinutes/60)%60 == 0)
      return busMinutes/60;
    else
      return busMinutes/60 + 1;
  }

  public void addBusMinutesForToday(int nrMinutes)
  {
    busMinutes += nrMinutes;
  }

  public int getUnavailableBusStartTime()
  {
    return unavailableStartTime;
  }

  public int getUnavailableBusEndTime()
  {
    return unavailableEndTime;
  }

  public void setUnavailableBusStartTime(int reqUnavailableStartTime)
  {
    unavailableStartTime = reqUnavailableStartTime;
  }

  public void setUnavailableBusEndTime(int reqUnavailableEndTime)
  {
    unavailableEndTime = reqUnavailableEndTime;
  }


  public boolean isAvailable(int serviceStartTime)
  {
    if (unavailableEndTime <= serviceStartTime)
      return true;
    else return false;
  }
  
 /* public boolean isAvailable(int serviceStartTime, int serviceEndTime)
  {
    if (unavailableStartTime < serviceStartTime || unavailableEndTime > serviceStartTime)
      return true;
    else return false;
  }  */

    @Override
    public  int compareTo(Bus otherBus)
    {
        if(this.getHoursDriven() > otherBus.getHoursDriven())
          return  1;
        else if(this.getHoursDriven() < otherBus.getHoursDriven())
          return -1;
        else
          return 0;
    }


}