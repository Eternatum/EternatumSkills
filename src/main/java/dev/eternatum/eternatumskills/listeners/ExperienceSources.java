package dev.eternatum.eternatumskills.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.TextDisplay.TextAlignment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import dev.eternatum.eternatumskills.Main;
import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import dev.eternatum.eternatumskills.misc.BlockTypes;
import dev.eternatum.eternatumskills.misc.ItemTypes;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ExperienceSources implements Listener {

    private Configuration experienceConfig;
    private Main plugin;
    private PlayerDataManager playerDataManager;

    public ExperienceSources(Main plugin, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        File configFile = new File(plugin.getDataFolder(), "ExperienceTypes.yml");
        if (!configFile.exists()) {
            plugin.saveResource("ExperienceTypes.yml", false);
        }
        experienceConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    @EventHandler
public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();
    ItemStack tool = player.getInventory().getItemInMainHand();
    int blockExperience = getExperienceFromBlock(block.getType());
    UUID playerID = player.getUniqueId();

    if (ItemTypes.isMiningTool(tool) && isMiningBlock(block.getType())) {

        Entity entity = block.getLocation().getWorld().spawnEntity(block.getLocation(), EntityType.TEXT_DISPLAY);
        ((TextDisplay) entity).setText(ChatColor.GOLD + "+ " + blockExperience + " XP");
        ((TextDisplay) entity).setSeeThrough(true);
        ((TextDisplay) entity).setBillboard(Billboard.CENTER);
        entity.teleport(block.getLocation().add(0, 1.5, 0));

        PlayerData playerData = playerDataManager.getPlayerData(playerID);
        if (playerData == null) {
            playerData = new PlayerData(playerID);
            playerDataManager.addPlayerData(playerData);
        }

        playerData.addMiningExperience(blockExperience);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                entity.remove();
            }
        }, 60L);

    } else if (ItemTypes.isWoodcuttingTool(tool) && isWoodcuttingBlock(block.getType())) {
        Entity entity = block.getLocation().getWorld().spawnEntity(block.getLocation(), EntityType.TEXT_DISPLAY);
        ((TextDisplay) entity).setText(ChatColor.GOLD + "+ " + blockExperience + " XP");
        ((TextDisplay) entity).setSeeThrough(true);
        ((TextDisplay) entity).setBillboard(Billboard.CENTER);
        entity.teleport(block.getLocation().add(0, 1.5, 0));

        PlayerData playerData = playerDataManager.getPlayerData(playerID);
        if (playerData == null) {
            playerData = new PlayerData(playerID);
            playerDataManager.addPlayerData(playerData);
        }

        playerData.addWoodcuttingExperience(blockExperience);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                entity.remove();
            }
        }, 60L);

    } else {
        player.sendMessage(ChatColor.of("#FF3333") + "You cannot break this block.");
    }
}

    private boolean isMiningBlock(Material blockType) {
        for (Material material : BlockTypes.MINING_SKILL_BLOCKS) {
            if (blockType == material) {
                return true;
            }
        }
        return false;
    }

    private boolean isWoodcuttingBlock(Material blockType) {
        for (Material material : BlockTypes.WOODCUTTING_SKILL_BLOCKS) {
            if (blockType == material) {
                return true;
            }
        }
        return false;
    }

    private int getExperienceFromBlock(Material blockType) {
        if (experienceConfig.contains(blockType.toString())) {
            return experienceConfig.getInt(blockType.toString());
        }
        return 0;
    }
}
