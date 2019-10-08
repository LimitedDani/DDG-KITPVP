package nl.daniquedejong.kitpvp.game;

import nl.daniquedejong.kitpvp.Plugin;
import nl.daniquedejong.kitpvp.SQLUtils;
import nl.daniquedejong.kitpvp.Utils;
import nl.daniquedejong.kitpvp.objects.Kit;
import nl.daniquedejong.kitpvp.objects.PVPPlayer;
import nl.daniquedejong.kitpvp.signs.SignUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class KitPvP {
    private Plugin plugin;
    private Map<Player, PVPPlayer> players = new HashMap<>();
    private Location spawnLocation;
    public GameStatus gameStatus;
    public int maxPlayers;
    public List<Location> spawns = new ArrayList<>();
    public List<Kit> kits = new ArrayList<>();

    public KitPvP(Plugin plugin) {
        this.plugin = plugin;
        this.gameStatus = GameStatus.STOPPED;
        this.maxPlayers = 0;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                for (Map.Entry<Player,PVPPlayer> entry : players.entrySet())   {
                    setScoreboard(entry.getKey());
                }
            }
        }, 0L, 60L);
    }
    public void init() {
        this.gameStatus = GameStatus.RUNNING;
        this.maxPlayers = this.plugin.getCustomConfig().getInt("kitpvp.maxplayers");
        this.spawnLocation = Utils.configLocationToLocation(plugin.getCustomConfig(), "kitpvp.spawn");
        this.getSpawnLocations();
        this.getKits();
    }

    public int getPlayers() {
        return players.size();
    }

    public void join(Player player, Kit kit) {
        PVPPlayer pvpPlayer = new PVPPlayer(0, 0);
        players.put(player, pvpPlayer);
        player.teleport(spawns.get(new Random().nextInt(spawns.size())));
        kit.setKit(player);
        this.updateKillsDeaths(player);
    }
    public void kill(Player killer, Player target) {
        if(killer != null) {
            PVPPlayer pvpPlayerKiller = players.get(killer);
            pvpPlayerKiller.setKills((pvpPlayerKiller.getKills() + 1));
            pvpPlayerKiller.setDeaths(pvpPlayerKiller.getDeaths());
            if (players.containsKey(killer)) {
                players.replace(killer, pvpPlayerKiller);
            }
        }

        PVPPlayer pvpPlayerTarget = players.get(target);
        pvpPlayerTarget.setKills(pvpPlayerTarget.getKills());
        pvpPlayerTarget.setDeaths((pvpPlayerTarget.getDeaths()+1));
        if(players.containsKey(pvpPlayerTarget)) {
            players.replace(target, pvpPlayerTarget);
        }
        try {
            SQLUtils.updateKillsAndDeaths(this.plugin, target.getUniqueId(), players.get(target).getKills(), players.get(target).getDeaths());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void death(Player player) {
        removeScoreboard(player);
    }
    public void leave(Player player, boolean serverLeave) {
        removeScoreboard(player);
        if(!serverLeave) {
            player.teleport(spawnLocation);
        }
    }
    public void respawn(Player player, PlayerRespawnEvent playerRespawnEvent) {
        removeScoreboard(player);
        players.remove(player);
        playerRespawnEvent.setRespawnLocation(this.spawnLocation);
    }
    public boolean isPlayerInGame(Player player) {
        return players.containsKey(player);
    }

    private void getSpawnLocations() {
        Set<String> spawnLocations = this.plugin.getCustomConfig().getConfigurationSection("kitpvp.spawns").getKeys(false);
        for(String i : spawnLocations) {
            Location spawnLocation = Utils.configLocationToLocation(this.plugin.getCustomConfig(), "kitpvp.spawns." + i);
            spawns.add(spawnLocation);
        }
    }
    private void setScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective;
        if (scoreboard.getObjective(player.getName()) != null) {
            scoreboard.getObjective(player.getName()).unregister();
        }
        objective = scoreboard.registerNewObjective(player.getName(), "dummy");

        objective.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"DDG Test");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        Score blank1 = objective.getScore(" ");

        Score online = objective.getScore(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD + "Kills");
        Score onlinePlayers = objective.getScore(ChatColor.DARK_GRAY + "» " + ChatColor.GREEN + players.get(player).getKills() + ChatColor.RESET.toString());

        Score blank2 = objective.getScore("   ");

        Score unique = objective.getScore(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD + "Deaths");
        Score uniquePlayers = objective.getScore(ChatColor.DARK_GRAY + "» " + ChatColor.RED + players.get(player).getDeaths());

        blank1.setScore(5);
        online.setScore(4);
        onlinePlayers.setScore(3);
        blank2.setScore(2);
        unique.setScore(1);
        uniquePlayers.setScore(0);

        player.setScoreboard(scoreboard);
    }
    private void removeScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    private void updateKillsDeaths(Player player) {
        try {
            ResultSet rs = SQLUtils.getKillsAndDeaths(this.plugin, player.getUniqueId());
            rs.next();
            int kills = rs.getInt("kills");
            int deaths = rs.getInt("deaths");
            PVPPlayer pvpPlayer = players.get(player);
            pvpPlayer.setKills(kills);
            pvpPlayer.setDeaths(deaths);
            if(players.containsKey(player)) {
                players.replace(player, pvpPlayer);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getKits() {
        Set<String> kitKeys = this.plugin.getCustomConfig().getConfigurationSection("kitpvp.kits").getKeys(false);
        for(String i : kitKeys) {
            Kit kit = new Kit(this.plugin.getCustomConfig(), i);
            kits.add(kit);
        }
    }
}
