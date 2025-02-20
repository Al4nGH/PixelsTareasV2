package me.alanelol.pixelstareasv2.listeners;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import me.alanelol.pixelstareasv2.utils.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class TareaEvents implements Listener {
    public TareaEvents(PixelsTareasV2 plugin)
    {
        this.plugin = plugin;
    }
    private final PixelsTareasV2 plugin;

    // BLOQUES
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Material materialMined = event.getBlock().getType();
        PlayerInfo playerInfo = plugin.getPlayerInfoHashMap().get(player.getUniqueId());

        if (playerInfo.isSameDay())
        {
            // LOOPEAR LOS MATERIALES REQUERIDOS EN LA TAREA
            for (Material materialRequired : playerInfo.getBlocksRequired().keySet())
            {
                if (materialRequired.equals(materialMined)) // minó un material correcto?
                {
                    HashMap<Material, Integer> blocksMined = playerInfo.getBlocksMined(); // HASHMAP

                    if (blocksMined.containsKey(materialMined)) // HASHMAP CONTAINS THE BLOCK? SUMALO
                    {
                        blocksMined.replace(materialMined, blocksMined.get(materialMined) + 1); // SUMARLO
                    }
                    else // HASHMAP DOES NOT CONTAIN THE BLOCK MINED, PUT IT
                    {
                        blocksMined.put(materialMined, 1); // PUT IT
                    }

                    playerInfo.setBlocksMined(blocksMined); // SAVE ON THE ORIGINAL TABLE
                }
            }
        }

        playerInfo.updateVariableStatuses();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        if (victim.getKiller() != null) // KILLED BY PLAYER
        {
            Player killer = victim.getKiller();
            PlayerInfo killerInfo = plugin.getPlayerInfoHashMap().get(killer.getUniqueId());

            if (killerInfo.isSameDay())
            {
                killerInfo.setPlayerKills(killerInfo.getPlayerKills() + 1);
                killerInfo.updateVariableStatuses();
            }
        }
    }
}
