package dev.eternatum.eternatumskills;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {
    private UUID playerId;
    private int totalExperience;
    private int woodcuttingExperience;
    private int woodcuttingLevel;
    private int metallurgyExperience;
    private int metallurgyLevel;
    private int miningExperience;
    private int miningLevel;
    private int combatExperience;
    private Map<String, Integer> skillLevels;

    // Arbitrary experience table for leveling
    static final int[] LEVEL_REQUIREMENTS = {0, 100, 500, 3000, 9000, 22000};

    public PlayerData(UUID playerId) {
        this.playerId = playerId;
        this.totalExperience = 0;
        this.woodcuttingExperience = 0;
        this.woodcuttingLevel = 1;
        this.metallurgyExperience = 0;
        this.metallurgyLevel = 1;
        this.miningExperience = 0;
        this.miningLevel = 1;
        this.combatExperience = 0;
        this.skillLevels = new HashMap<>();
    }

    public int getExpRequiredForLevel(int level) {
        // Check if the level is within the experience table
        if (level >= 1 && level < LEVEL_REQUIREMENTS.length) {
            return LEVEL_REQUIREMENTS[level];
        }
        return 0;
    }


    public enum SkillType {
        WOODCUTTING("Woodcutting"),
        METALLURGY("Metallurgy");

        private final String name;

        SkillType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public UUID getPlayerId() {
        return playerId;
    }
//Experience Getters
    public int getTotalExperience() {
        return totalExperience;
    }

    public int getWoodcuttingExperience() {
        return woodcuttingExperience;
    }

    public int getWoodcuttingLevel() {
        return woodcuttingLevel;
    }

    public int getMetallurgyExperience() {
        return metallurgyExperience;
    }
    public int getMiningExperience() {
        return miningExperience;
    }

    public int getMetallurgyLevel() {
        return metallurgyLevel;
    }
    public int getMiningLevel() {
        return miningLevel;
    }

    public int getCombatExperience() {
        return combatExperience;
    }

    public void setCombatExperience(int combatExperience) {
        this.combatExperience = combatExperience;
    }

    public void addCombatExperience(int experience) {
        this.combatExperience += experience;
    }

    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }

    public void setMiningExperience(int miningExperience) {
        this.miningExperience = miningExperience;
        updateMiningLevel();
    }
    public void addMiningExperience(int experience) {
        this.miningExperience += experience;
        updateMiningLevel();
    }


    public void setWoodcuttingExperience(int woodcuttingExperience) {
        this.woodcuttingExperience = woodcuttingExperience;
        updateWoodcuttingLevel();
    }

    public void setMetallurgyExperience(int metallurgyExperience) {
        this.metallurgyExperience = metallurgyExperience;
        updateMetallurgyLevel();
    }

    public void addExperience(int experience) {
        this.totalExperience += experience;
    }

    public void addWoodcuttingExperience(int experience) {
        this.woodcuttingExperience += experience;
        updateWoodcuttingLevel();
    }

    public void addMetallurgyExperience(int experience) {
        this.metallurgyExperience += experience;
        updateMetallurgyLevel();
    }

    private void updateWoodcuttingLevel() {
        // Find the corresponding level based on experience in the table
        for (int level = 1; level < LEVEL_REQUIREMENTS.length; level++) {
            if (woodcuttingExperience < LEVEL_REQUIREMENTS[level]) {
                this.woodcuttingLevel = level;
                break;
            }
        }
    }

    private void updateMetallurgyLevel() {
        // Find the corresponding level based on experience in the table
        for (int level = 1; level < LEVEL_REQUIREMENTS.length; level++) {
            if (metallurgyExperience < LEVEL_REQUIREMENTS[level]) {
                this.metallurgyLevel = level;
                break;
            }
        }
    }

    private void updateMiningLevel() {
        for (int level = 1; level < LEVEL_REQUIREMENTS.length; level++) {
            if (miningExperience < LEVEL_REQUIREMENTS[level]) {
                this.miningLevel = level;
                break;
            }
        }
    }

    // Set the level for a specific skill
    public void setSkillLevel(String skillName, int level) {
        skillLevels.put(skillName, level);
    }

    // Get the level for a specific skill
    public int getSkillLevel(String skillName) {
        return skillLevels.getOrDefault(skillName, 0);
    }
}