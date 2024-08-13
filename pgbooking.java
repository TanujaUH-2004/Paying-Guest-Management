package tanuja;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;

public class pgbooking extends JFrame implements ActionListener {
    JLabel l1 = new JLabel("SAMUNNATHI PG");
    JLabel l2 = new JLabel("Welcome to PG Booking");
    JLabel l3 = new JLabel("(feel like your home)");

    JLabel l4 = new JLabel("Name :");
    JLabel l5 = new JLabel("Phone :");
    JLabel l6 = new JLabel("Address :");
    JTextField t1 = new JTextField(20);
    JTextField t2 = new JTextField(20);
    JTextField t3 = new JTextField(20);

    JLabel l7 = new JLabel("Rooms Available :");

    JRadioButton r1 = new JRadioButton("3 sharing (price:6000/-)");
    JRadioButton r2 = new JRadioButton("4 sharing (price:4000/-)");

    JButton b1 = new JButton("SUBMIT");
    JButton b2 = new JButton("CANCEL");
    ButtonGroup group = new ButtonGroup();
    JLabel l8 = new JLabel();

    pgbooking() {
    	JLabel l = new JLabel();
        setContentPane(new JLabel(new ImageIcon("C:\\Users\\ASUS\\Desktop\\OIP12.png")));
        add(l);
        l.setBounds(0,0,1920,1200);
        l.setSize(1920,1200);
        
        setLayout(null);
        l1.setBounds(550, 25, 500, 80);
        l2.setBounds(640, 70, 300, 80);
        l3.setBounds(670, 95, 250, 80);
        l4.setBounds(630, 145, 80, 80);
        l5.setBounds(630, 185, 80, 80);
        l6.setBounds(630, 225, 100, 80);
        l7.setBounds(630, 268, 190, 80);
        l8.setBounds(630, 455, 600, 80);
        l1.setFont(new Font("Times New Roman", Font.BOLD, 55));
        l2.setFont(new Font("Times New Roman", Font.BOLD, 25));
        l3.setFont(new Font("Times New Roman", Font.BOLD, 22));
        t1.setBounds(700, 175, 195, 20);
        t2.setBounds(700, 214, 195, 20);
        t3.setBounds(717, 253, 180, 20);
        r1.setBounds(670, 332, 200, 20);
        r2.setBounds(670, 372, 200, 20);
        l8.setFont(new Font("Times New Roman", Font.BOLD, 22));
        l8.setForeground(Color.white);
        l4.setFont(new Font("Times New Roman", Font.BOLD, 20));
        l5.setFont(new Font("Times New Roman", Font.BOLD, 20));
        l6.setFont(new Font("Times New Roman", Font.BOLD, 20));
        l7.setFont(new Font("Times New Roman", Font.BOLD, 20));
        
        b1.setBounds(620, 430, 100, 40);
        b2.setBounds(820, 430, 100, 40);
        
        group.add(r1);
        group.add(r2);
        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(t1);
        add(l5);
        add(t2);
        add(l6);
        add(t3);
        add(l7);
        add(r1);
        add(r2);
        add(b1);
        add(b2);
        add(l8);
        b1.addActionListener(this);
        b2.addActionListener(this);
        r1.addActionListener(this);
        r2.addActionListener(this);

        setTitle("pgbooking app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("SUBMIT")) {
            submit();
        } 
        else if (s.equals("CANCEL")) {
        	t1.setText("");
            t2.setText("");
            t3.setText("");
            group.clearSelection();
            l8.setText("");
        }
    }

    private void submit() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "tejas")) {
                String sql = "INSERT INTO pg (name, phoneno, address, roombooked) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, t1.getText());
                    preparedStatement.setString(2, t2.getText());
                    preparedStatement.setString(3, t3.getText());
                    preparedStatement.setString(4, getSelectedRoomType());

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        l8.setText("Submitted successfully");
                    } else {
                        l8.setText("Submission failed");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            l8.setText("Error: " + ex.getMessage());
        }
    }

    private String getSelectedRoomType() {
        if (r1.isSelected()) {
            return "3 sharing (price:6000/-)";
        } else if (r2.isSelected()) {
            return "4 sharing (price:4000/-)";
        } else {
            return ""; // No radio button selected
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new pgbooking();
        });
    }
}