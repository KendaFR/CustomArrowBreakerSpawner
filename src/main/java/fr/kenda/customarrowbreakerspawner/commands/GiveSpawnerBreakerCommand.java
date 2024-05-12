package fr.kenda.customarrowbreakerspawner.commands;

import fr.kenda.customarrowbreakerspawner.utils.Config;
import fr.kenda.customarrowbreakerspawner.utils.ItemBuilder;
import fr.kenda.customarrowbreakerspawner.utils.Messages;
import fr.kenda.customarrowbreakerspawner.utils.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveSpawnerBreakerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(Messages.getPrefix() + "§cSeul un joueur peut faire cette commande.");
            return false;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission(Permission.GIVE_BREAKER)) {
            player.sendMessage(Messages.getMessage("no_permission"));
            return false;
        }

        String durabilityStr = String.valueOf(Config.getInt("spawnerbreaker.durability"));
        ItemStack item = new ItemBuilder(Config.getMaterial("spawnerbreaker.material"))
                .setName(Config.getString("spawnerbreaker.name"))
                .setLore(Config.getList("spawnerbreaker.lores", "{durability_left}", durabilityStr,
                        "{durability}", durabilityStr))
                .toItemStack();

        item.setDurability((short) (item.getType().getMaxDurability() - Config.getInt("spawnerbreaker.durability")));

        player.getInventory().addItem(item);

        player.sendMessage(Messages.getMessage("spawnerbreaker_receive"));
        return false;
    }

}
