package fr.kenda.customarrowbreakerspawner.managers;

import fr.kenda.customarrowbreakerspawner.CustomArrowBreakerSpawner;
import fr.kenda.customarrowbreakerspawner.commands.GiveArrowCommand;
import fr.kenda.customarrowbreakerspawner.commands.GiveSpawnerBreakerCommand;

public class CommandManager implements IManager {
    @Override
    public void register() {

        final CustomArrowBreakerSpawner instance = CustomArrowBreakerSpawner.getInstance();
        instance.getCommand("givearrow").setExecutor(new GiveArrowCommand());
        instance.getCommand("givebreaker").setExecutor(new GiveSpawnerBreakerCommand());
    }
}
