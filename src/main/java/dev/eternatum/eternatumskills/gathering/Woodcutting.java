package dev.eternatum.eternatumskills.gathering;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Woodcutting implements Listener {
    private PlayerDataManager playerDataManager;

    public Woodcutting(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public static final int OAK_LOG_EXPERIENCE = 10;
    public static final int BIRCH_LOG_EXPERIENCE = 15;
    public static final int SPRUCE_LOG_EXPERIENCE = 20;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check if the broken block is a log
        if (block.getType() == Material.OAK_LOG || block.getType() == Material.BIRCH_LOG ||
                block.getType() == Material.SPRUCE_LOG /* Add more log types as needed */) {

            // Check if the player is holding an axe
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType().name().endsWith("_AXE")) {
                // Calculate experience gained based on log type
                int experience = 0;
                ChatColor logColor = ChatColor.WHITE;
                String logTypeName = "";
                switch (block.getType()) {
                    case OAK_LOG:
                        experience = OAK_LOG_EXPERIENCE;
                        logColor = ChatColor.AQUA;
                        logTypeName = "Oak";
                        break;
                    case BIRCH_LOG:
                        experience = BIRCH_LOG_EXPERIENCE;
                        logColor = ChatColor.YELLOW;
                        logTypeName = "Birch";
                        break;
                    case SPRUCE_LOG:
                        experience = SPRUCE_LOG_EXPERIENCE;
                        logColor = ChatColor.DARK_GREEN;
                        logTypeName = "Spruce";
                        break;
                    // Add more cases for other log types
                }

                // Modify chopping speed based on axe material
                int choppingSpeed = calculateChoppingSpeed(item.getType());

                // Get the player's unique ID
                UUID playerId = player.getUniqueId();

                // Retrieve or create player data
                PlayerData playerData = playerDataManager.getPlayerData(playerId);
                if (playerData == null) {
                    playerData = new PlayerData(playerId);
                    playerDataManager.addPlayerData(playerData);
                }

                // Add experience to the player's data
                playerData.addWoodcuttingExperience(experience);

                // Perform any other desired operations (e.g., modify player's Minecraft experience)

                // Send feedback to the player
                player.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "▥" + ChatColor.GRAY + "] " +
                        ChatColor.WHITE + "You gained " + ChatColor.GREEN + experience + ChatColor.WHITE +
                        " experience for chopping " + logColor + logTypeName + " logs" + ChatColor.WHITE + ".");
            }
        }
    }

    private int calculateChoppingSpeed(Material axeMaterial) {
        // Add your implementation to calculate chopping speed based on axe material
        // You can use a similar switch statement as above to assign different chopping speeds to different axe materials
        return 0;
    }
}
