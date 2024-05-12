package fr.kenda.customarrowbreakerspawner.utils;

import fr.kenda.customarrowbreakerspawner.CustomArrowBreakerSpawner;
import fr.kenda.customarrowbreakerspawner.managers.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    private static final CustomArrowBreakerSpawner instance = CustomArrowBreakerSpawner.getInstance();

    /**
     * Obtenir le préfixe du plugin
     *
     * @return String
     */
    public static String getPrefix() {
        return transformColor(instance.getConfig().getString("prefix")) + " ";
    }

    /**
     * Obtenir un message à partir du fichier de messages
     *
     * @param path String
     * @param args String...
     * @return String
     */
    public static String getMessage(String path, String... args) {
        FileConfiguration config = instance.getManagers().getManager(FileManager.class).getConfigFrom("messages");
        if (config == null)
            return transformColor("&c[Messages] Le fichier de messages n'existe pas. Relancez ou restaurez le fichier avant d'exécuter cette commande.");

        String message = config.getString(path);
        if (message == null) return "[Messages] Le chemin d'accès '" + path + "' n'a pas été trouvé dans messages.yml";

        int size = args.length - 1;
        for (int i = 0; i < size; i += 2)
            message = message.replace(args[i], args[i + 1]);

        return getPrefix() + transformColor(message);

    }

    /**
     * Transformer un message avec des couleurs
     *
     * @param message String
     * @return String
     */
    public static String transformColor(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> getMessageList(String path) {
        FileConfiguration config = instance.getManagers().getManager(FileManager.class).getConfigFrom("messages");
        List<String> list = config.getStringList(path);
        List<String> listColored = new ArrayList<>();
        for (String str : list)
            listColored.add(transformColor(str));
        return listColored;
    }
}
