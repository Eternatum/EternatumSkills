package dev.eternatum.eternatumskills.debugging;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ChatColor;

public class EnchantingGUI {
    //Make the enchanting gui that we will be using in
    //Enchanting.java

    //This is the item that will be enchanted
    private ItemStack itemToEnchant;

    public EnchantingGUI(@NotNull ItemStack itemToEnchant) {
        this.itemToEnchant = itemToEnchant;
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
        ItemStack item = itemToEnchant;

        //Add the items to the GUI
        enchantingGUI.setItem(4, enchantItem);
        enchantingGUI.setItem(22, closeGUI);
        enchantingGUI.setItem(13, item);

        //Open the GUI for the player
        player.openInventory(enchantingGUI);
    }
    
}
