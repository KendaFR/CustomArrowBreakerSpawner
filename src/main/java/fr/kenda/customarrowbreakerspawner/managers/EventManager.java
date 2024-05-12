package fr.kenda.customarrowbreakerspawner.managers;

import fr.kenda.customarrowbreakerspawner.CustomArrowBreakerSpawner;
import fr.kenda.customarrowbreakerspawner.events.ArrowEventListener;
import org.bukkit.plugin.PluginManager;

public class EventManager implements IManager {
    @Override
    public void register() {
        final CustomArrowBreakerSpawner instance = CustomArrowBreakerSpawner.getInstance();
        final PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new ArrowEventListener(), instance);
    }
}
