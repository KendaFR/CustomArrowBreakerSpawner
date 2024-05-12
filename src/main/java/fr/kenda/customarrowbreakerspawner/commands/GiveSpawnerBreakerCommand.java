package fr.kenda.customarrowbreakerspawner.commands;

import fr.kenda.customarrowbreakerspawner.utils.Config;
import fr.kenda.customarrowbreakerspawner.utils.ItemBuilder;
import fr.kenda.customarrowbreakerspawner.utils.Messages;
import fr.kenda.customarrowbreakerspawner.utils.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSpawnerBreakerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Messages.getPrefix() + "§cSeul un joueur peut faire cette commande.");
            return false;
        }
        if (!player.hasPermission(Permission.GIVE_BREAKER)) {
            player.sendMessage(Messages.getMessage("no_permission"));
            return false;
        }

        player.getInventory().addItem(new ItemBuilder(Config.getMaterial("spawnerbreaker.material")).setName(Config.getString("spawnerbreaker.name")).toItemStack());
        player.sendMessage(Messages.getMessage("spawnerbreaker_receive"));
        return false;
    }
}
