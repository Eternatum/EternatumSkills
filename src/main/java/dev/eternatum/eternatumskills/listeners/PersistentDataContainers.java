package dev.eternatum.eternatumskills.listeners;

import com.destroystokyo.paper.event.block.AnvilDamagedEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PersistentDataContainers {
    private Plugin plugin;
    private Player player;
    private NamespacedKey key;
    private ItemStack item;

    public PersistentDataContainers(Player player) {
        this.player = player;
        this.key = new NamespacedKey(plugin, "itemtype");
        this.item = player.getInventory().getItemInMainHand();
    }

    public void setMining() {
        // ItemMeta implements PersistentDataHolder so we can get the PDC from it
        if (item.getType().name().endsWith("_PICKAXE")) {
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "mining");
            item.setItemMeta(meta);
        }
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
