package nl.daniquedejong.kitpvp.listeners;

import nl.daniquedejong.kitpvp.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private Plugin plugin;
    /**
     * @param plugin Main class
     */
    public PlayerQuitListener(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent playerQuitEvent) {
        if(this.plugin.kitPvP.isPlayerInGame(playerQuitEvent.getPlayer())) {
            this.plugin.kitPvP.leave(playerQuitEvent.getPlayer(), true);
        }
    }
}
