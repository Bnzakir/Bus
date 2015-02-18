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
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setBounds(100, 100, 400, 220);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
               
                JLabel lblStartOfHoliday = new JLabel("The driver number could not be found. Please retry.");
                lblStartOfHoliday.setBounds(40, 40, 350, 22);
                contentPane.add(lblStartOfHoliday);
               
                JButton btnRequestHoliday = new JButton("OK");
                btnRequestHoliday.setBounds(104, 140, 145, 33);
                contentPane.add(btnRequestHoliday);
        }
}