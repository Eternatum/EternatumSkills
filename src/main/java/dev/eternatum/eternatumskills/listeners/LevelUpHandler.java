package dev.eternatum.eternatumskills.listeners;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelUpHandler implements Listener {
    private PlayerDataManager playerDataManager;
    private Plugin plugin;
    private Map<UUID, Integer> previousWoodcuttingLevels;
    private Map<UUID, Integer> previousMetallurgyLevels;

    public LevelUpHandler(PlayerDataManager playerDataManager, Plugin plugin) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
        this.previousWoodcuttingLevels = new HashMap<>();
        this.previousMetallurgyLevels = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        PlayerData playerData = playerDataManager.getPlayerData(playerId);

        if (playerData != null) {
            int woodcuttingLevel = playerData.getWoodcuttingLevel();
            int metallurgyLevel = playerData.getMetallurgyLevel();

            previousWoodcuttingLevels.put(playerId, woodcuttingLevel);
            previousMetallurgyLevels.put(playerId, metallurgyLevel);
        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        PlayerData playerData = playerDataManager.getPlayerData(playerId);

        if (playerData != null) {
            int newWoodcuttingLevel = playerData.getWoodcuttingLevel();
            int newMetallurgyLevel = playerData.getMetallurgyLevel();

            int previousWoodcuttingLevel = previousWoodcuttingLevels.getOrDefault(playerId, 0);
            int previousMetallurgyLevel = previousMetallurgyLevels.getOrDefault(playerId, 0);

            if (newWoodcuttingLevel > previousWoodcuttingLevel) {
                sendLevelUpMessage(player, "Woodcutting", newWoodcuttingLevel);
                playLevelUpSound(player);

                previousWoodcuttingLevels.put(playerId, newWoodcuttingLevel);
            }

            if (newMetallurgyLevel > previousMetallurgyLevel) {
                sendLevelUpMessage(player, "Metallurgy", newMetallurgyLevel);
                playLevelUpSound(player);

                previousMetallurgyLevels.put(playerId, newMetallurgyLevel);
            }
        }
    }

    private void sendLevelUpMessage(Player player, String skillName, int level) {
        String message = ChatColor.GREEN + "Congratulations! Your " + skillName + " skill has leveled up to level " + level + "!";
        player.sendMessage(message);
    }

    private void playLevelUpSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }
}
