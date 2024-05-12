package fr.kenda.customarrowbreakerspawner.events;

import fr.kenda.customarrowbreakerspawner.utils.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;


public class ArrowEventListener implements Listener {

    private final HashMap<UUID, String> arrows = new HashMap<>();

    private static ItemStack getShotArrow(PlayerInventory inventory) {
        for (int slot = 0; slot < inventory.getSize(); ++slot) {
            ItemStack stack = inventory.getItem(slot);

            if (stack != null && stack.getType() == Material.ARROW) {
                return stack;
            }
        }
        return null;
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        ItemStack arrow = getShotArrow(((Player) e.getEntity()).getInventory());
        if (arrow != null && arrow.hasItemMeta()) {
            String nameArrow = arrow.getItemMeta().getDisplayName();
            arrows.computeIfAbsent(e.getProjectile().getUniqueId(), k -> nameArrow);
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e) {

        if (e.getEntity() instanceof Arrow arrow) {

            UUID id = arrow.getUniqueId();
            if (arrows.containsKey(id)) {
                String nameArrow = arrows.get(id);


                if (nameArrow.equalsIgnoreCase(Config.getString("arrow.explosive.name"))) {
                    arrow.getWorld().createExplosion(arrow.getLocation(), Config.getInt("arrow.explosive.force"));
                    arrow.remove();
                } else if (nameArrow.equalsIgnoreCase(Config.getString("arrow.teleport.name"))) {
                    Player p = (Player) arrow.getShooter();
                    Location arrowLoc = arrow.getLocation();
                    Location pLoc = p.getLocation();
                    Location loc = new Location(arrow.getWorld(), arrowLoc.getX(), arrowLoc.getY(), arrowLoc.getZ(), pLoc.getYaw(), pLoc.getPitch());

                    p.teleport(loc);
                    arrow.remove();
                }
            }
        }
    }
}
