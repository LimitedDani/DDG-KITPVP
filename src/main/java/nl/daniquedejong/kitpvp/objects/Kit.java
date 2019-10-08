package nl.daniquedejong.kitpvp.objects;

import nl.daniquedejong.kitpvp.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Kit {
    private String name;
    private ItemStack menuItem;
    private int slot;
    private ItemStack helmet, chestPlate, leggings, boots;
    private Map<Integer, ItemStack> items = new HashMap<>();

    public Kit(FileConfiguration config, String kitInteger) throws NullPointerException {
        ConfigurationSection cs = config.getConfigurationSection("kitpvp.kits." + kitInteger);
        ConfigurationSection csHelmet = cs.getConfigurationSection("armor.helmet");
        ConfigurationSection csChestPlate = cs.getConfigurationSection("armor.chestplate");
        ConfigurationSection csLeggings = cs.getConfigurationSection("armor.leggings");
        ConfigurationSection csBoots = cs.getConfigurationSection("armor.boots");
        ConfigurationSection csItems = cs.getConfigurationSection("items");

        this.name = cs.getString("name");

        if(cs.getString("material") != null) {
            System.out.println("0");
            Material material = Material.matchMaterial(cs.getString("material"));
            System.out.println(cs.getString("material"));
            if(material != null) {
                System.out.println("1");
                this.menuItem = Utils.createItem(material, cs.getString("display_name"), 1, cs.getStringList("lore"));
                System.out.println(this.menuItem);
            }
        }
        this.slot = cs.getInt("slot");

        if(csHelmet.getString("material") != null) {
            Material material = Material.matchMaterial(csHelmet.getString("material"));
            if(material != null) {
                this.helmet = Utils.createItem(material, csHelmet.getString("display_name"), csHelmet.getInt("amount"), csHelmet.getStringList("lore"));
            }
        }

        if(csChestPlate.getString("material") != null) {
            Material material = Material.matchMaterial(csChestPlate.getString("material"));
            if(material != null) {
                this.chestPlate = Utils.createItem(material, csChestPlate.getString("display_name"), csChestPlate.getInt("amount"), csChestPlate.getStringList("lore"));
            }
        }

        if(csLeggings.getString("material") != null) {
            Material material = Material.matchMaterial(csLeggings.getString("material"));
            if(material != null) {
                this.leggings = Utils.createItem(material, csLeggings.getString("display_name"), csLeggings.getInt("amount"), csLeggings.getStringList("lore"));
            }
        }

        if(csBoots.getString("material") != null) {
            Material material = Material.matchMaterial(csBoots.getString("material"));
            if(material != null) {
                this.boots = Utils.createItem(material, csBoots.getString("display_name"), csBoots.getInt("amount"), csBoots.getStringList("lore"));
            }
        }

        Set<String> kitItems = csItems.getKeys(false);
        for(String i : kitItems) {
            ConfigurationSection csItem = csItems.getConfigurationSection(i);
            if(csItem.getString("material") != null) {
                Material material = Material.matchMaterial(csItem.getString("material"));
                if(material != null) {
                    ItemStack itemStack = Utils.createItem(material, csItem.getString("display_name"), csItem.getInt("amount"), csItem.getStringList("lore"));
                    this.items.put(Integer.parseInt(i), itemStack);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(ItemStack menuItem) {
        this.menuItem = menuItem;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public ItemStack getChestPlate() {
        return chestPlate;
    }

    public void setChestPlate(ItemStack chestPlate) {
        this.chestPlate = chestPlate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ItemStack> items) {
        this.items = items;
    }

    public void setKit(Player player) {
        player.getInventory().setHelmet(this.helmet);
        player.getInventory().setChestplate(this.chestPlate);
        player.getInventory().setLeggings(this.leggings);
        player.getInventory().setBoots(this.boots);
        for(Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            Integer key = entry.getKey();
            ItemStack value = entry.getValue();
            player.getInventory().setItem(key, value);
        }
    }
}
