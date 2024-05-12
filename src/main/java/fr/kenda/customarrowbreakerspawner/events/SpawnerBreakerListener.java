package fr.kenda.customarrowbreakerspawner.events;

import com.bgsoftware.wildstacker.api.WildStacker;
import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedSpawner;
import fr.kenda.customarrowbreakerspawner.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerBreakerListener implements Listener {


    /**
     * Reduce durability of item
     *
     * @param event    PlayerInteractEvent
     * @param item     ItemStack
     * @param itemMeta ItemMeta
     */
    private static void decreaseDurability(PlayerInteractEvent event, ItemStack item, ItemMeta itemMeta)
    {

        int duraUse = Config.getInt("spawnerbreaker.durability");
        int itemMaxDura = item.getType().getMaxDurability();

        String durabilityStr = String.valueOf(duraUse);

        short newDurability = (short) (item.getDurability() + 1);
        itemMeta.setLore(Config.getList("spawnerbreaker.lores", "{durability_left}", String.valueOf(itemMaxDura - newDurability), "{durability}", durabilityStr));
        item.setItemMeta(itemMeta);

        item.setDurability(newDurability);
        if (newDurability >= itemMaxDura)
            event.getPlayer().getInventory().remove(item);
    }

    /**
     * Perform the action when he clicked
     *
     * @param event PlayerInteractEvent
     * @param block Block
     */
    private static void performAction(PlayerInteractEvent event, Block block) {
        WildStacker stacker = WildStackerAPI.getWildStacker();
        StackedSpawner spawner = stacker.getSystemManager().getStackedSpawner(block.getLocation());
        if (spawner == null)
            return;

        int amountSpawner = spawner.getStackAmount();
        EntityType entity = spawner.getSpawner().getSpawnedType();

        Player player = event.getPlayer();
        String cmd = String.format("stacker give %s spawner %s %d", player.getName(), entity.name(), amountSpawner);

        Server server = Bukkit.getServer();
        server.dispatchCommand(server.getConsoleSender(), cmd);

        spawner.remove();
        block.setType(Material.AIR);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        ItemStack item = event.getItem();

        if (block == null || item == null) return;
        if (block.getType() != Material.MOB_SPAWNER || item.getType() != Config.getMaterial("spawnerbreaker.material"))
            return;

        ItemMeta itemMeta = item.getItemMeta();
        if (Config.getBoolean("spawnerbreaker.custom_name")) {
            if (itemMeta == null || !itemMeta.getDisplayName().equalsIgnoreCase(Config.getString("spawnerbreaker.name")))
                return;
        }

        performAction(event, block);

        decreaseDurability(event, item, itemMeta);
    }
}
