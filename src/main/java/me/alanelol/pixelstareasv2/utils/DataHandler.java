package me.alanelol.pixelstareasv2.utils;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class DataHandler {
    // HACER COSAS CON LOS DATOS
    public DataHandler(PixelsTareasV2 plugin)
    {
        this.plugin = plugin;
        data = plugin.getData();
        file = plugin.getDataFile();
    }
    private final PixelsTareasV2 plugin;
    FileConfiguration data;
    File file;
    String tareaNamePATH = ".tareaName";
    String tareaAssignedDayPATH = ".tareaAssignedDay";
    String blocksMinedParentPATH = ".blocksMined";
    String playerKillsPATH = ".playerKills";
    String secondsPlayed = ".secondsPlayed";

                                // SETTERS
    public void setData() // SAVE DATA FOR ALL PLAYERS
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            setDataForPlayer(player);
        }
    }

    public void setData(Player player) // SAVE DATA FOR SPECIFIC PLAYER
    {
        setDataForPlayer(player);
    }

    private void setDataForPlayer(Player player) // LOCAL SAVE METHOD
    {
        log("SETTING DATA FOR PLAYER " + player.getName());
        HashMap<UUID, PlayerInfo> playerInfoHashMap = plugin.getPlayerInfoHashMap();
        String uuid = player.getUniqueId().toString();
        PlayerInfo playerInfo = playerInfoHashMap.get(player.getUniqueId());

        data.set(uuid + tareaNamePATH, playerInfo.getTareaName());
        data.set(uuid + tareaAssignedDayPATH, playerInfo.getTareaAssignedDay());
        data.set(uuid + playerKillsPATH, playerInfo.getPlayerKills());
        data.set(uuid + secondsPlayed, playerInfo.getSecondsPlayed());
        // BLOCKS MINED
        HashMap<Material, Integer> blocksMined = playerInfo.getBlocksMined(); // get the hashmap
        if (blocksMined != null) // if not null
        {
            for (Map.Entry<Material, Integer> entry : blocksMined.entrySet())
            {
                // for each entry
                data.set(uuid + blocksMinedParentPATH + "." + entry.getKey().toString(), entry.getValue());
            }
        }

        save();
    }

    @SuppressWarnings("deprecation")
    public void setDefaultsOnFile(Player player) // SET THE DEFAULTS IF HAS NO DATA
    {
        log("SETTING DEFAULTS ON FILE");
        String uuid = player.getUniqueId().toString();

        data.set(uuid + tareaNamePATH, "Tarea1");
        data.set(uuid + tareaAssignedDayPATH, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        data.set(uuid + playerKillsPATH, 0);
        data.set(uuid + secondsPlayed, 0);

        // BLOCKS MINED
        HashMap<Material, Integer> blocksMined = new HashMap<>(); // hashMap
        blocksMined.put(Material.AIR, 0);
        for (Map.Entry<Material, Integer> entry : blocksMined.entrySet())
        {
            // for each entry set
            data.set(uuid + blocksMinedParentPATH + "." + entry.getKey().toString(), entry.getValue());
        }

        save();
    }

                                // GETTERS
    public String getTareaNameFromPlayer(Player player)
    {
        log("GETTING TAREA NAME FOR PLAYER " + player.getName());
        String uuid = player.getUniqueId().toString();

        return data.getString(uuid + tareaNamePATH);
    }

    public int getAssignedDay(Player player)
    {
        log("GETTING ASSIGNED DAY FOR PLAYER " + player.getName());
        String uuid = player.getUniqueId().toString();

        return data.getInt(uuid + tareaAssignedDayPATH);
    }

    public int getSecondsPlayed(Player player)
    {
        log("GETTING ASSIGNED INSTANT FOR PLAYER " + player.getName());
        String uuid = player.getUniqueId().toString();

        return data.getInt(uuid + secondsPlayed);
    }

    public int getPlayerKills(Player player)
    {
        log("GETTING PLAYER KILLS FOR PLAYER " + player.getName());
        String uuid = player.getUniqueId().toString();

        return data.getInt(uuid + playerKillsPATH);
    }

    public HashMap<Material, Integer> getBlocksMined(Player player)
    {
        log("GETTING BLOCKS MINED FOR PLAYER " + player.getName());

        String uuid = player.getUniqueId().toString();
        ConfigurationSection configurationSection = data.getConfigurationSection(uuid + blocksMinedParentPATH); // Player's blocksMined section
        HashMap<Material, Integer> blocksMined = new HashMap<>();

        if (configurationSection == null) {return blocksMined;} // si es null
        else // si está todo bien
        {
            for (String key : configurationSection.getKeys(false))
            { // POR CADA KEY
                try
                {
                    Material material = Material.valueOf(key); // CONSEGUIR EL MATERIAL
                    int count = configurationSection.getInt(key); // CUANTOS HA MINADO
                    blocksMined.put(material, count);
                }catch (IllegalArgumentException e)
                {
                    log("ERROR: " + e.getMessage());
                    log("Unknown material found in config: " + key);
                }
            }
        }

        // GET FROM DATA

        log("BLOCKS MINED: " + blocksMined);
        return blocksMined;
    }

    // SAVE FILE
    private void save()
    {
        try {
            data.save(file);
            log("SAVING FILE");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(String string)
    {
        plugin.getLogger().info(string);
    }
}
