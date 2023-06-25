package dev.eternatum.eternatumskills.gathering;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
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

public class Woodcutting implements Listener {
    private PlayerDataManager playerDataManager;
    private Plugin plugin;

    public Woodcutting(Plugin plugin, PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
    }

    public static final int OAK_LOG_EXPERIENCE = 10;
    public static final int BIRCH_LOG_EXPERIENCE = 15;
    public static final int SPRUCE_LOG_EXPERIENCE = 20;
    public static final int ACACIA_LOG_EXPERIENCE = 20;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        // Check if the broken block is a log
        if (isWoodcuttingTool(tool)){
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
                case ACACIA_LOG:
                    experience = ACACIA_LOG_EXPERIENCE;
                    logColor = ChatColor.RED;
                    logTypeName = "Acacia";
                    break;
                // Add more cases for other log types
            }

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

    private boolean isWoodcuttingTool(ItemStack item) {
        if (item != null && item.getType().name().endsWith("_AXE")) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "itemtype");

            // Check if the pickaxe type is "mining" or "default"
            String axeType = dataContainer.get(key, PersistentDataType.STRING);
            return axeType != null && (axeType.equals("woodcutting") || axeType.equals("default"));
        }

        return false;
    }
}
