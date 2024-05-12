package fr.kenda.customarrowbreakerspawner.commands;

import fr.kenda.customarrowbreakerspawner.utils.Config;
import fr.kenda.customarrowbreakerspawner.utils.ItemBuilder;
import fr.kenda.customarrowbreakerspawner.utils.Messages;
import fr.kenda.customarrowbreakerspawner.utils.Permission;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveArrowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Messages.getPrefix() + "§cSeul un joueur peut faire cette commande.");
            return false;
        }
        if (!player.hasPermission(Permission.GIVE_ARROW)) {
            player.sendMessage(Messages.getMessage("no_permission"));
            return false;
        }

        if (strings.length != 1) {
            sendHelp(player);
            return true;
        }

        String arg = strings[0];
        if (!arg.equalsIgnoreCase("explosive") && !arg.equalsIgnoreCase("teleport")) {
            player.sendMessage(Messages.transformColor("&cArgument invalide. Utilisez /givearrow <explosive/teleport>"));
            return false;
        }

        ItemStack itemStack = createItemStack(arg);

        if (itemStack != null) {
            player.getInventory().addItem(itemStack);
            player.sendMessage(Messages.getMessage("received_arrow", "{arrow}", itemStack.getItemMeta().getDisplayName()));
            return true;
        }
        return false;
    }

    void sendHelp(Player player) {
        player.sendMessage(Messages.transformColor("&c/givearrow <explosive/teleport>: &7Give la flèche de téléportation ou d'explosion"));
    }

    private ItemStack createItemStack(String arg) {
        String name;
        List<String> lores;

        switch (arg.toLowerCase()) {
            case "explosive":
                name = Config.getString("arrow.explosive.name");
                lores = Config.getList("arrow.explosive.lores");
                break;
            case "teleport":
                name = Config.getString("arrow.teleport.name");
                lores = Config.getList("arrow.teleport.lores");
                break;
            default:
                return null;
        }

        return new ItemBuilder(Material.ARROW).setName(name)
                .setLore(lores).toItemStack();
    }
}

