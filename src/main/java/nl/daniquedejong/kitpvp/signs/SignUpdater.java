package nl.daniquedejong.kitpvp.signs;

import nl.daniquedejong.kitpvp.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;


public class SignUpdater {

    private Sign sign;
    private Location location;
    /**
     * @param plugin Main class
     * @param location Location of the sign
     */
    public SignUpdater(final Plugin plugin, Location location) {
        this.location = location;
        this.sign = (Sign) this.location.getWorld().getBlockAt(this.location).getState();
        sign.setLine(0, "[KitPVP]");
        sign.setLine(1, "Map: " + "DDG");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                sign.setLine(2, plugin.kitPvP.gameStatus.toString());
                sign.setLine(3, "Players: " + plugin.kitPvP.getPlayers() + " / " + plugin.kitPvP.maxPlayers);
                sign.update();
            }
        }, 0L, 60L);
    }
}
