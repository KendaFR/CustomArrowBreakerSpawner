package fr.kenda.customarrowbreakerspawner;

import fr.kenda.customarrowbreakerspawner.managers.CommandManager;
import fr.kenda.customarrowbreakerspawner.managers.EventManager;
import fr.kenda.customarrowbreakerspawner.managers.FileManager;
import fr.kenda.customarrowbreakerspawner.managers.IManager;

import java.util.HashMap;
import java.util.Map;

public class Managers {

    private final Map<Class<? extends IManager>, IManager> managers = new HashMap<>();

    public Managers() {
        registerManager(CommandManager.class, new CommandManager());
        registerManager(EventManager.class, new EventManager());
        registerManager(FileManager.class, new FileManager());

        managers.forEach((aClass, iManager) -> iManager.register());
    }

    <T extends IManager> void registerManager(Class<T> managerClass, T managerInstance) {
        managers.putIfAbsent(managerClass, managerInstance);
    }

    @SuppressWarnings("unchecked")
    public <T extends IManager> T getManager(Class<T> managerClass) {
        return (T) managers.get(managerClass);
    }

}
