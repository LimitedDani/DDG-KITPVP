package nl.daniquedejong.kitpvp.listeners;

import nl.daniquedejong.kitpvp.Plugin;
import nl.daniquedejong.kitpvp.SQLUtils;
import nl.daniquedejong.kitpvp.Utils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {
    private Plugin plugin;
    /**
     * @param plugin Main class
     */
    public PlayerJoinListener(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        Location spawnLocation = Utils.configLocationToLocation(plugin.getCustomConfig(), "kitpvp.spawn");
        playerJoinEvent.getPlayer().teleport(spawnLocation);
        try {
            SQLUtils.createPlayerRowIfNotExist(this.plugin, playerJoinEvent.getPlayer().getUniqueId());
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
