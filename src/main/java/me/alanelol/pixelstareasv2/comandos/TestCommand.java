package me.alanelol.pixelstareasv2.comandos;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    private final PixelsTareasV2 plugin;
    public TestCommand(PixelsTareasV2 plugin)
    {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player){
            plugin.getLogger().info(plugin.getPlayerInfoHashMap().toString());
            player.sendMessage(plugin.getPlayerInfoHashMap().toString());
        }
        else
        {
            plugin.getLogger().info(plugin.getPlayerInfoHashMap().toString());
        }
        return false;
    }
}
