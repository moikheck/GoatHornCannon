package moikheck.listeners;

import moikheck.GoatHornCannon;
import moikheck.items.GoatHornAdminCannonItem;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;

import java.util.Objects;

public class CraftingListener implements Listener {
    private final boolean usesPermissions;
    private final boolean usesAdminPermissions;

    public CraftingListener(GoatHornCannon plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        usesPermissions = Objects.equals(plugin.getConfig().getString("uses-permissions"), "true");
        usesAdminPermissions = Objects.equals(plugin.getConfig().getString("admin-use-permissions"), "true");
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        HumanEntity he = e.getWhoClicked();
        CraftingInventory ci = e.getInventory();
        if (!(he instanceof Player)) {
            return;
        }
        Player p = (Player) he;
        if (!e.getRecipe().getResult().equals(GoatHornCannonItem.horn) && !e.getRecipe().getResult().equals(GoatHornAdminCannonItem.horn)) {
            return;
        }
        if (ci.contains(GoatHornCannonItem.horn) || ci.contains(GoatHornAdminCannonItem.horn)) {
            return;
        }
        if (e.getRecipe().getResult().equals(GoatHornCannonItem.horn) && !p.hasPermission("goathorncannon.craft") && usesPermissions) {
            e.setCancelled(true);
            return;
        }
        if (e.getRecipe().getResult().equals(GoatHornAdminCannonItem.horn) && !p.hasPermission("goathorncannon.admin.craft") && usesAdminPermissions) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        if (e.getRecipe() == null) {
            return;
        }
        HumanEntity he = e.getViewers().get(0);
        CraftingInventory ci = e.getInventory();
        if (!(he instanceof Player)) {
            return;
        }
        Player p = (Player) he;
        if (!Objects.requireNonNull(e.getRecipe()).getResult().equals(GoatHornCannonItem.horn) && !e.getRecipe().getResult().equals(GoatHornAdminCannonItem.horn)) {
            return;
        }
        if (ci.contains(GoatHornCannonItem.horn) || ci.contains(GoatHornAdminCannonItem.horn)) {
            return;
        }
        if (e.getRecipe().getResult().equals(GoatHornCannonItem.horn) && !p.hasPermission("goathorncannon.craft") && usesPermissions) {
            ci.setResult(null);
            return;
        }
        if (e.getRecipe().getResult().equals(GoatHornAdminCannonItem.horn) && !p.hasPermission("goathorncannon.admin.craft") && usesAdminPermissions) {
            ci.setResult(null);
        }
    }
}
