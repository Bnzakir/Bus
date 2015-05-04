import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.Date;
/**
 * calculates all possible combinations of three weights
 * @author Hasan Kolcu
 */
public class AddDelay extends JFrame implements ActionListener
{

  // text fields for entering and result display area
  private final JTextField day = new JTextField(20);
  private final JTextField month = new JTextField(20);
  private final JTextField year = new JTextField(20);

  private final JTextField service_number = new JTextField(20);
  private final JTextField bus_number = new JTextField(20);
  private final JTextField delay_time = new JTextField(20);

  private final JTextField message = new JTextField(20);

  private final JTextArea resultJTextArea = new JTextArea(15,20);
  
  /* constructor - adds textfields, textarea, scrollpanel, button
  and packs them - uses gridlayout and borderlayout */
  public AddDelay()
  { 
    setTitle("Add delay");
    Container contents = getContentPane();
    contents.setLayout(new BorderLayout());
    JPanel numbersPanel = new JPanel(new GridLayout(4,0));

    contents.add(numbersPanel, BorderLayout.NORTH);



    numbersPanel.add(new JLabel("day"));
    numbersPanel.add(day);

    numbersPanel.add(new JLabel("month"));
    numbersPanel.add(month);

    numbersPanel.add(new JLabel("year"));
    numbersPanel.add(year);

    numbersPanel.add(new JLabel("service number"));
    numbersPanel.add(service_number);

    numbersPanel.add(new JLabel("bus_number"));
    numbersPanel.add(bus_number);

    numbersPanel.add(new JLabel("delay_time"));
    numbersPanel.add(delay_time);

    numbersPanel.add(new JLabel("message"));
    numbersPanel.add(message);

    JButton computeJButton = new JButton("Add Delay");
    contents.add(computeJButton, BorderLayout.SOUTH);
    computeJButton.addActionListener(this);

    // results 

   
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    pack();
  } // ThreeWeights

  public void actionPerformed(ActionEvent e)
  {
    database.openBusDatabase();

    int day1 = Integer.parseInt(day.getText());
    int month1 = Integer.parseInt(month.getText());
    int year1 = Integer.parseInt(year.getText()) + 1900;

    int service = Integer.parseInt(service_number.getText());

    int bus = Integer.parseInt(bus_number.getText());

    int delay = Integer.parseInt(delay_time.getText());

    String message1 = message.getText();
    
    Date delay_date = new Date(year1 - 3800, month1 - 1, day1);

    Delay.addDelayToDatabase(service, bus, delay, message1, delay_date);
 

  } // actionPerformed

  public static void main(String[] args)
  {
  //  new AddDelay().setVisible(true);
database.openBusDatabase();
    Date test = new Date(2015-1900,3,29);

  //  System.out.println(Delay.getDelay(565, test));
    
      Roster.getServiceTimesRoutePassenger(770, test);

  } // main

} // class ThreeWeights
 
 


