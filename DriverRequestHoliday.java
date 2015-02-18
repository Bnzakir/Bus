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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


 
 
public class DriverRequestHoliday extends JFrame implements ActionListener{
 
        private JPanel contentPane;
 
        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        DriverRequestHoliday frame = new DriverRequestHoliday();
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
        private JDatePickerImpl datePickerStart;
        private JDatePickerImpl datePickerEnd;
        public DriverRequestHoliday() {
                setTitle("Driver Request Holiday Page");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(300, 300, 300, 500);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);

                JLabel lblStartOfHoliday = new JLabel("Start of holiday");
                lblStartOfHoliday.setBounds(10, 31, 120, 22);
                contentPane.add(lblStartOfHoliday);
                
                JLabel lblEndOfHoliday = new JLabel("End of holiday");
                lblEndOfHoliday.setBounds(10, 150, 120, 14);
                contentPane.add(lblEndOfHoliday);

                JButton btnRequestHoliday = new JButton("Request Holiday");
                btnRequestHoliday.addActionListener(this);
                btnRequestHoliday.setBounds(80, 300, 145, 33);
                contentPane.add(btnRequestHoliday);

                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                UtilDateModel model1 = new UtilDateModel();
                model1.setDate(year, month, day);
                model1.setSelected(true);
                JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
                datePickerStart = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
                datePickerStart.setBounds(120, 31, 160, 50);
                contentPane.add(datePickerStart);
                
                UtilDateModel model2 = new UtilDateModel();
                model2.setDate(year, month, day+1);
                model2.setSelected(true);
                JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
                datePickerEnd = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
                datePickerEnd.setBounds(120, 150, 160, 50);
                contentPane.add(datePickerEnd);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Date startDate = (Date) datePickerStart.getModel().getValue();
            Date endDate = (Date) datePickerEnd.getModel().getValue();

            if(RequestHoliday.requestHoliday(IBMS.driverID, startDate, endDate))
            {
                JOptionPane.showMessageDialog(contentPane, "Holiday request sucessful.");
            }
            else
            {
                JOptionPane.showMessageDialog(contentPane, RequestHoliday.reason);
            }
        }
    }