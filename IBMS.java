import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
 
public class IBMS extends JFrame implements ActionListener{
 
        private JPanel contentPane;
        private final Action action = new SwingAction();
        private JButton btnTimetable;
        private JButton btnDriverLogin;
 
        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        IBMS frame = new IBMS();
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
        public static int driverID;
        public IBMS() {
                setTitle("IBMS");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 299, 130);
                contentPane = new JPanel();
                //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
               
                btnTimetable = new JButton("Tmetable");
                btnTimetable.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                               
                        }
                });
                btnTimetable.setBounds(147, 29, 93, 33);
                contentPane.add(btnTimetable);
               
                btnDriverLogin = new JButton("Driver Login");
                btnDriverLogin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        driverID = Integer.parseInt(
                                   JOptionPane.showInputDialog("Please enter driver Id"));
                        
                        //RequestHoliday.requestHoliday(driverID, DriverRequestHoliday.)
                        //if (responseFromDB != 0)
                        DriverRequestHoliday j2 = new DriverRequestHoliday();
                        j2.setVisible(true);
                        //else
                        //WrongPassword j3 = new WrongPassword();
                        //j3.setVisible(true);
                        }
                });
                btnDriverLogin.setBounds(32, 29, 105, 33);
                contentPane.add(btnDriverLogin);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        private class SwingAction extends AbstractAction {
                public SwingAction() {
                        putValue(NAME, "SwingAction");
                        putValue(SHORT_DESCRIPTION, "Some short description");
                }
                public void actionPerformed(ActionEvent e) {
                }
        }
}