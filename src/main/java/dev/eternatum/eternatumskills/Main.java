package dev.eternatum.eternatumskills;

import dev.eternatum.eternatumskills.combat.Combat;
import dev.eternatum.eternatumskills.crafting.Metallurgy;
import dev.eternatum.eternatumskills.debugging.DebugCommands;
import dev.eternatum.eternatumskills.gathering.Mining;
import dev.eternatum.eternatumskills.listeners.LevelUpHandler;
import dev.eternatum.eternatumskills.gathering.Woodcutting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin implements CommandExecutor, Listener {
    private PlayerDataManager playerDataManager;
    private LevelUpHandler levelUpHandler;
    private Combat combat;
    private Map<UUID, Integer> previousLevels;

    @Override
    public void onEnable() {
        // Instantiate the PlayerDataManager
        playerDataManager = new PlayerDataManager(getDataFolder());

        // Load player data
        playerDataManager.loadData();

        // Initialize the Combat class
        combat = new Combat(this, playerDataManager);

        // Register the Combat class as an event listener
        getServer().getPluginManager().registerEvents(combat, this);

        // Register the TreeChopListener with the PlayerDataManager instance
        getServer().getPluginManager().registerEvents(new Woodcutting(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new Metallurgy(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new Mining(playerDataManager, this), this);

        // Initialize the LevelUpHandler
        levelUpHandler = new LevelUpHandler(playerDataManager, this);

        // Register the LevelUpHandler as a listener
        getServer().getPluginManager().registerEvents(levelUpHandler, this);

        // Initialize the previousLevels map
        previousLevels = new HashMap<>();

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        // Set the command executor
        getCommand("skills").setExecutor(this);
        getCommand("debugskills").setExecutor(new DebugCommands(playerDataManager));
    }

    @Override
    public void onDisable() {
        // Save player data
        playerDataManager.saveData();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerId = player.getUniqueId();

            PlayerData playerData = playerDataManager.getPlayerData(playerId);

            if (playerData != null) {
                sendSkillsInfo(player, playerData);
            } else {
                player.sendMessage(ChatColor.RED + "You don't have any skill data.");
            }
        } else {
            sender.sendMessage("This command can only be used by players.");
        }

        return true;
    }

    private void sendSkillsInfo(Player player, PlayerData playerData) {
        int woodcuttingExp = playerData.getWoodcuttingExperience();
        int woodcuttingLevel = playerData.getWoodcuttingLevel();
        int metallurgyExp = playerData.getMetallurgyExperience();
        int metallurgyLevel = playerData.getMetallurgyLevel();
        int miningExp = playerData.getMiningExperience();
        int miningLevel = playerData.getMiningLevel();

        //This will be displayed when the player runs /skills command
        player.sendMessage(ChatColor.DARK_GRAY + "----------");
        player.sendMessage(ChatColor.DARK_GREEN + "Skill Experience:");
        player.sendMessage(ChatColor.GREEN + "Woodcutting: Level " + woodcuttingLevel +
                " (Exp: " + woodcuttingExp + "/" + playerData.getExpRequiredForLevel(woodcuttingLevel) + ")");
        player.sendMessage(ChatColor.GOLD + "Metallurgy: Level " + metallurgyLevel +
                " (Exp: " + metallurgyExp + "/" + playerData.getExpRequiredForLevel(metallurgyLevel) + ")");
        player.sendMessage(ChatColor.GOLD + "Mining: Level " + miningLevel +
                " (Exp: " + miningExp + "/" + playerData.getExpRequiredForLevel(miningLevel) + ")");
        player.sendMessage(ChatColor.DARK_GRAY + "----------");
    }
}
