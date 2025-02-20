package me.alanelol.pixelstareasv2.listeners;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import me.alanelol.pixelstareasv2.utils.DataHandler;
import me.alanelol.pixelstareasv2.utils.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class OnJoinAndLeave implements Listener {

    private final PixelsTareasV2 plugin;

    public OnJoinAndLeave(PixelsTareasV2 plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();
        allocate(uuid, event.getPlayer());


        PlayerInfo playerinfo = plugin.getPlayerInfoHashMap().get(event.getPlayer().getUniqueId());

        // Update playtime
        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getPlayer().isOnline())
                {
                    if (playerinfo.isSameDay())
                    {
                        playerinfo.setSecondsPlayed(playerinfo.getSecondsPlayed() + 1);
                        playerinfo.updateVariableStatuses();
                    }
                }
                else
                {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        UUID uuid = event.getPlayer().getUniqueId();

        deallocate(uuid, event.getPlayer());
    }

    public void allocate(UUID uuid, Player player)
    {
        DataHandler dataHandler = new DataHandler(plugin);
        // Asignar el player con su tarea y su info en sus mapas respectivos
        // (si es que existe, no hacer nada, para evitar pérdida de datos)

        if (plugin.getData().get(uuid.toString()) == null) // si no existe el save data
        {
            dataHandler.setDefaultsOnFile(player); // los defaults

        }
        PlayerInfo playerInfo = new PlayerInfo(uuid, plugin, player); // CARGAR DESDE EL SAVE DATA

        if (!plugin.getPlayerInfoHashMap().containsKey(uuid)) // si no existe la tarea
        {
            plugin.getPlayerInfoHashMap().put(uuid, playerInfo); // asignarla, bien :)
        }
        else // D:
        {
            plugin.getLogger().warning("WARNING DE DATOS PARA " + uuid + " IMAP");
        }
    }

    public void deallocate(UUID uuid, Player player)
    {
        DataHandler dataHandler = new DataHandler(plugin);

        dataHandler.setData(player); // SAVE DATA FOR PLAYER
        plugin.getPlayerInfoHashMap().remove(uuid); // borrala
    }
}
