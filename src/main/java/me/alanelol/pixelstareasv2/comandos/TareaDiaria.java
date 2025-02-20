package me.alanelol.pixelstareasv2.comandos;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import me.alanelol.pixelstareasv2.abstractos.menus.TareaInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TareaDiaria implements CommandExecutor {
    public TareaDiaria(PixelsTareasV2 plugin)
    {
        this.plugin = plugin;
    }
    private final PixelsTareasV2 plugin;
    // TODO: esto xd
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player)
        {
            TareaInfo tareaInfo = new TareaInfo(player, plugin);
            tareaInfo.open();
        }
        return false;
    }
}
