package nl.daniquedejong.kitpvp.signs;

import nl.daniquedejong.kitpvp.Plugin;
import nl.daniquedejong.kitpvp.Utils;
import org.bukkit.Location;

import java.util.*;

public class Signs {
    private Plugin plugin;
    private List<SignUpdater> signUpdaters;
    /**
     * @param plugin Main class
     */
    public Signs(Plugin plugin) {
        this.plugin = plugin;
        this.signUpdaters = new ArrayList<SignUpdater>();
    }
    public void init() {
        Set<String> signs = this.plugin.getCustomConfig().getConfigurationSection("kitpvp.signs").getKeys(false);
        for(String i : signs) {
            Location signLocation = Utils.configLocationToLocation(this.plugin.getCustomConfig(), "kitpvp.signs." + i);
            SignUpdater signUpdater = new SignUpdater(this.plugin, signLocation);
            signUpdaters.add(signUpdater);
        }
    }
}
