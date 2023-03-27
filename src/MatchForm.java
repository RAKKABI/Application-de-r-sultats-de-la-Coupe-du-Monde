import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MatchForm extends JFrame implements ActionListener {private JLabel equipe1Label, equipe2Label, stadeLabel, dateLabel, score1Label, score2Label;
    private JComboBox<Integer> equipe1Field, equipe2Field, stadeField;
    private JTextField dateField, score1Field, score2Field;
    private JButton submitButton;

    public MatchForm() {
        setTitle("Add Match");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        equipe1Label = new JLabel("id d'Equipe 1");
        equipe1Field = new JComboBox<Integer>();
        equipe2Label = new JLabel("id d'Equipe 2");
        equipe2Field = new JComboBox<Integer>();
        stadeLabel = new JLabel("id du Stade");
        stadeField = new JComboBox<Integer>();
        dateLabel = new JLabel("Date (YYYY-MM-DD)");
        dateField = new JTextField();
        score1Label = new JLabel("Score Equipe 1");
        score1Field = new JTextField();
        score2Label = new JLabel("Score Equipe 2");
        score2Field = new JTextField();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(equipe1Label);
        panel.add(equipe1Field);
        panel.add(equipe2Label);
        panel.add(equipe2Field);
        panel.add(stadeLabel);
        panel.add(stadeField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(score1Label);
        panel.add(score1Field);
        panel.add(score2Label);
        panel.add(score2Field);
        panel.add(submitButton);

        setContentPane(panel);
        setVisible(true);

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupe_du_monde", "root", "");
            PreparedStatement ps1 = conn.prepareStatement("SELECT id FROM Equipes");
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                equipe1Field.addItem(rs1.getInt("id"));
                equipe2Field.addItem(rs1.getInt("id"));
            }
            PreparedStatement ps2 = conn.prepareStatement("SELECT id FROM Stades");
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                stadeField.addItem(rs2.getInt("id"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        MatchForm form = new MatchForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Clear the combo boxes
            equipe1Field.removeAllItems();
            equipe2Field.removeAllItems();
            stadeField.removeAllItems();
            PhaseForm i = new PhaseForm();
            i.setVisible(true);

            try {
                // Load the new data into the combo boxes
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupe_du_monde", "root", "");
                PreparedStatement ps1 = conn.prepareStatement("SELECT id FROM Equipes");
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    equipe1Field.addItem(rs1.getInt("id"));
                    equipe2Field.addItem(rs1.getInt("id"));
                }
                PreparedStatement ps2 = conn.prepareStatement("SELECT id FROM Stades");
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    stadeField.addItem(rs2.getInt("id"));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
            }

        }
    }}