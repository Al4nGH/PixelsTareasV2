package me.alanelol.pixelstareasv2.abstractos;

import me.alanelol.pixelstareasv2.utils.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Tareas {
    // COMO FUNCIONAN LAS TAREAS (COMO HACERLAS)
    // SOLO SE PUEDEN HASTA 7
    // para añadir un bloque requerido, simplemente es con la linea
    // blocksRequired.put(Material.{ID DEL MATERIAL}, {CANTIDAD})
    // para poner las kills requeridas y segundos requeridos (playtime)
    // es un poco obvio no te voy a mentir
    // LOS PREMIOS ESTAN ES LOS MÉTODOS tareaXComplete, se puede hacer lo que sea
    public void tarea1Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.OAK_LOG, 20);
        blocksRequired.put(Material.STONE, 64);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(2);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea1Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }


    public void tarea2Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.DIRT, 120);
        blocksRequired.put(Material.GRASS_BLOCK, 20);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(3);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea2Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }


    public void tarea3Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.STONE, 128);
        blocksRequired.put(Material.DEEPSLATE, 128);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(4);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea3Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }


    public void tarea4Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.NETHERITE_BLOCK, 20);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(0);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea4Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }


    public void tarea5Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.EMERALD_BLOCK, 20);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(0);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea5Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }


    public void tarea6Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.IRON_BLOCK, 20);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(0);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea6Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }


    public void tarea7Values(PlayerInfo playerInfo)
    {
        HashMap<Material, Integer> blocksRequired = new HashMap<>();
        blocksRequired.put(Material.WITHER_ROSE, 20);
        playerInfo.setBlocksRequired(blocksRequired);
        playerInfo.setKillsRequired(0);
        playerInfo.setSecondsRequired(120);
    }
    public void tarea7Complete(Player player)
    {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));
    }
}
