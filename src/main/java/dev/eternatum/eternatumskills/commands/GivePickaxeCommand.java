package dev.eternatum.eternatumskills.commands;

import dev.eternatum.eternatumskills.misc.ItemTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class GivePickaxeCommand implements CommandExecutor {
    private final Plugin plugin;
    private final NamespacedKey miningKey;

    public GivePickaxeCommand(Plugin plugin) {
        this.plugin = plugin;
        this.miningKey = new NamespacedKey(plugin, "mining");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player already has a mining pickaxe
        if (ItemTypes.isMiningTool(player.getInventory().getItemInMainHand())) {
            player.sendMessage(ChatColor.YELLOW + "You already have a mining pickaxe.");
            return true;
        }

        // Create the mining pickaxe item
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.getPersistentDataContainer().set(miningKey, PersistentDataType.STRING, "");
        pickaxeMeta.setDisplayName(ChatColor.GREEN + "Mining Pickaxe");
        pickaxe.setItemMeta(pickaxeMeta);

        // Give the mining pickaxe to the player
        player.getInventory().addItem(pickaxe);
        player.sendMessage(ChatColor.GREEN + "You have been given a mining pickaxe.");

        return true;
    }
}
