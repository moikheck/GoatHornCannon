package moikheck.functionality;

import moikheck.GoatHornCannon;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Objects;

public class GoatHornCannonUse {

    public static void useCannon(Player player, String type, String direction, Block block, GoatHornCannon main) {
        double cooldownLength = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("cooldown-length") : main.getConfig().getString("cooldown-length-admin")));
        Long start = main.startTimes.get(player.getName()) != null ? main.startTimes.get(player.getName()) : 0;
        System.out.println(start);
        System.out.println(main.startTimes.toString());
        double end = start + cooldownLength;
        if (end < System.currentTimeMillis()) {
            main.startTimes.put(player.getName(), System.currentTimeMillis());
            double playerX = player.getFacing().getModX();
            double playerY = player.getFacing().getModY() + 0.5;
            double playerZ = player.getFacing().getModZ();
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY + 1.0, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY - 1.0, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX + 1.0, playerY, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX - 1.0, playerY, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY, playerZ + 1.0);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY, playerZ - 1.0);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY + 0.5, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY - 0.5, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX + 0.5, playerY, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX - 0.5, playerY, playerZ);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY, playerZ + 0.5);
            player.spawnParticle(Particle.CLOUD, player.getLocation(), 0, playerX, playerY, playerZ - 0.5);
            if (direction.equalsIgnoreCase("ground")) {
                launchOffGround(player, type, block, main);
            }
            else {
                attack(player, type, main);
            }
        }
    }

    public static void launchOffGround(Player player, String type, Block block, GoatHornCannon main) {
        boolean launchOwner = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("launch-owner") : main.getConfig().getString("launch-owner-admin"));
        double maxLaunchOwner = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("max-launch-owner") : main.getConfig().getString("max-launch-owner-admin")));
        String fireSound = type.equals("normal") ? main.getConfig().getString("fire-sound") : main.getConfig().getString("fire-sound-admin");

        if (!launchOwner) {
            return;
        }

        player.setVelocity(new Vector(player.getFacing().getModX() * -0.5 ,maxLaunchOwner,player.getFacing().getModZ() * -0.5));
    }

    public static void attack(Player player, String type, GoatHornCannon main) {
        System.out.println("Attacking");
        boolean damagePlayers = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("damage-players") : main.getConfig().getString("damage-players-admin"));
        boolean damageEntities = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("damage-entities") : main.getConfig().getString("damage-entities-admin"));
        boolean highlightPlayers = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("highlight-players") : main.getConfig().getString("highlight-players-admin"));
        boolean highlightEntities = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("highlight-entities") : main.getConfig().getString("highlight-entities-admin"));
        int damageAmount = Integer.parseInt(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("damage-amount") : main.getConfig().getString("damage-amount-admin")));
        double critRate = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("crit-rate") : main.getConfig().getString("crit-rate-admin")));
        double range = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("range") : main.getConfig().getString("range-admin")));
        double angle = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("angle") : main.getConfig().getString("angle-admin")));
        double maxLaunchDistance = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("max-launch-distance") : main.getConfig().getString("max-launch-distance-admin")));
        String fireSound = type.equals("normal") ? main.getConfig().getString("fire-sound") : main.getConfig().getString("fire-sound-admin");
    }
}
