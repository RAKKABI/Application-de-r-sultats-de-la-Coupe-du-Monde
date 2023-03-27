import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PhaseForm extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel label;
    private JComboBox<String> combo;
    private JButton button;

    private static final String[] PHASES = {"Phase de groupes", "Huitièmes de finale", "Quarts de finale", "Demi-finales", "Finale"};

    public PhaseForm() {
        setTitle("Formulaire de phase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 100);
        setResizable(false);
        setLayout(new FlowLayout());

        label = new JLabel("Choisir une phase:");
        combo = new JComboBox<String>(PHASES);
        button = new JButton("Enregistrer");

        button.addActionListener(this);

        add(label);
        add(combo);
        add(button);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String phase = (String) combo.getSelectedItem();
            insertPhase(phase);
        }
    }

    private void insertPhase(String phase) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/coupe_du_monde";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);

            String query = "INSERT INTO Phase (nom) VALUES (?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, phase);
            int result = pstmt.executeUpdate();

            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Phase enregistrée avec succès!");
            } else {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement de la phase.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la connexion à la base de données.");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PhaseForm();
    }
}