package dev.eternatum.eternatumskills.debugging;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    public static ItemStack createMiningTool(Plugin plugin, Material material) {
        ItemStack miningTool = new ItemStack(material);

        // Set the display name of the item
        ItemMeta meta = miningTool.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Mining Pickaxe"); // Replace with the desired display name

        // Create a NamespacedKey for the persistent data
        NamespacedKey key = new NamespacedKey(plugin, "itemtype");

        // Set the persistent data on the item's metadata
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(key, PersistentDataType.STRING, "mining");

        // Update the item's metadata
        miningTool.setItemMeta(meta);

        return miningTool;
    }

    public static List<Material> getPickaxeMaterials() {
        return Arrays.asList(
                Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.IRON_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.DIAMOND_PICKAXE
        );
    }

    // Other item-related methods...
}
