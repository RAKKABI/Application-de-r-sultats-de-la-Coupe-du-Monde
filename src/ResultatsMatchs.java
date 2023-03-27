import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ResultatsMatchs extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;

    public ResultatsMatchs() {
        // Configuration de la fenêtre
        setTitle("Résultats des matchs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuration de la table
        String[] columns = {"Equipe 1", "Score", "Equipe 2", "Score", "Stade", "Date", "phase"};
        Object[][] data = new Object[0][6];
        table = new JTable(data, columns);
        scrollPane = new JScrollPane(table);
        add(scrollPane);

        // Connexion à la base de données et récupération des données des matchs
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupe_du_monde", "username", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Equipes1.nom, Equipes1.code_de_pays, Matchs.score_equipe_1, Equipes2.nom, Equipes2.code_de_pays, Matchs.score_equipe_2, Stades.nom, Stades.ville, Stades.pays, Matchs.date, Phase.nom\n" +
                    "FROM Matchs\n" +
                    "INNER JOIN Equipes AS Equipes1 ON Matchs.id_equipe_1 = Equipes1.id\n" +
                    "INNER JOIN Equipes AS Equipes2 ON Matchs.id_equipe_2 = Equipes2.id\n" +
                    "INNER JOIN Stades ON Matchs.id_stade = Stades.id\n" +
                    "INNER JOIN Phase ON Phase.id_match = Matchs.id;");

            // Parcours des résultats et remplissage de la table
            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getString(5);
                row[5] = rs.getDate(6);
                row[6] = rs.getString(7);
                ((DefaultTableModel) table.getModel()).addRow(row);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ResultatsMatchs resultatsMatchs = new ResultatsMatchs();
        resultatsMatchs.setVisible(true);
    }
}
