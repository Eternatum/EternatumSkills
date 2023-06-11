package dev.eternatum.eternatumskills;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {
    private File dataFolder;
    private File dataFile;
    private FileConfiguration dataConfig;
    private Map<UUID, PlayerData> playerDataMap;

    public PlayerDataManager(File dataFolder) {
        this.dataFolder = dataFolder;
        this.dataFile = new File(dataFolder, "data.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        this.playerDataMap = new HashMap<>();
    }

    public PlayerData getPlayerData(UUID playerId) {
        return playerDataMap.get(playerId);
    }

    public void addPlayerData(PlayerData playerData) {
        playerDataMap.put(playerData.getPlayerId(), playerData);
    }

    public void saveData() {
        for (PlayerData playerData : playerDataMap.values()) {
            savePlayerData(playerData);
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        if (!dataFile.exists()) {
            return;
        }

        ConfigurationSection playersSection = dataConfig.getConfigurationSection("players");
        if (playersSection == null) {
            return;
        }

        for (String playerIdString : playersSection.getKeys(false)) {
            UUID playerId = UUID.fromString(playerIdString);
            ConfigurationSection playerSection = playersSection.getConfigurationSection(playerIdString);

            int totalExperience = playerSection.getInt("totalExperience");
            int woodcuttingExperience = playerSection.getInt("woodcuttingExperience");
            int metallurgyExperience = playerSection.getInt("metallurgyExperience");
            int miningExperience = playerSection.getInt("miningExperience");

            PlayerData playerData = new PlayerData(playerId);
            playerData.setTotalExperience(totalExperience);
            playerData.setWoodcuttingExperience(woodcuttingExperience);
            playerData.setMetallurgyExperience(metallurgyExperience);
            playerData.setMiningExperience(miningExperience);

            addPlayerData(playerData);
        }
    }

    private void savePlayerData(PlayerData playerData) {
        UUID playerId = playerData.getPlayerId();
        ConfigurationSection playerSection = dataConfig.createSection("players." + playerId.toString());

        playerSection.set("totalExperience", playerData.getTotalExperience());
        playerSection.set("woodcuttingExperience", playerData.getWoodcuttingExperience());
        playerSection.set("metallurgyExperience", playerData.getMetallurgyExperience());
        playerSection.set("miningExperience", playerData.getMiningExperience());
    }
}
