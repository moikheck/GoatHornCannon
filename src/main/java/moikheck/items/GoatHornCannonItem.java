package moikheck.items;

import moikheck.GoatHornCannon;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoatHornCannonItem {

    public static ItemStack horn;
    public static ItemMeta itemMeta;
    public static List<String> lore;
    public static GoatHornCannon main;

    public static void initialize(GoatHornCannon plugin) {
        main = plugin;
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Use to blast away your foes!"));
        horn = new ItemStack(Material.GOAT_HORN);
        itemMeta = horn.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eGoat Horn Cannon"));
        itemMeta.setLore(lore);
        String namespace = plugin.getConfig().getString("namespace");
        if (!namespace.equalsIgnoreCase("none")) {
            String key = plugin.getConfig().getString("custom-model-data");
            NamespacedKey namespacedKey = new NamespacedKey(namespace, key);
            itemMeta.setItemModel(namespacedKey);
        }
        horn.setItemMeta(itemMeta);
    }
}
