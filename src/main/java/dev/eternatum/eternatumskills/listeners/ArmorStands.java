package dev.eternatum.eternatumskills.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import dev.eternatum.eternatumskills.misc.BlockTypes;
import dev.eternatum.eternatumskills.misc.ItemTypes;

public class ArmorStands implements Listener {
    private Plugin plugin;

    public ArmorStands(Plugin plugin) {
        this.plugin = plugin;
        ItemTypes.setPlugin(plugin); // Set the plugin instance in ItemTypes
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Material blockType = block.getType();
        ItemStack tool = player.getInventory().getItemInMainHand();

        // Check if the broken block is a mining block type
        if (BlockTypes.isMiningBlockType(blockType) && ItemTypes.isMiningTool(tool)) {
            // Add your mining block handling logic here
            // Example:
            player.sendMessage("You broke a mining block!");
        }
        
        // Check if the broken block is a woodcutting block type
        if (BlockTypes.isWoodcuttingBlockType(blockType) && ItemTypes.isWoodcuttingTool(tool)) {
            // Add your woodcutting block handling logic here
            // Example:
            player.sendMessage("You broke a woodcutting block!");
        }
    }
}
