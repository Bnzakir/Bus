import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;


public class ControllerCreateRoster extends JFrame implements ActionListener{

private static String dayOfMonth;

        private JPanel contentPane;
 
        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        ControllerCreateRoster frame = new ControllerCreateRoster();
                                        frame.setVisible(true);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }
 
        /**
         * Create the frame.
         */
 
        public ControllerCreateRoster() {
                setTitle("Create Roster");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(400, 400, 400, 400);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                /*JLabel lblStartOfRoster = new JLabel("start time of roster");
                lblStartOfRoster.setBounds(10, 31, 120, 22);
                contentPane.add(lblStartOfRoster);*/
                


                JButton btnRequestHoliday = new JButton("Generate Roster");
                btnRequestHoliday.addActionListener(this);
                btnRequestHoliday.setBounds(100, 100, 200, 200);
                contentPane.add(btnRequestHoliday);

                

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            database.openBusDatabase();
            dayOfMonth = (JOptionPane.showInputDialog("Please enter the day of the month"));
            Date date = new Date();
            int day = Integer.parseInt(dayOfMonth);
            date.setDate(day);
            day = date.getDate();
            for (int index = 0; index < 7; index++)
            {
              Timetable mainFrame = new Timetable(date);
              mainFrame.setVisible( true ); 
                date.setDate(++day);
            }
        }
    }