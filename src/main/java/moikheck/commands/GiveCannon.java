package moikheck.commands;

import moikheck.GoatHornCannon;
import moikheck.items.GoatHornAdminCannonItem;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GiveCannon implements CommandExecutor {
    public GoatHornCannon main;
    boolean usesPermissions;
    boolean adminUsesPermissions;

    public GiveCannon(GoatHornCannon main) {
        this.main = main;
        usesPermissions = Objects.requireNonNull(main.getConfig().getString("use-permissions")).equalsIgnoreCase("true") ;
        adminUsesPermissions = Objects.requireNonNull(main.getConfig().getString("admin-use-permissions")).equalsIgnoreCase("true") ;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("goathorncannon")) {
            if (sender.hasPermission("goathorncannon.command") || !usesPermissions ) {
                if (args.length == 0 || args[0].equalsIgnoreCase(sender.getName())) {
                    Player p = (Player) sender;
                    p.getInventory().addItem(GoatHornCannonItem.horn);
                }
                else {
                    if (sender.hasPermission("goathorncannon.command.others") || !usesPermissions) {
                        System.out.println("For others (working)");
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give to other people."));
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command."));
            }

            return true;
        }
        else if (label.equalsIgnoreCase("adminhorncannon")) {
            if (sender.hasPermission("goathorncannon.admin.command") || !adminUsesPermissions ) {
                if (args.length == 0 || args[0].equalsIgnoreCase(sender.getName())) {
                    Player p = (Player) sender;
                    p.getInventory().addItem(GoatHornAdminCannonItem.horn);
                }
                else {
                    if (sender.hasPermission("goathorncannon.admin.others") || !adminUsesPermissions) {
                        System.out.println("For others (working)");
                    }
                    else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give to other people."));
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command."));
            }

            return true;
        }

        return false;
    }
}
