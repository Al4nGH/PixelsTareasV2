package me.alanelol.pixelstareasv2.abstractos;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import me.alanelol.pixelstareasv2.utils.PlayerInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class MenuAbstract {

    protected Inventory inventory;
    protected Player owner;
    protected PlayerInfo playerInfo;
    protected PixelsTareasV2 plugin;

    public abstract int getRows();

    public abstract Component getTitle();

    public abstract void setItems();

    public abstract void handleClickEvent(InventoryClickEvent event);

    public void open() {
        inventory = Bukkit.createInventory(owner, 9 * getRows(), getTitle());
        setItems();
        owner.openInventory(inventory);
        plugin.getLogger().info("opened inv for " + owner.getName());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getOwner() {
        return owner;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
