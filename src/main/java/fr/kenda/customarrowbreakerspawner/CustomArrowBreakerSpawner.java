package fr.kenda.customarrowbreakerspawner;

import fr.kenda.customarrowbreakerspawner.managers.FileManager;
import fr.kenda.customarrowbreakerspawner.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomArrowBreakerSpawner extends JavaPlugin {

    private static CustomArrowBreakerSpawner instance;

    private Managers managers;

    private String prefix;

    public static CustomArrowBreakerSpawner getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        managers = new Managers();
        prefix = Messages.getPrefix();

        Bukkit.getConsoleSender().sendMessage(Messages.transformColor(
                "\n§a########################## " + "\n" +
                        "§a# " + prefix + " §a#" + "\n" +
                        "§a# Merci de m'avoir fait confiance :D #" + "\n" +
                        "§a# Kenda #" + "\n" +
                        "§a##########################"));

    }

    @Override
    public void onDisable() {
    }

    public Managers getManagers() {
        return managers;
    }

    public String getPrefix() {
        return prefix;
    }
}
