import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
 
public class WrongPassword extends JFrame {
 
        private JPanel contentPane;
 
        /**
         * Launch the application.
         */
        public static void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        WrongPassword frame = new WrongPassword();
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
        public WrongPassword() {
                setTitle("Driver Request Holiday Page");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 349, 235);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
               
                JLabel lblStartOfHoliday = new JLabel("Wrong USER ID");
                lblStartOfHoliday.setBounds(10, 31, 120, 22);
                contentPane.add(lblStartOfHoliday);
               
                JButton btnRequestHoliday = new JButton("OK");
                btnRequestHoliday.setBounds(104, 140, 145, 33);
                contentPane.add(btnRequestHoliday);
        }
}