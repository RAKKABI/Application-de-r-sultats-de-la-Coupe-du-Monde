import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class InsertStadesForm extends JFrame implements ActionListener {

    private JLabel lblNom, lblVille, lblPays;
    private JTextField txtNom, txtVille, txtPays;
    private JButton btnInsert;

    public InsertStadesForm() {
        setTitle("Insertion des stades");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        lblNom = new JLabel("Nom:");
        txtNom = new JTextField();
        lblVille = new JLabel("Ville:");
        txtVille = new JTextField();
        lblPays = new JLabel("Pays:");
        txtPays = new JTextField();
        btnInsert = new JButton("Insérer");

        add(lblNom);
        add(txtNom);
        add(lblVille);
        add(txtVille);
        add(lblPays);
        add(txtPays);
        add(btnInsert);

        btnInsert.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInsert) {
            MatchForm i = new MatchForm();
            i.setVisible(true);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/coupe_du_monde", "root", "");
                Statement stmt = con.createStatement();

                String nom = txtNom.getText();
                String ville = txtVille.getText();
                String pays = txtPays.getText();

                String sql = "INSERT INTO stades (nom, ville, pays) VALUES ('" + nom + "', '" + ville + "', '" + pays + "')";
                stmt.executeUpdate(sql);

                con.close();
                JOptionPane.showMessageDialog(this, "Stade ajouté avec succès");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new InsertStadesForm();
    }
}
