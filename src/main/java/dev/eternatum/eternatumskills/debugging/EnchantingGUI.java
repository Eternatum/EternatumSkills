package dev.eternatum.eternatumskills.debugging;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import dev.eternatum.eternatumskills.crafting.Enchanting;
import net.md_5.bungee.api.ChatColor;

public class EnchantingGUI {
    private ItemStack itemToEnchant;

    public EnchantingGUI(@NotNull ItemStack itemToEnchant) {
        this.itemToEnchant = itemToEnchant;
    }

    public void openEnchantingGUI(@NotNull Player player) {
        Inventory enchantingGUI = Bukkit.createInventory(player, 27, "Enchanting");

        ItemStack enchantItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta enchantItemMeta = enchantItem.getItemMeta();
        enchantItemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Enchant");
        enchantItem.setItemMeta(enchantItemMeta);

        ItemStack closeGUI = new ItemStack(Material.BARRIER);
        ItemMeta closeGUIMeta = closeGUI.getItemMeta();
        closeGUIMeta.setDisplayName(ChatColor.RED + "Close");
        closeGUI.setItemMeta(closeGUIMeta);

        ItemStack item = itemToEnchant.clone();

        enchantingGUI.setItem(4, enchantItem);
        enchantingGUI.setItem(22, closeGUI);
        enchantingGUI.setItem(13, item);

        player.openInventory(enchantingGUI);
    }
}
