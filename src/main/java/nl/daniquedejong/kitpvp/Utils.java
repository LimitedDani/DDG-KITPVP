package nl.daniquedejong.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {
    /**
     * @param fileConfiguration Config file
     * @param path Path to the location in the config file
     * @return Location
     */
    public static Location configLocationToLocation(FileConfiguration fileConfiguration, String path) throws NumberFormatException, NullPointerException{
        World world = Bukkit.getWorld(fileConfiguration.getString(path + ".world"));
        int x = fileConfiguration.getInt(path + ".x");
        int y = fileConfiguration.getInt(path + ".y");
        int z = fileConfiguration.getInt(path + ".z");
        long pitch = Optional.ofNullable(fileConfiguration.getLong(path + ".pitch")).orElse(new Long(0));
        long yaw = Optional.ofNullable(fileConfiguration.getLong(path + ".yaw")).orElse(new Long(0));
        return new Location(world, x, y, z, pitch, yaw);
    }

    /**
     * @param material Material
     * @param name Display name for ItemStack
     * @param amount The amount of the material
     * @param lore Lore of the ItemStack
     * @return ItemStack
     */
    public static ItemStack createItem(Material material, String name, int amount, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> metalore = new ArrayList<String>();

        for(String lorecomments : lore) {
            metalore.add(lorecomments);
        }

        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }
}
