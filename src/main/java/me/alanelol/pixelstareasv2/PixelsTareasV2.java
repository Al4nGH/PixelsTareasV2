package me.alanelol.pixelstareasv2;

import me.alanelol.pixelstareasv2.comandos.TareaDiaria;
import me.alanelol.pixelstareasv2.comandos.TestCommand;
import me.alanelol.pixelstareasv2.listeners.OnJoinAndLeave;
import me.alanelol.pixelstareasv2.listeners.TareaEvents;
import me.alanelol.pixelstareasv2.utils.DataHandler;
import me.alanelol.pixelstareasv2.utils.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public final class PixelsTareasV2 extends JavaPlugin {
    /*
    Ver si el jugador tiene su tarea completada y asignarle la siguiente
    Si no, asignarle la primera tarea al jugador
    Si esta en la última, asignarle la primera tarea al jugador

    al unirse un jugador, asignarle su tarea y su util (desde sus datos guardados)
     */

    private final HashMap<UUID, PlayerInfo> playerInfoHashMap = new HashMap<>();
    private File file;
    private FileConfiguration data;
    private final DataHandler dataHandler = new DataHandler(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        // START DATA FILE
        file = new File(getDataFolder()+File.separator+"data.yml");
        data = YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // ETC
        getServer().getPluginManager().registerEvents(new OnJoinAndLeave(this), this);
        getServer().getPluginManager().registerEvents(new TareaEvents(this), this);
        getCommand("tareadiaria").setExecutor(new TareaDiaria(this));
        getCommand("ttest").setExecutor(new TestCommand(this));
        getLogger().info("Iniciado.");
        for (Player player : Bukkit.getOnlinePlayers())
        {
            // si /reload
            new OnJoinAndLeave(this).allocate(player.getUniqueId(), player);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dataHandler.setData(); // SAVE DATA FOR EVERYONE

        for (Player player : Bukkit.getOnlinePlayers())
        {
            // si /reload
            new OnJoinAndLeave(this).deallocate(player.getUniqueId(), player);
        }
    }

    public HashMap<UUID, PlayerInfo> getPlayerInfoHashMap() {return playerInfoHashMap;}
    public FileConfiguration getData() {return data;}
    public File getDataFile() {return file;}
}
