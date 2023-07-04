package dev.eternatum.eternatumskills.listeners;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import dev.eternatum.eternatumskills.events.SkillLevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelUpHandler implements Listener {
    private PlayerDataManager playerDataManager;
    private Plugin plugin;
    private Map<UUID, Integer> previousWoodcuttingLevels;
    private Map<UUID, Integer> previousMetallurgyLevels;
    private Map<UUID, Integer> previousMiningLevels;

    public LevelUpHandler(PlayerDataManager playerDataManager, Plugin plugin) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
        this.previousWoodcuttingLevels = new HashMap<>();
        this.previousMetallurgyLevels = new HashMap<>();
        this.previousMiningLevels = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        PlayerData playerData = playerDataManager.getPlayerData(playerId);

        if (playerData != null) {
            int woodcuttingLevel = playerData.getSkillLevel("Woodcutting");
            int metallurgyLevel = playerData.getSkillLevel("Metallurgy");
            int miningLevel = playerData.getSkillLevel("Mining");

            previousWoodcuttingLevels.put(playerId, woodcuttingLevel);
            previousMetallurgyLevels.put(playerId, metallurgyLevel);
            previousMiningLevels.put(playerId, miningLevel);
        }
    }

    @EventHandler
    public void onSkillLevelUp(SkillLevelUpEvent event) {
        Player player = event.getPlayer();
        String skillName = event.getSkillName();
        int newLevel = event.getNewLevel();
        int previousLevel = event.getPreviousLevel();

        if (newLevel > previousLevel) {
            sendLevelUpMessage(player, skillName, newLevel);
            playLevelUpSound(player);

            // Update the previous level for the corresponding skill
            UUID playerId = player.getUniqueId();
            switch (skillName) {
                case "Woodcutting":
                    previousWoodcuttingLevels.put(playerId, newLevel);
                    break;
                case "Metallurgy":
                    previousMetallurgyLevels.put(playerId, newLevel);
                    break;
                case "Mining":
                    previousMiningLevels.put(playerId, newLevel);
                    break;
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
