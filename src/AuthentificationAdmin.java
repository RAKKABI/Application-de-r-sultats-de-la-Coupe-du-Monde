import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AuthentificationAdmin extends JFrame implements ActionListener {
    JLabel labelTitre, labelNom, labelPassword;
    JTextField fieldNom;
    JPasswordField fieldPassword;
    JButton boutonValider, boutonAnnuler;
    Connection conn;

    public AuthentificationAdmin() {
        setTitle("Authentification d'administrateur");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labelTitre = new JLabel("Veuillez entrer vos identifiants d'administrateur");
        labelNom = new JLabel("Nom d'utilisateur : ");
        labelPassword = new JLabel("Mot de passe : ");

        fieldNom = new JTextField(20);
        fieldPassword = new JPasswordField(20);

        boutonValider = new JButton("Valider");
        boutonAnnuler = new JButton("Annuler");

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel panelTitre = new JPanel(new FlowLayout());
        JPanel panelChamps = new JPanel(new GridLayout(2, 2));
        JPanel panelBoutons = new JPanel(new FlowLayout());

        panelTitre.add(labelTitre);

        panelChamps.add(labelNom);
        panelChamps.add(fieldNom);
        panelChamps.add(labelPassword);
        panelChamps.add(fieldPassword);

        panelBoutons.add(boutonValider);
        panelBoutons.add(boutonAnnuler);

        panelPrincipal.add(panelTitre, BorderLayout.NORTH);
        panelPrincipal.add(panelChamps, BorderLayout.CENTER);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

        add(panelPrincipal);

        boutonValider.addActionListener(this);
        boutonAnnuler.addActionListener(this);

        // establish a connection to the database
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/coupe_du_monde";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonValider) {
            String nom = fieldNom.getText();
            String password = new String(fieldPassword.getPassword());
            InsertEquipeForm i = new InsertEquipeForm();
            i.setVisible(true);

            try {
                // Connexion à la base de données
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupe_du_monde", "root", "");

                // Requête pour récupérer l'utilisateur correspondant aux identifiants entrés
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nom);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                // Vérification que l'utilisateur existe et que le mot de passe est correct
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Authentification réussie !");
                    // Ajoutez ici le code pour ouvrir la fenêtre d'administration
                } else {
                    JOptionPane.showMessageDialog(this, "Identifiants incorrects.");
                }

                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == boutonAnnuler) {
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        AuthentificationAdmin fenetre = new AuthentificationAdmin();
        fenetre.setVisible(true);
    }
}