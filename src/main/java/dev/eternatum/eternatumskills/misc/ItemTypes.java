package dev.eternatum.eternatumskills.misc;

import org.bukkit.NamespacedKey;
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
    }

    public static boolean isMiningTool(ItemStack item) {
        if (item != null && item.getType().name().endsWith("_PICKAXE")) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "itemtype");

            // Check if the pickaxe type is "mining" or "default"
            String pickaxeType = dataContainer.get(key, PersistentDataType.STRING);
            return pickaxeType != null && (pickaxeType.equals("mining") || pickaxeType.equals("default"));
        }

        return false;
    }

    public static boolean isWoodcuttingTool(ItemStack item) {
        // Implement your woodcutting tool check logic here
        return false;
    }
}
