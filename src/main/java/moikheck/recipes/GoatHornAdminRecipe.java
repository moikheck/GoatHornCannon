package moikheck.recipes;

import moikheck.GoatHornCannon;
import moikheck.items.GoatHornAdminCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class GoatHornAdminRecipe {

    public GoatHornCannon main;
    public static ShapedRecipe shapedRecipe;

    public GoatHornAdminRecipe (GoatHornCannon main) {
        this.main = main;
        shapedRecipe = new ShapedRecipe(new NamespacedKey(main,"admingoathorncannon"), GoatHornAdminCannonItem.horn);
        shapedRecipe.shape("XXX"," O "," D ");
        shapedRecipe.setIngredient('X', Material.FEATHER);
        shapedRecipe.setIngredient('O', Material.GOAT_HORN);
        shapedRecipe.setIngredient('D', Material.NETHERITE_INGOT);

        Bukkit.addRecipe(shapedRecipe);
    }

}
