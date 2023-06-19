package dev.eternatum.eternatumskills.crafting;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Alchemy implements Listener {
    private PlayerDataManager playerDataManager;
    private Plugin plugin;

    // Experience values
    private static final int NORMAL_POTION_EXP = 10;
    private static final int SPLASH_POTION_EXP = (int) (NORMAL_POTION_EXP * 0.5);
    private static final int LINGERING_POTION_EXP = (int) (SPLASH_POTION_EXP * 1.5);


    public Alchemy(PlayerDataManager playerDataManager, Plugin plugin) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onBrew(BrewEvent event) {
        BrewerInventory inventory = event.getContents();
        Player player = (Player) inventory.getHolder();


        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack ingredient = inventory.getItem(i);

            // Check if the ingredient is a potion
            if (ingredient != null && ingredient.getItemMeta() instanceof PotionMeta) {
                Potion potion = Potion.fromItemStack(ingredient);
                ItemStack brewedPotion = inventory.getItem(3); // Index 3 is the result slot

                // Check if the potion has been successfully brewed
                if (brewedPotion != null && brewedPotion.getItemMeta() instanceof PotionMeta) {
                    double experience = getAlchemyExperience(potion, brewedPotion);
                    grantAlchemyExperience(player, experience);
                    break;
                }
            }
        }
    }

    private int getAlchemyExperience(Potion potion, ItemStack brewedPotion) {
        int experience = NORMAL_POTION_EXP;

        if (potion.isSplash()) {
            experience = SPLASH_POTION_EXP;
        } else if (isLingeringPotion(brewedPotion)) {
            experience = LINGERING_POTION_EXP;
        }

        return experience;
    }


    private boolean isLingeringPotion(ItemStack potion) {
        return potion.getType() == Material.LINGERING_POTION;
    }

    private void grantAlchemyExperience(Player player, double experience) {
        if (experience > 0) {
            // Get the player's unique ID
            UUID playerId = player.getUniqueId();

            // Retrieve or create player data
            PlayerData playerData = playerDataManager.getPlayerData(playerId);
            if (playerData == null) {
                playerData = new PlayerData(playerId);
                playerDataManager.addPlayerData(playerData);
            }

            // Add experience to the player's data
            playerData.addAlchemyExperience((int) experience);

            // Create and customize the armor stand for feedback
            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setCustomName(ChatColor.GOLD + "✨ +" + experience + " Alchemy XP");
            armorStand.setCustomNameVisible(true);

            // Remove the armor stand after 1 second
            plugin.getServer().getScheduler().runTaskLater(plugin, armorStand::remove, 20);
        }
    }
}
