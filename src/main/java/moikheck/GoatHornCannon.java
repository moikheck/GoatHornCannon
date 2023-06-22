package moikheck;

import moikheck.commands.GiveCannon;
import moikheck.items.GoatHornAdminCannonItem;
import moikheck.items.GoatHornCannonItem;
import moikheck.listeners.CraftingListener;
import moikheck.listeners.RightClickListener;
import moikheck.recipes.GoatHornAdminRecipe;
import moikheck.recipes.GoatHornClearRecipe;
import moikheck.recipes.GoatHornRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public final class GoatHornCannon extends JavaPlugin {
    public HashMap<String, Long> startTimes = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerCmds();
        registerItems();
        registerRecipes();
        registerListeners();
        getLogger().log(Level.INFO, "GoatHornCannon plugin enabled.");
    }

    private void registerListeners() {
        new CraftingListener(this);
        new RightClickListener(this);
    }

    private void registerRecipes() {
        new GoatHornRecipe(this);
        new GoatHornAdminRecipe(this);
        new GoatHornClearRecipe(this);
    }

    private void registerItems() {
        GoatHornCannonItem.initialize(this);
        GoatHornAdminCannonItem.initialize(this);
    }

    private void registerCmds() {
        Objects.requireNonNull(this.getCommand("goathorncannon")).setExecutor(new GiveCannon(this));
        Objects.requireNonNull(this.getCommand("adminhorncannon")).setExecutor(new GiveCannon(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
