package moikheck.functionality;

import moikheck.GoatHornCannon;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoatHornCannonUse {

    public static void useCannon(Player player, String type, String direction, GoatHornCannon main) {
        int defaultHornCooldown = 140;
        double cooldownLengthSeconds = Double.parseDouble(Objects.requireNonNull(type.equals("normal") ? main.getConfig().getString("cooldown-length") : main.getConfig().getString("cooldown-length-admin")));
        int cooldownLengthTicks = defaultHornCooldown;
        if (cooldownLengthSeconds > 0) {
            cooldownLengthTicks = (int)(cooldownLengthSeconds * 20);
        }
        boolean keepOriginalHornSound = Boolean.parseBoolean(type.equals("normal") ? main.getConfig().getString("keep-horn-sound") : main.getConfig().getString("keep-horn-sound-admin"));
        String fireSound = type.equals("normal") ? main.getConfig().getString("fire-sound") : main.getConfig().getString("fire-sound-admin");
        if (player.getCooldown(Material.GOAT_HORN) > 0) {
            return;
        }
        player.setCooldown(Material.GOAT_HORN, cooldownLengthTicks);
        spawnParticleCone(player);
        if (!keepOriginalHornSound) {
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_0, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_1, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_2, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_3, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_4, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_5, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_6, SoundCategory.RECORDS);
            player.stopSound(Sound.ITEM_GOAT_HORN_SOUND_7, SoundCategory.RECORDS);
        }
        if (fireSound == null) {
            main.getLogger().warning("Fire sound not assigned to the goat horn cannon. Using default EVENT_RAID_HORN. Check your config to make sure you have it set up correctly!");
            fireSound = "EVENT_RAID_HORN";
        }
        Sound sound = Sound.valueOf(fireSound);
        player.getWorld().playSound(player.getLocation(), sound, SoundCategory.PLAYERS, 100F, 1.0F);
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

        List<Entity> targets = getEntitiesInFront(player, range);
        for (Entity ent : targets) {
            if (ent instanceof LivingEntity) {
                if (!ent.hasMetadata("NPC") || Objects.equals(main.getConfig().getString("launch-npc"), "true")) {
                    if (!(ent instanceof Player)) {
                        if (highlightEntities) {
                            ((LivingEntity) ent).addPotionEffect(PotionEffectType.GLOWING.createEffect(200, 0));
                        }
                        if (damageEntities) {
                            ((LivingEntity) ent).damage(actualDamage);
                            ent.setLastDamageCause(new EntityDamageEvent(ent, EntityDamageEvent.DamageCause.SONIC_BOOM, ((LivingEntity) ent).getHealth()));
                        }
                    } else {
                        if (highlightPlayers) {
                            ((LivingEntity) ent).addPotionEffect(PotionEffectType.GLOWING.createEffect(200, 0));
                        }
                        if (damagePlayers) {
                            ((Player) ent).damage(actualDamage);
                            ent.setLastDamageCause(new EntityDamageEvent(ent, EntityDamageEvent.DamageCause.SONIC_BOOM, ((Player) ent).getHealth()));

                            ((Player) ent).playSound(ent.getLocation(), Sound.valueOf("ITEM_ARMOR_EQUIP_ELYTRA"), SoundCategory.PLAYERS, 1.0F, 2.0F);
                        }
                    }

                    double actualLaunchDistance = Math.abs(player.getLocation().distance(ent.getLocation()) - maxLaunchDistance);
                    ent.setVelocity(new Vector(player.getLocation().getDirection().multiply(actualLaunchDistance / 2).getX(), 1.0, player.getLocation().getDirection().multiply(actualLaunchDistance / 2).getZ()));
                }
            }
        }
    }

    public static List<Entity> getEntitiesInFront(Player player, double range) {
        Location eyeLocation = player.getEyeLocation();
        Vector playerDirection = eyeLocation.getDirection().normalize();

        // Get nearby entities in a cube first
        List<Entity> nearby = player.getNearbyEntities(range, range, range);
        List<Entity> inFront = new ArrayList<>();

        for (Entity entity : nearby) {
            if (entity.equals(player)) continue;

            Vector toEntity = entity.getLocation().toVector().subtract(eyeLocation.toVector());
            double distance = toEntity.length();

            if (distance > range) continue;

            toEntity.normalize();
            double dotProduct = playerDirection.dot(toEntity);

            // dotProduct = 1 when directly in front, 0 when 90°, -1 when directly behind
            if (dotProduct > 0.5) { // adjust this threshold as needed
                inFront.add(entity);
            }
        }

        return inFront;
    }



    public static void spawnParticleCone(Player player) {
        Location origin = player.getEyeLocation();
        Vector direction = origin.getDirection().normalize();

        World world = player.getWorld();

        int particles = 100;
        double coneLength = 5.0;
        double coneRadius = 2.0;

        for (int i = 0; i < particles; i++) {
            double t = Math.random(); // depth along the cone [0,1]
            double theta = Math.random() * 2 * Math.PI;

            double localRadius = t * coneRadius;
            double x = localRadius * Math.cos(theta);
            double y = localRadius * Math.sin(theta);
            double z = t * coneLength;

            // Local vector in cone coordinates
            Vector local = new Vector(x, y, z);

            // Rotate local vector to align with player's facing direction
            Vector rotated = rotateVectorToDirection(local, direction);

            // Final position
            Location particleLoc = origin.clone().add(rotated);
            world.spawnParticle(Particle.CLOUD, particleLoc, 0, 0, 0, 0);
        }
    }


    public static Vector rotateVectorToDirection(Vector local, Vector direction) {
        direction = direction.clone().normalize();

        Vector up = new Vector(0, 1, 0);
        if (direction.getY() == 1.0 || direction.getY() == -1.0) {
            up = new Vector(0, 0, 1); // avoid cross product issues straight up/down
        }

        Vector right = direction.clone().crossProduct(up).normalize();
        Vector correctedUp = right.clone().crossProduct(direction).normalize();

        // Convert local (cone space) to world space using basis vectors
        return right.multiply(local.getX())
                .add(correctedUp.multiply(local.getY()))
                .add(direction.multiply(local.getZ()));
    }

}
