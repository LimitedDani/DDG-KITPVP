package nl.daniquedejong.kitpvp.listeners;

import nl.daniquedejong.kitpvp.Plugin;
import nl.daniquedejong.kitpvp.game.GameStatus;
import nl.daniquedejong.kitpvp.game.KitMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignClickListener implements Listener {
    private Plugin plugin;
    public SignClickListener(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerSignJoinEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getClickedBlock() != null) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.SIGN || block.getType() == Material.WALL_SIGN) {
                if (event.getClickedBlock().getState() != null) {
                    if (event.getClickedBlock().getState() instanceof Sign) {
                        Sign sign = (Sign) event.getClickedBlock().getState();
                        if (sign.getLines()[0].equalsIgnoreCase("[KitPVP]")) {
                            KitMenu kitMenu = new KitMenu(this.plugin);
                            kitMenu.initializeItems();
                            kitMenu.openInventory(event.getPlayer());
                        }
                    }
                }
            }
        }
    }
}
