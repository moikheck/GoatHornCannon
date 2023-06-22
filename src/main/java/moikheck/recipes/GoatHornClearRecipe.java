package moikheck.recipes;

import moikheck.GoatHornCannon;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class GoatHornClearRecipe {

    public GoatHornCannon main;
    public static ShapelessRecipe shapelessRecipe;

    public GoatHornClearRecipe (GoatHornCannon main) {
        this.main = main;
        shapelessRecipe = new ShapelessRecipe(new NamespacedKey(main, "goathornclear"), new ItemStack(Material.GOAT_HORN));
        shapelessRecipe.addIngredient(Material.GOAT_HORN);

        Bukkit.addRecipe(shapelessRecipe);
    }
}
