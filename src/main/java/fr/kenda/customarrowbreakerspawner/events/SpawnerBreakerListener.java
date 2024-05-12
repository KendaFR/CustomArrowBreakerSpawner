package fr.kenda.customarrowbreakerspawner.events;

import fr.kenda.customarrowbreakerspawner.utils.Config;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerBreakerListener implements Listener {

    private static void performAction(PlayerInteractEvent event, Block block) {
        CreatureSpawner spawner = (CreatureSpawner) block.getState();

        ItemStack spawnerItem = new ItemStack(Material.MOB_SPAWNER);
        ItemMeta meta = spawnerItem.getItemMeta();
        meta.setDisplayName(spawner.getSpawnedType().toString());
        spawnerItem.setItemMeta(meta);

        event.setCancelled(true);

        block.setType(Material.AIR, true);
        block.getState().update();

        Player player = event.getPlayer();
        player.getWorld().dropItemNaturally(block.getLocation(), spawnerItem);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();
        if (block != null && item != null && block.getType() == Material.MOB_SPAWNER
                && item.getType() == Config.getMaterial("spawnerbreaker.material")) {

            if (Config.getBoolean("spawnerbreaker.custom_name")) {
                if (!item.hasItemMeta()) return;
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Config.getString("spawnerbreaker.name"))) {
                    performAction(event, block);
                }
            } else {
                performAction(event, block);
            }

        }
    }

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.MOB_SPAWNER) {
            ItemStack spawnerItem = event.getItemInHand();
            if (spawnerItem.hasItemMeta()) {
                String entityType = spawnerItem.getItemMeta().getDisplayName();
                CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
                spawner.setSpawnedType(EntityType.valueOf(entityType));
                spawner.update();
            }
        }
    }
}
