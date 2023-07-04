package dev.eternatum.eternatumskills.misc;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class ItemTypes {
    private static NamespacedKey miningKey;
    private static NamespacedKey woodcuttingKey;
    private static Plugin plugin;

    public static void setPlugin(Plugin plugin) {
        ItemTypes.plugin = plugin;
        miningKey = new NamespacedKey(plugin, "mining");
        woodcuttingKey = new NamespacedKey(plugin, "woodcutting");
    }

    public static boolean isMiningTool(ItemStack item) {
        if (item != null && item.getType().name().endsWith("_PICKAXE")) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            if (dataContainer != null) {
                // Check if the pickaxe type is "mining" and not "woodcutting"
                boolean isMiningTool = dataContainer.has(miningKey, PersistentDataType.STRING)
                        && !dataContainer.has(woodcuttingKey, PersistentDataType.STRING);

                if (isMiningTool) {
                    Player player = getPlayerFromItemMeta(meta);
                    if (player != null) {
                        player.sendMessage("You are holding an appropriate mining pickaxe!");
                    }
                }

                return isMiningTool;
            }
        }
        return false;
    }

    public static boolean isWoodcuttingTool(ItemStack item) {
        if (item != null && item.getType().name().endsWith("_AXE")) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            if (dataContainer != null) {
                // Check if the axe type is "woodcutting" and not "mining"
                boolean isWoodcuttingTool = dataContainer.has(woodcuttingKey, PersistentDataType.STRING)
                        && !dataContainer.has(miningKey, PersistentDataType.STRING);

                if (isWoodcuttingTool) {
                    Player player = getPlayerFromItemMeta(meta);
                    if (player != null) {
                        player.sendMessage("You are holding an appropriate woodcutting axe!");
                    }
                }

                return isWoodcuttingTool;
            }
        }
        return false;
    }

    private static Player getPlayerFromItemMeta(ItemMeta meta) {
        if (meta.hasCustomModelData()) {
            int playerEntityId = meta.getCustomModelData();
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                if (player.getEntityId() == playerEntityId) {
                    return player;
                }
            }
        }
        return null;
    }
    
    // Method to set persistent data for mining pickaxe
    public static void setMiningTool(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(miningKey, PersistentDataType.STRING, "mining");
        item.setItemMeta(meta);
    }

    // Method to set persistent data for woodcutting axe
    public static void setWoodcuttingTool(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(woodcuttingKey, PersistentDataType.STRING, "woodcutting");
        item.setItemMeta(meta);
    }
}
