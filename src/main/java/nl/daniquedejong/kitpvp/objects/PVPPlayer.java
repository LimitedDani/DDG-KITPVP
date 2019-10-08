package nl.daniquedejong.kitpvp.objects;

import org.bukkit.entity.Player;

public class PVPPlayer {
    private int kills;
    private int deaths;

    public PVPPlayer(int kills, int deaths) {
        this.kills = kills;
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
