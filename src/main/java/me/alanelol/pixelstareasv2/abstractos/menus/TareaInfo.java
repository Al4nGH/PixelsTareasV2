package me.alanelol.pixelstareasv2.abstractos.menus;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import me.alanelol.pixelstareasv2.abstractos.MenuAbstract;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TareaInfo extends MenuAbstract {
    public TareaInfo(Player owner, PixelsTareasV2 plugin) {
        this.owner = owner;
        this.plugin = plugin;
        this.playerInfo = plugin.getPlayerInfoHashMap().get(owner.getUniqueId());
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public Component getTitle() {
        return MiniMessage.miniMessage().deserialize("Tarea Actual");
    }

    @Override
    public void setItems() {
        // GLASS PANES
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
        inventory.setItem(9, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        inventory.setItem(17, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        for (int i = 18; i < 27; i++) {
            inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }

        // 11: PAPEL

        //                          PAPEL
        ItemStack papel = new ItemStack(Material.PAPER);
        ItemMeta papelMeta = papel.getItemMeta();
            // NAME
            String tareaName = playerInfo.getTareaName();
            String tareaTarea = "<white><italic:false><bold><aqua>" + tareaName.substring(0, 5);
            String tareaSeparator = " ";
            String tareaNumber = "<bold:false><white>#<bold>" + tareaName.charAt(5);
            Component papelName1 = MiniMessage.miniMessage().deserialize(tareaTarea+tareaSeparator+tareaNumber);
            papelMeta.displayName(papelName1);
            // LORE
            papelMeta.lore(List.of(MiniMessage.miniMessage().deserialize("<gray><italic:false>Las tareas escalan por tu <white>racha <gray>de días activ@!")));
            papel.setItemMeta(papelMeta);
        inventory.setItem(11, papel);

        // 13: BLOQUE

        //                          BLOQUE
        ItemStack bloque = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta bloqueMeta = (SkullMeta) bloque.getItemMeta();
        bloqueMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Wood"));
            // NAME
            String bloqueName = "<white><italic:false><bold><gradient:aqua:green>Bloques minados<white><bold:false>:";
            bloqueMeta.displayName(MiniMessage.miniMessage().deserialize(bloqueName));
            // LORE
            List<Component> bloqueLoreList = new ArrayList<>();
            for (Map.Entry<Material, Integer> entry : playerInfo.getBlocksRequired().entrySet())
            {
                Material material = entry.getKey();

                String blockLore = "<white><italic:false><gradient:yellow:gold>" +material.toString() + ": <bold><green>";
                // "DIAMOND_BLOCK: |||||||||||"
                for (int i = 0; i < playerInfo.getBlockProgress(material); i++)
                {
                    blockLore = blockLore.concat("|");
                }
                blockLore = blockLore.concat("<gray>");
                for (int i = 0; i < 10 - playerInfo.getBlockProgress(material); i++)
                {
                    blockLore = blockLore.concat("|");
                }

                bloqueLoreList.add(MiniMessage.miniMessage().deserialize(blockLore));
            }
            bloqueMeta.lore(bloqueLoreList);
            bloque.setItemMeta(bloqueMeta);
        inventory.setItem(13, bloque);

        // 14: KILLS

        ItemStack calavera = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta calaveraMeta = (SkullMeta) calavera.getItemMeta();
        calaveraMeta.setOwningPlayer(Bukkit.getOfflinePlayer("ZeeFear"));

            // NOMBRE
            String calaveraName = "<white><italic:false><bold><gradient:aqua:green>Kills<white><bold:false>:";
            calaveraMeta.displayName(MiniMessage.miniMessage().deserialize(calaveraName));

            // LORE
            String calaveraLoreString = "<white><italic:false><gradient:yellow:gold>Progreso: <bold><green>";
            for (int i = 0; i < playerInfo.getKillsProgress(); i++)
            {
                calaveraLoreString = calaveraLoreString.concat("|");
            }
            calaveraLoreString = calaveraLoreString.concat("<gray>");
            for (int i = 0; i < 10 - playerInfo.getKillsProgress(); i++)
            {
                calaveraLoreString = calaveraLoreString.concat("|");
            }
            Component calaveraLoreComponent = MiniMessage.miniMessage().deserialize(calaveraLoreString);
            calaveraMeta.lore(List.of(calaveraLoreComponent));
        calavera.setItemMeta(calaveraMeta);
        inventory.setItem(14, calavera);

        // 15: PLAYTIME

        ItemStack cabeza = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta cabezaMeta = (SkullMeta) cabeza.getItemMeta();
        cabezaMeta.setOwningPlayer(Bukkit.getOfflinePlayer("DavidGriffiths"));
        // NOMBRE
        String cabezaName = "<white><italic:false><bold><gradient:aqua:green>Playtime<white><bold:false>:";
        cabezaMeta.displayName(MiniMessage.miniMessage().deserialize(cabezaName));
        // LORE
        String cabezaLoreString = "<white><italic:false><gradient:yellow:gold>Progreso: <bold><green>";
        for (int i = 0; i < playerInfo.getPlaytimeProgress(); i++)
        {
            cabezaLoreString = cabezaLoreString.concat("|");
        }
        cabezaLoreString = cabezaLoreString.concat("<gray>");
        for (int i = 0; i < 10 - playerInfo.getPlaytimeProgress(); i++)
        {
            cabezaLoreString = cabezaLoreString.concat("|");
        }
        Component cabezaLoreComponent = MiniMessage.miniMessage().deserialize(cabezaLoreString);
        cabezaMeta.lore(List.of(cabezaLoreComponent));
        cabeza.setItemMeta(cabezaMeta);
        inventory.setItem(15, cabeza);
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event) {

    }
}
