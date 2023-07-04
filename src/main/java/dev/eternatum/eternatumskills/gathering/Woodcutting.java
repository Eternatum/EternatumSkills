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
    public static final int JUNGLE_LOG_EXPERIENCE = 25;
    public static final int DARK_OAK_LOG_EXPERIENCE = 30;


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
