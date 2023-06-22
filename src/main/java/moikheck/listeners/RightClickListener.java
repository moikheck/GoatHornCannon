package moikheck.listeners;

import moikheck.GoatHornCannon;
import moikheck.functionality.GoatHornCannonUse;
import moikheck.items.GoatHornAdminCannonItem;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class RightClickListener implements Listener {
    private final boolean usesPermissions;
    private final boolean usesAdminPermissions;
    private GoatHornCannon main;

    public RightClickListener(GoatHornCannon plugin) {
        main = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        usesPermissions = Objects.equals(plugin.getConfig().getString("uses-permissions"), "true");
        usesAdminPermissions = Objects.equals(plugin.getConfig().getString("admin-use-permissions"), "true");
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Block b = event.getClickedBlock();
        boolean isGround = event.getAction().equals(Action.RIGHT_CLICK_BLOCK);
        boolean isOnGround = !p.getLocation().add(0,-0.1,0).getBlock().getType().isAir();
        boolean isNormalHorn = p.getInventory().getItemInMainHand().equals(GoatHornCannonItem.horn);
        boolean isAdminHorn = p.getInventory().getItemInMainHand().equals(GoatHornAdminCannonItem.horn);
        String type = isNormalHorn ? "normal" : "admin";
        String direction = isGround ? "ground" : "air";


        if (!isNormalHorn && !isAdminHorn) {
            return;
        }
        if (!isOnGround) {
            return;
        }
        if (isNormalHorn && !p.hasPermission("goathorncannon.craft") && usesPermissions) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this."));
            return;
        }
        if (isAdminHorn && !p.hasPermission("goathorncannon.admin.craft") && usesAdminPermissions) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this."));
        }

        if (p.getInventory().getItemInMainHand().equals(GoatHornCannonItem.horn)) {
            GoatHornCannonUse.useCannon(p, type, direction, b, main);
        }

    }

}
