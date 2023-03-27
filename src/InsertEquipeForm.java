import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class InsertEquipeForm extends JFrame implements ActionListener {

    JLabel labelNom;
    JTextField txtNom;
    JButton btnInsert;

    public InsertEquipeForm() {
        labelNom = new JLabel("Nom de l'équipe:");
        txtNom = new JTextField(20);
        btnInsert = new JButton("Insérer");

        btnInsert.addActionListener(this);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(labelNom);
        panel.add(txtNom);
        panel.add(new JLabel());
        panel.add(btnInsert);

        add(panel, BorderLayout.CENTER);

        setTitle("Formulaire d'insertion d'équipe");
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        String nom = txtNom.getText();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupe_du_monde", "root", "");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Equipes(nom) VALUES('" + nom + "')";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Données insérées avec succès !");
            conn.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        InsertEquipeForm form = new InsertEquipeForm();
        form.setVisible(true);
    }
}
