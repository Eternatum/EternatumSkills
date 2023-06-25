package dev.eternatum.eternatumskills.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class TagAssigner implements Listener {
    private Plugin plugin;
    private NamespacedKey miningKey;
    private NamespacedKey woodcuttingKey;

    public TagAssigner(Plugin plugin) {
        this.plugin = plugin;
        this.miningKey = new NamespacedKey(plugin, "itemtype");
        this.woodcuttingKey = new NamespacedKey(plugin, "itemtype");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        assignMiningItemType(player);
        assignWoodcuttingItemType(player);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem().getItemStack();

        if (isVanillaPickaxe(item)) {
            setMiningTag(item);
        }
        else if (isVanillaAxe(item)){
            setWoodcuttingTag(item);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player player = (Player) event.getWhoClicked();
        CraftingInventory inventory = event.getInventory();
        ItemStack craftedItem = event.getCurrentItem();

        // Check if the crafted item is a vanilla pickaxe
        if (isVanillaPickaxe(craftedItem)) {
            setMiningTag(craftedItem);
        }

        // Check if the crafting result is a vanilla pickaxe
        ItemStack[] matrix = inventory.getMatrix();
        for (ItemStack item : matrix) {
            if (isVanillaPickaxe(item)) {
                setMiningTag(craftedItem);
                break;
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        // Check if the clicked item is obtained from Creative mode
        if (event.getInventory().getType() == InventoryType.CREATIVE && isVanillaPickaxe(clickedItem)) {
            setMiningTag(clickedItem);
        }
    }

    private void assignMiningItemType(Player player) {
        ItemStack[] inventory = player.getInventory().getContents();

        for (ItemStack item : inventory) {
            if (isVanillaPickaxe(item)) {
                setMiningTag(item);
            }
        }
    }

    private void assignWoodcuttingItemType(Player player) {
        ItemStack[] inventory = player.getInventory().getContents();

        for (ItemStack item : inventory) {
            if (isVanillaAxe(item)) {
                setWoodcuttingTag(item);
            }
        }
    }

    private boolean isVanillaPickaxe(ItemStack item) {
        return item != null && item.getType().name().endsWith("_PICKAXE");
    }

    private boolean isVanillaAxe(ItemStack item) {
        return item != null && item.getType().name().endsWith("_AXE");
    }

    private void setMiningTag(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(miningKey, PersistentDataType.STRING, "mining");
        item.setItemMeta(meta);
    }

    private void setWoodcuttingTag(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(woodcuttingKey, PersistentDataType.STRING, "woodcutting");
        item.setItemMeta(meta);
    }
}
