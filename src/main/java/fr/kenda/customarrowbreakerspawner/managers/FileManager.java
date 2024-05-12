package fr.kenda.customarrowbreakerspawner.managers;

import fr.kenda.customarrowbreakerspawner.CustomArrowBreakerSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class FileManager implements IManager {

    private final CustomArrowBreakerSpawner instance = CustomArrowBreakerSpawner.getInstance();
    private final HashMap<String, FileConfiguration> files = new HashMap<>();

    /**
     * Register and create all files
     */
    @Override
    public void register() {
        createFile("messages");
    }

    /**
     * Create file
     *
     * @param fileName String
     */
    public void createFile(String fileName) {
        final File file = new File(instance.getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            instance.saveResource(fileName + ".yml", false);
        }

        FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);
        files.put(fileName, configFile);
    }

    /**
     * Get configuration from file
     *
     * @param fileName String
     * @return FileConfiguration
     */
    public FileConfiguration getConfigFrom(String fileName) {
        return files.get(fileName);
    }
}