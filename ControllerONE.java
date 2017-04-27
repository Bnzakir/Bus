import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
 
public class ControllerONE extends JFrame implements ActionListener{
 
        private JPanel contentPane;
        private final Action action = new SwingAction();
        private JButton btnTimetable;
        private JButton btnDriverLogin;
 
        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                database.openBusDatabase();
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        ControllerONE frame = new ControllerONE();
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
        public static String driverNumber;
        public ControllerONE() {
                setTitle("ControllerONE");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 300, 130);
                contentPane = new JPanel();
                //contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
               
               
                btnDriverLogin = new JButton("Click here to generate roster");
                btnDriverLogin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    ControllerCreateRoster j2 = new ControllerCreateRoster();
                    j2.setVisible(true);
                }
                });
                btnDriverLogin.setBounds(20, 29, 120, 33);
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