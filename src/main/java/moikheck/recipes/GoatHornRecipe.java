package moikheck.recipes;

import moikheck.GoatHornCannon;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class GoatHornRecipe {

    public GoatHornCannon main;
    public static ShapedRecipe shapedRecipe;

    public GoatHornRecipe (GoatHornCannon main) {
        this.main = main;
        shapedRecipe = new ShapedRecipe(new NamespacedKey(main,"goathorncannon"), GoatHornCannonItem.horn);
        shapedRecipe.shape("XXX"," O "," D ");
        shapedRecipe.setIngredient('X', Material.FEATHER);
        shapedRecipe.setIngredient('O', Material.GOAT_HORN);
        shapedRecipe.setIngredient('D', Material.DIAMOND);

        Bukkit.addRecipe(shapedRecipe);
    }

}
