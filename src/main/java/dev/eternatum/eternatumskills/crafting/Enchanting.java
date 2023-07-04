package dev.eternatum.eternatumskills.crafting;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import dev.eternatum.eternatumskills.debugging.EnchantingGUI;
import net.md_5.bungee.api.ChatColor;

public class Enchanting implements Listener {
    //This is the item that will be enchanted

    // When the player right clicks an enchanting table, cancel the event and
    // open the gui from EnchantingGUI.java
    @EventHandler
    public void onEnchantTableClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.ENCHANTING_TABLE) {
                event.setCancelled(true);
                EnchantingGUI enchantingGUI = new EnchantingGUI(event.getItem());
                openEnchantingGUI(event.getPlayer());
            }
        }
    }
    public void openEnchantingGUI(@NotNull Player player) {
        Inventory enchantingGUI = Bukkit.createInventory(player, 27, "Enchanting");

        //Create an item that will be used to enchant the item
        ItemStack enchantItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta enchantItemMeta = enchantItem.getItemMeta();
        enchantItemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Enchant");
        enchantItem.setItemMeta(enchantItemMeta);

        //Create an item that will be used to close the GUI
        ItemStack closeGUI = new ItemStack(Material.BARRIER);
        ItemMeta closeGUIMeta = closeGUI.getItemMeta();
        closeGUIMeta.setDisplayName(ChatColor.RED + "Close");
        closeGUI.setItemMeta(closeGUIMeta);

        //Create the item that will be enchanted

        //Add the items to the GUI
        enchantingGUI.setItem(4, enchantItem);
        enchantingGUI.setItem(22, closeGUI);

        //Open the GUI for the player
        player.openInventory(enchantingGUI);
    }

    //Method that closes the gui when the player clicks the close button
    public void closeGUI(@NotNull Player player) {
        player.closeInventory();
    }
}
