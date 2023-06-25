package dev.eternatum.eternatumskills.listeners;

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

    public PersistentDataContainers(Player player, Plugin plugin) {
        this.player = player;
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "itemtype");
        this.item = player.getInventory().getItemInMainHand();
    }

    public void setMining(ItemStack item) {
    if (item.getType().name().endsWith("_PICKAXE")) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "itemtype");

        // Check if the pickaxe type is "mining" or "default"
        String pickaxeType = dataContainer.get(key, PersistentDataType.STRING);
        if (pickaxeType == null || !pickaxeType.equals("mining")) {
            dataContainer.set(key, PersistentDataType.STRING, "mining");
            item.setItemMeta(meta);
        }
    }
}

    public void setWoodcutting(ItemStack item){
        if (item.getType().name().endsWith("_AXE")){
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "itemtype");
            String axeType = dataContainer.get(key, PersistentDataType.STRING);
            if(axeType == null || !axeType.equals("woodcutting")){
                dataContainer.set(key, PersistentDataType.STRING, "woodcutting");
            }

        }
    }




    public String getCustomData(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

        // Retrieve the custom data from the PersistentDataContainer
        String customData = dataContainer.get(key, PersistentDataType.STRING);

        return customData;
    }
}
