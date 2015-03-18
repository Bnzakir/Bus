/*
 * A very simple application illustrating how to use the interface.
 * Prints the names of all the drivers in the database.
 * @author John Sargeant
 */
import java.util.*;
import static java.util.Calendar.*;

/*******************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*******************************************************************************/

public class Timetable extends JFrame{

  public enum timetableKind {weekday, saturday, sunday};
    /**
     * @param args the command line arguments
     */
     
/*******************************************************************************/
//Attributes used
  private	JPanel topPanel;
  private	JTable table;
  private	JScrollPane scrollPane;
  private static String times[][];
  private static ArrayList<Service> services; 
/*******************************************************************************/


    public static void main(String[] args) 
    {
        database.openBusDatabase();
        Timetable mainFrame	= new Timetable();
	      mainFrame.setVisible( true );         
     
    }
    
    
   
/*******************************************************************************/
	  // Constructor of main frame
	  public Timetable()
	  {
        Date tempDate = new Date(115,9,20);
		  // Set the frame characteristics
		  setTitle( "Simple Table Application" );
		  setSize( 300, 200 );
		  setBackground( Color.gray );
            services = Scheduling.Schedule(tempDate);
		  // Create a panel to hold all other components
		  topPanel = new JPanel();
		  topPanel.setLayout( new BorderLayout() );
		  getContentPane().add( topPanel );

		  // Create columns names, according to the number of bus stops
		  




/* Method to store all the timing points in an array list */

        int servicesize =  services.size();
       // int nostops = services.get(0).getServiceTimes().length;
        int nostops = 12;
        times = new String[servicesize][nostops + 1];
        String columnNames[] = new String[nostops + 1];
        columnNames[0] = "Route";
          for(int i = 1; i < nostops + 1; i++)
          {
            columnNames[i] = ("Bus stop" + i);
          }
        System.out.println("no.services: " + services.size());

        for(int i=0; i < services.size(); i++)
          times[i][0] = "" +services.get(i).getRoute();

       // int times[][];
        for(int i=0; i < services.size(); i++)
        {
            
            for(int j = 1; j < services.get(i).getServiceTimes().length + 1; j++)
            {
                int time =  services.get(i).getServiceTimes()[j - 1].getTime();

                times[i][j] = (time/60) + ":" + (time%60);
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
