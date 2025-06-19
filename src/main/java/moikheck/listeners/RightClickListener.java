package moikheck.listeners;

import moikheck.GoatHornCannon;
import moikheck.functionality.GoatHornCannonUse;
import moikheck.items.GoatHornAdminCannonItem;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Objects;

public class RightClickListener implements Listener {
    private final boolean usesPermissions;
    private final boolean usesAdminPermissions;
    private final GoatHornCannon main;

    public RightClickListener(GoatHornCannon plugin) {
        main = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        usesPermissions = Objects.equals(plugin.getConfig().getString("uses-permissions"), "true");
        usesAdminPermissions = Objects.equals(plugin.getConfig().getString("admin-use-permissions"), "true");
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        int cooldown = p.getCooldown(Material.GOAT_HORN);
        boolean isGround = event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getLocation().getPitch() >= 45.0;
        boolean isOnGround = !p.getLocation().add(0,-0.1,0).getBlock().getType().isAir();
        boolean isNormalHorn = p.getInventory().getItemInMainHand().equals(GoatHornCannonItem.horn);
        boolean isAdminHorn = p.getInventory().getItemInMainHand().equals(GoatHornAdminCannonItem.horn);
        String type = isNormalHorn ? "normal" : "admin";
        String direction = isGround && isOnGround ? "ground" : "air";

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }
        if (!isNormalHorn && !isAdminHorn) {
            return;
        }
        if (cooldown != 0) {
            event.setCancelled(true);
            return;
        }
        if (isNormalHorn && !p.hasPermission("goathorncannon.use") && usesPermissions) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this."));
            return;
        }
        if (isAdminHorn && !p.hasPermission("goathorncannon.admin.use") && usesAdminPermissions) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this."));
            return;
        }

        List<String> disabledWorlds = main.getConfig().getStringList("disabled-worlds");
        if (isNormalHorn && disabledWorlds.contains(p.getWorld().getName())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cGoat horn cannons are disabled in this world."));
            return;
        }
        if (isAdminHorn && disabledWorlds.contains(p.getWorld().getName())) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cGoat horn cannons are disabled in this world."));
            return;
        }

        GoatHornCannonUse.useCannon(p, type, direction, main);

    }

}
