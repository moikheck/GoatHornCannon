package moikheck.commands;

import moikheck.GoatHornCannon;
import moikheck.items.GoatHornAdminCannonItem;
import moikheck.items.GoatHornCannonItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.Objects;

public class Commands implements CommandExecutor {
    public GoatHornCannon main;
    boolean usesPermissions;
    boolean adminUsesPermissions;

    public Commands(GoatHornCannon main) {
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
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found."));
                        }
                        else {
                            target.getInventory().addItem(GoatHornCannonItem.horn);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGave ".concat(target.getName()).concat(" a goat horn cannon.")));
                        }
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
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found."));
                        }
                        else {
                            target.getInventory().addItem(GoatHornAdminCannonItem.horn);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGave ".concat(target.getName()).concat(" an admin cannon.")));
                        }
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
        else if (label.equalsIgnoreCase("ghc")) {
            if (args.length == 0 || !args[0].equalsIgnoreCase("reload") ) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGoatHornCommand v1.0 by moikheck"));
            }
            else {
                if (sender.hasPermission("goathorncannon.reload")) {
                    main.reloadConfig();
                    usesPermissions = Objects.requireNonNull(main.getConfig().getString("use-permissions")).equalsIgnoreCase("true") ;
                    adminUsesPermissions = Objects.requireNonNull(main.getConfig().getString("admin-use-permissions")).equalsIgnoreCase("true") ;
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig successfully reloaded!"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command."));
                }
            }
        }

        return false;
    }
}
