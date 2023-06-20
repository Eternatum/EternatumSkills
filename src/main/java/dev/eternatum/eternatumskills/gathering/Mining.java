package dev.eternatum.eternatumskills.gathering;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import dev.eternatum.eternatumskills.listeners.PersistentDataContainers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Mining implements Listener {
    private Plugin plugin;
    private PersistentDataContainers dataContainers;
    private PlayerDataManager playerDataManager;
    private final int IRON_EXP = 10; // Adjust the experience values as needed
    private final int COAL_EXP = 5;
    private final int GOLD_EXP = 15;
    private final int DIAMOND_EXP = 20;

    public Mining(Plugin plugin, PersistentDataContainers dataContainers, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.dataContainers = dataContainers;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        // Check if the tool type is "mining"
        if (dataContainers.getCustomData(tool, plugin).equals("mining")) {
            // Calculate experience gained based on block type
            int experience = 0;
            ChatColor stoneColor = ChatColor.of("#FFFFFF");
            switch (block.getType()) {
                case IRON_ORE:
                case DEEPSLATE_IRON_ORE:
                    experience = IRON_EXP;
                    stoneColor = ChatColor.of("#A19D94");
                    break;
                case COAL_ORE:
                case DEEPSLATE_COAL_ORE:
                    experience = COAL_EXP;
                    stoneColor = ChatColor.of("#36454F");
                    break;
                case GOLD_ORE:
                case DEEPSLATE_GOLD_ORE:
                    experience = GOLD_EXP;
                    stoneColor = ChatColor.of("#FFD700");
                    break;
                case DIAMOND_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                    experience = DIAMOND_EXP;
                    stoneColor = ChatColor.of("#B9F2FF");
                    break;
            }

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
                playerData.addMiningExperience(experience);

                // Create and customize the armor stand for feedback
                ArmorStand armorStand = (ArmorStand) block.getLocation().getWorld().spawnEntity(block.getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setCustomName(ChatColor.GOLD + "⛏ +" + experience + " Mining XP");
                armorStand.setCustomNameVisible(true);

                // Remove the armor stand after 1 second
                plugin.getServer().getScheduler().runTaskLater(plugin, armorStand::remove, 20);
            }
        } else {
            player.sendMessage("You do not have the required tool to break this block!");
        }
    }
}
