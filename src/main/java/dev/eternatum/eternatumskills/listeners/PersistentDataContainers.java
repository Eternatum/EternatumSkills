package dev.eternatum.eternatumskills.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PersistentDataContainers {
    private Plugin plugin;

    public void setCustomData() {

        this.plugin = plugin;
        NamespacedKey key = new NamespacedKey(plugin, "itemtype");
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key,PersistentDataType.STRING, "Mining");
        item.setItemMeta(meta);

    }

    public String getCustomData(ItemStack itemStack, Plugin plugin) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

        // Retrieve the custom data from the PersistentDataContainer
        NamespacedKey customDataKey = new NamespacedKey(plugin, "itemtype");
        String customData = dataContainer.get(customDataKey, PersistentDataType.STRING);

        return customData;
    }
}
