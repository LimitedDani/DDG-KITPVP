package nl.daniquedejong.kitpvp;

import nl.daniquedejong.kitpvp.game.KitMenu;
import nl.daniquedejong.kitpvp.game.KitPvP;
import nl.daniquedejong.kitpvp.listeners.*;
import nl.daniquedejong.kitpvp.mysql.MySQL;
import nl.daniquedejong.kitpvp.signs.Signs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.SQLException;

public class Plugin extends JavaPlugin implements Listener {
    private File customConfigFile;
    private FileConfiguration customConfig;
    private String customConfigFileName = "config.yml";
    public Signs signs;
    public KitPvP kitPvP;
    public MySQL mysql;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        createCustomConfig();
        openConnection();
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(this), this);
        pluginManager.registerEvents(new SignClickListener(this), this);
        pluginManager.registerEvents(new PlayerRespawnListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
        pluginManager.registerEvents(new PlayerDeathListener(this), this);
        pluginManager.registerEvents(new KitMenu(this), this);

        kitPvP = new KitPvP(this);
        kitPvP.init();
        signs = new Signs(this);
        signs.init();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
        super.onDisable();
    }

    public void openConnection()
    {
        this.mysql = new MySQL(this.getCustomConfig().getString("database.host"), this.getCustomConfig().getString("database.port"), this.getCustomConfig().getString("database.database"), this.getCustomConfig().getString("database.user"), this.getCustomConfig().getString("database.password"));
        try
        {
            this.mysql.openConnection();
        }
        catch (ClassNotFoundException | SQLException e )
        {
            System.err.println("MySQLservice disabled because of: (" + e.getMessage() + ").");
        }
    }
    /**
     * @return Config file
     */
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void saveCustomConfig() {
        try {
            this.customConfig.save(this.customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), customConfigFileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource(customConfigFileName, false);
        }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

