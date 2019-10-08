package nl.daniquedejong.kitpvp.game;

import nl.daniquedejong.kitpvp.Plugin;
import nl.daniquedejong.kitpvp.Utils;
import nl.daniquedejong.kitpvp.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitMenu implements InventoryHolder, Listener {
    private final Inventory inv;
    private Plugin plugin;
    public KitMenu(Plugin plugin) {
        this.inv = Bukkit.createInventory(this, 27, "Choose your kit");
        this.plugin = plugin;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void initializeItems() {
        for(Kit kit : this.plugin.kitPvP.kits) {
            inv.setItem(kit.getSlot(), kit.getMenuItem());
        }
    }

    private ItemStack createGuiItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
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

    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (inv.getHolder() != this) {
            return;
        }
        try {
            if (!e.getInventory().getName().equalsIgnoreCase(inv.getTitle())) {
                return;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        if (e.getClick().equals(ClickType.NUMBER_KEY)){
            e.setCancelled(true);
        }
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        for(Kit kit : this.plugin.kitPvP.kits) {
            if(e.getSlot() == kit.getSlot()) {
                this.plugin.kitPvP.join(player, kit);
            }
        }
    }
}
