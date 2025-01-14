package churchmanagementsystem;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChurchManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ChurchDB";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = ""; // Replace with your MySQL password
    private Connection connection;

    public ChurchManagementSystem() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        initComponents();
    }

    private void initComponents() {
        JFrame frame = new JFrame("Church Management System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Add Member", createAddMemberPanel());
        tabbedPane.add("Manage Events", createManageEventsPanel());
        tabbedPane.add("Record Donations", createRecordDonationsPanel());
        tabbedPane.add("View Members", createViewMembersPanel());
        tabbedPane.add("View Events", createViewEventsPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private JPanel createAddMemberPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel joinDateLabel = new JLabel("Join Date (YYYY-MM-DD):");
        JTextField joinDateField = new JTextField();
        JButton addButton = new JButton("Add Member");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(joinDateLabel);
        panel.add(joinDateField);
        panel.add(new JLabel()); // Spacer
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String joinDate = joinDateField.getText();

            if (!name.isEmpty() && !joinDate.isEmpty()) {
                try {
                    String query = "INSERT INTO members (name, address, phone, email, join_date) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, name);
                    stmt.setString(2, address);
                    stmt.setString(3, phone);
                    stmt.setString(4, email);
                    stmt.setString(5, joinDate);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    joinDateField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createManageEventsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel titleLabel = new JLabel("Event Title:");
        JTextField titleField = new JTextField();
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField();
        JButton addButton = new JButton("Add Event");

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(new JLabel()); // Spacer
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String date = dateField.getText();
            String location = locationField.getText();

            if (!title.isEmpty() && !date.isEmpty()) {
                try {
                    String query = "INSERT INTO events (title, date, location) VALUES (?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, title);
                    stmt.setString(2, date);
                    stmt.setString(3, location);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Event added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    titleField.setText("");
                    dateField.setText("");
                    locationField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createRecordDonationsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel memberIdLabel = new JLabel("Member ID:");
        JTextField memberIdField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JButton recordButton = new JButton("Record Donation");

        panel.add(memberIdLabel);
        panel.add(memberIdField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(new JLabel()); // Spacer
        panel.add(recordButton);

        recordButton.addActionListener(e -> {
            String memberId = memberIdField.getText();
            String amount = amountField.getText();
            String date = dateField.getText();

            if (!memberId.isEmpty() && !amount.isEmpty() && !date.isEmpty()) {
                try {
                    String query = "INSERT INTO donations (member_id, amount, date) VALUES (?, ?, ?)";
                    PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setInt(1, Integer.parseInt(memberId));
                    stmt.setDouble(2, Double.parseDouble(amount));
                    stmt.setString(3, date);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Donation recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    memberIdField.setText("");
                    amountField.setText("");
                    dateField.setText("");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createViewMembersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton refreshButton = new JButton("Refresh");
        JTable table = new JTable(new DefaultTableModel(new Object[]{"Member ID", "Name", "Address", "Phone", "Email", "Join Date"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);

        refreshButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the table
            try {
                String query = "SELECT * FROM members";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("member_id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getDate("join_date")
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(refreshButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createViewEventsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton refreshButton = new JButton("Refresh");
        JTable table = new JTable(new DefaultTableModel(new Object[]{"Event ID", "Title", "Date", "Location"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);

        refreshButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the table
            try {
                String query = "SELECT * FROM events";
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("event_id"),
                            rs.getString("title"),
                            rs.getDate("date"),
                            rs.getString("location")
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(refreshButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChurchManagementSystem::new);
    }
}