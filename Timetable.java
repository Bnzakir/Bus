import java.util.*;
import static java.util.Calendar.*;

/*******************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*******************************************************************************/
// Timaetable class
public class Timetable extends JFrame{

  public enum timetableKind {weekday, saturday, sunday};
    /**
     * @param args the command line arguments
     */
     
/*******************************************************************************/
//Attributes used
  private JPanel topPanel;
  private JTable table;
  private JScrollPane scrollPane;
  private static String times[][];
  private static ArrayList<Service> services; 
/*******************************************************************************/

// Main Method 
    public static void main(String[] args) 
    {
        database.openBusDatabase();
/*
        Date date = new Date(116, 2, 21);
        int day = date.getDate();
        for (int index = 0; index < 7; index++)
        {
          Timetable mainFrame = new Timetable(date);
          mainFrame.setVisible( true ); 
          date.setDate(++day);
        }*/
    }
/*******************************************************************************/
    // Constructor of main frame
    public Timetable(Date date)
    {
              //Date date = new Date(116, 1, 1);
             // int day = date.getDate();
      // Set the frame characteristics
      setTitle("Date: " + date.getDate() + "." + date.getMonth() + "." + (date.getYear()+1900));
      setSize( 1000, 1000 );
      setBackground( Color.gray );
              // for (int index = 0; index < nrOfDays; index++)
             //  {
                  services = Scheduling.Schedule(date);
               //   date.setDate(++day);
              // }
      // Create a panel to hold all other components
      topPanel = new JPanel();
      topPanel.setLayout( new BorderLayout() );
      getContentPane().add( topPanel );

      // Create columns names, according to the number of bus stops
      
/* Method to store all the timing points in an array list */

        int servicesize =  services.size();
       // int nostops = services.get(0).getServiceTimes().length;
        int nostops = 12;
        times = new String[servicesize][nostops + 3];
        String columnNames[] = new String[nostops + 3];
        columnNames[0] = "Route";
          for(int i = 1; i < nostops + 1; i++)
          {
            columnNames[i] = ("Bus stop" + i);
          }
   
            columnNames[nostops + 1] = ("driver ID");
            columnNames[nostops + 2] = ("bus ID");
        System.out.println("no.services: " + services.size());

        for(int i=0; i < services.size(); i++)
          times[i][0] = "" +services.get(i).getRoute();

        for(int i=0; i < services.size(); i++)
          times[i][nostops + 1] = "" +services.get(i).getDriver().getDriverID();

        for(int i=0; i < services.size(); i++)
          times[i][nostops + 2] = "" +services.get(i).getBus();


       // int times[][];
        for(int i=0; i < services.size(); i++)
        {
            
            for(int j = 1; j < services.get(i).getServiceTimes().length + 1; j++)
            {
                int time = services.get(i).getServiceTimes()[j - 1].getTime();

                times[i][j] =  BusStopInfo.getName(services.get(i).getServiceTimes()[j - 1].getStop()) + " " + (time/60) + "\n:" + (time%60);
            }

        } 

      // Create a new table instancem
      table = new JTable( times, columnNames );

      // Add the table to a scrolling pane
      scrollPane = new JScrollPane( table );
      topPanel.add( scrollPane, BorderLayout.CENTER );
    }
    
/*******************************************************************************/
}