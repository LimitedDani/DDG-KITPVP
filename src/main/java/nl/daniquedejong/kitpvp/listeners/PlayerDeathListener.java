package nl.daniquedejong.kitpvp.listeners;

import nl.daniquedejong.kitpvp.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private Plugin plugin;

    /**
     * @param plugin Main class
     */
    public PlayerDeathListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent playerDeathEvent) {
        this.plugin.kitPvP.death(playerDeathEvent.getEntity());
        playerDeathEvent.setKeepInventory(true);
        playerDeathEvent.setKeepLevel(true);
        playerDeathEvent.getEntity().getInventory().clear();
        this.plugin.kitPvP.kill(playerDeathEvent.getEntity().getKiller(), playerDeathEvent.getEntity());
    }
}