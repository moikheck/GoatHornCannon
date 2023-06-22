package moikheck.functionality;

import moikheck.GoatHornCannon;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

public class GoatHornCannonUse {

    public static void useCannon(Player player, String type, String direction, GoatHornCannon main) {
        int defaultHornCooldown = 140;
        double cooldownLengthSeconds = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("cooldown-length") : main.getConfig().getString("cooldown-length-admin")));
        int cooldownLengthTicks = defaultHornCooldown + (int)(cooldownLengthSeconds * 20);
        boolean keepOriginalHornSound = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("keep-horn-sound") : main.getConfig().getString("keep-horn-sound-admin"));
        String fireSound = type.equals("normal") ? main.getConfig().getString("fire-sound") : main.getConfig().getString("fire-sound-admin");
        if (player.getCooldown(Material.GOAT_HORN) > 0) {
            return;
        }
        player.setCooldown(Material.GOAT_HORN, cooldownLengthTicks);
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
        if (!keepOriginalHornSound) {
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_0, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_1, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_2, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_3, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_4, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_5, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_6, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_7, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_PLAY, SoundCategory.RECORDS);
        }
        if (fireSound == null) {
            main.getLogger().warning("Fire sound not assigned to the goat horn cannon. Using default ENTITY_FIREWORK_ROCKET_BLAST. Check your config to make sure you have it set up correctly!");
            fireSound = "ENTITY_FIREWORK_ROCKET_BLAST";
        }
        Sound sound = Sound.valueOf(fireSound);
        player.getWorld().playSound(player.getLocation(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (direction.equalsIgnoreCase("ground")) {
            launchOffGround(player, type, main);
        }
        else {
            attack(player, type, main);
        }
    }

    public static void launchOffGround(Player player, String type, GoatHornCannon main) {
        boolean launchOwner = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("launch-owner") : main.getConfig().getString("launch-owner-admin"));
        double maxLaunchOwner = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("max-launch-owner") : main.getConfig().getString("max-launch-owner-admin")));

        if (!launchOwner) {
            return;
        }

        player.setVelocity(new Vector(player.getFacing().getModX() * -0.5 ,maxLaunchOwner,player.getFacing().getModZ() * -0.5));
    }

    public static void attack(Player player, String type, GoatHornCannon main) {
        boolean damagePlayers = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("damage-players") : main.getConfig().getString("damage-players-admin"));
        boolean damageEntities = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("damage-entities") : main.getConfig().getString("damage-entities-admin"));
        boolean highlightPlayers = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("highlight-players") : main.getConfig().getString("highlight-players-admin"));
        boolean highlightEntities = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("highlight-entities") : main.getConfig().getString("highlight-entities-admin"));
        int damageAmount = Integer.parseInt(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("damage-amount") : main.getConfig().getString("damage-amount-admin")));
        double critRate = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("crit-rate") : main.getConfig().getString("crit-rate-admin")));
        double range = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("range") : main.getConfig().getString("range-admin")));
        double maxLaunchDistance = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("max-launch-distance") : main.getConfig().getString("max-launch-distance-admin")));

        boolean isCrit = Math.random() <= critRate / 100;
        int actualDamage = isCrit ? damageAmount * 2 : damageAmount;

        for (Entity ent : player.getNearbyEntities(range*2, range, range*2)) {
            if (ent instanceof LivingEntity) {
                if (!(ent instanceof Player)) {
                    if (highlightEntities) {
                        ((LivingEntity) ent).addPotionEffect(PotionEffectType.GLOWING.createEffect(200,0));
                    }
                    if (damageEntities) {
                        ((LivingEntity) ent).damage(actualDamage);
                        ent.setLastDamageCause(new EntityDamageEvent(ent, EntityDamageEvent.DamageCause.SONIC_BOOM, ((LivingEntity) ent).getHealth()));
                    }
                }
                else {
                    if (highlightPlayers) {
                        ((LivingEntity) ent).addPotionEffect(PotionEffectType.GLOWING.createEffect(200,0));
                    }
                    if (damagePlayers) {
                        ((Player) ent).damage(actualDamage);
                        ent.setLastDamageCause(new EntityDamageEvent(ent, EntityDamageEvent.DamageCause.SONIC_BOOM, ((Player) ent).getHealth()));

                        ((Player) ent).playSound(ent.getLocation(), Sound.valueOf("ITEM_ARMOR_EQUIP_ELYTRA"), SoundCategory.PLAYERS, 1.0F, 2.0F);
                    }
                }

                double actualLaunchDistance = Math.abs(player.getLocation().distance(ent.getLocation()) - maxLaunchDistance);
                ent.setVelocity(new Vector (player.getLocation().getDirection().multiply(actualLaunchDistance / 2).getX(), 1.0, player.getLocation().getDirection().multiply(actualLaunchDistance / 2).getZ()));
            }
        }
    }
}
