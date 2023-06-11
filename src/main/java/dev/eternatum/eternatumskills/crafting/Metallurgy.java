package dev.eternatum.eternatumskills.crafting;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Metallurgy implements Listener {
    private PlayerDataManager playerDataManager;
    private final int IRON_INGOT_EXPERIENCE = 10;  // Arbitrary experience value for iron ingot
    private final int GOLD_INGOT_EXPERIENCE = 20;  // Arbitrary experience value for gold ingot

    public Metallurgy(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public static void registerEvents(Plugin plugin, PlayerDataManager playerDataManager) {
        Metallurgy metallurgy = new Metallurgy(playerDataManager);
        plugin.getServer().getPluginManager().registerEvents(metallurgy, plugin);
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        Material extractedMaterial = event.getItemType();
        if (isMetalIngot(extractedMaterial)) {
            int extractedAmount = event.getItemAmount();
            int experience = getExperienceValue(extractedMaterial) * extractedAmount;
            if (experience > 0) {
                updateMetallurgyExperience(player, experience);
                player.sendMessage(ChatColor.YELLOW + "You retrieved " + extractedAmount + " " + extractedMaterial.name() + "(s) from the furnace");
                player.sendMessage(ChatColor.YELLOW + "and received " + ChatColor.GREEN + experience + ChatColor.YELLOW + " metallurgy experience.");
            }
        }
    }

    private boolean isMetalIngot(Material material) {
        return material == Material.IRON_INGOT || material == Material.GOLD_INGOT;
    }

    private int getExperienceValue(Material material) {
        if (material == Material.IRON_INGOT) {
            return IRON_INGOT_EXPERIENCE;
        } else if (material == Material.GOLD_INGOT) {
            return GOLD_INGOT_EXPERIENCE;
        }
        return 0;
    }

    private void updateMetallurgyExperience(Player player, int experience) {
        PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId());
        if (playerData != null) {
            playerData.addMetallurgyExperience(experience);
        }
    }
}
