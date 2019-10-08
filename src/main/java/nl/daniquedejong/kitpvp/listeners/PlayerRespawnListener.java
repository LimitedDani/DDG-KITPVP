package nl.daniquedejong.kitpvp.listeners;

import nl.daniquedejong.kitpvp.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    private Plugin plugin;
    /**
     * @param plugin Main class
     */
    public PlayerRespawnListener(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent playerRespawnEvent ) {
        if (this.plugin.kitPvP.isPlayerInGame(playerRespawnEvent.getPlayer())) {
            this.plugin.kitPvP.respawn(playerRespawnEvent.getPlayer(), playerRespawnEvent);
        }
    }
}
