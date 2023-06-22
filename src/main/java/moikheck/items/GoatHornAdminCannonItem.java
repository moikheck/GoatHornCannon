package moikheck.items;

import moikheck.GoatHornCannon;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoatHornAdminCannonItem {

    public static ItemStack horn;
    public static ItemMeta itemMeta;
    public static List<String> lore;
    public static GoatHornCannon main;

    public static void initialize(GoatHornCannon plugin) {
        main = plugin;
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7The Admin Cannon"));
        horn = new ItemStack(Material.GOAT_HORN);
        itemMeta = horn.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cGoat Horn Cannon"));
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(Integer.parseInt(Objects.requireNonNull(main.getConfig().getString("custom-model-data-admin"))));
        horn.setItemMeta(itemMeta);
    }
}
