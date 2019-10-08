package nl.daniquedejong.kitpvp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class SQLUtils {
    public static void createPlayerRowIfNotExist(Plugin plugin, UUID uuid) throws SQLException {
        String query = "INSERT INTO ddg.player_stats (UUID) " +
                "SELECT * FROM (SELECT '" + uuid.toString() + "') AS tmp " +
                "WHERE NOT EXISTS (" +
                "    SELECT UUID FROM ddg.player_stats WHERE UUID = '" + uuid.toString() + "'" +
                ") LIMIT 1;";
        PreparedStatement preparedStatement = plugin.mysql.getConnection().prepareStatement(query);
        preparedStatement.execute();
        preparedStatement.close();
    }
    public static ResultSet getKillsAndDeaths(Plugin plugin, UUID uuid) throws SQLException {
        String query = "SELECT * FROM ddg.player_stats WHERE uuid='" + uuid.toString()  + "'";
        Statement st = plugin.mysql.getConnection().createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs;
    }
    public static void updateKillsAndDeaths(Plugin plugin, UUID uuid, int kills, int deaths) throws SQLException {
        String query = "UPDATE ddg.player_stats SET kills = " + kills + ", deaths = " + deaths + " WHERE uuid='" + uuid.toString()  + "'";
        PreparedStatement prepareStatement = plugin.mysql.getConnection().prepareStatement(query);
        prepareStatement.executeUpdate();
    }
}
