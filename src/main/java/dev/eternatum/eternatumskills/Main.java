package dev.eternatum.eternatumskills;

import dev.eternatum.eternatumskills.combat.Combat;
import dev.eternatum.eternatumskills.crafting.Alchemy;
import dev.eternatum.eternatumskills.crafting.Metallurgy;
import dev.eternatum.eternatumskills.debugging.DebugCommands;
import dev.eternatum.eternatumskills.debugging.ItemUtils;
import dev.eternatum.eternatumskills.gathering.Mining;
import dev.eternatum.eternatumskills.listeners.LevelUpHandler;
import dev.eternatum.eternatumskills.gathering.Woodcutting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
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
        getServer().getPluginManager().registerEvents(new Alchemy(playerDataManager, this), this);

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


        // Assign mining type to default vanilla pickaxes
        assignMiningTypeToPickaxes();
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

        // Check if the command is "/giveminingtool"
        if (command.getName().equalsIgnoreCase("giveminingtool")) {
            if (sender.hasPermission("yourplugin.giveminingtool")) {
                // Create the mining tool
                ItemStack miningTool = ItemUtils.createMiningTool(this, Material.DIAMOND_PICKAXE);

                // Check if the player is online
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.getInventory().addItem(miningTool);
                    player.sendMessage("You have received the mining tool.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            }
        }

        return true;
    }

    public void assignMiningTypeToPickaxes() {
        List<Material> pickaxeMaterials = ItemUtils.getPickaxeMaterials();
        for (Material material : pickaxeMaterials) {
            assignMiningType(material);
        }
    }

    public void assignMiningType(Material pickaxeMaterial) {
        ItemStack pickaxe = new ItemStack(pickaxeMaterial);

        ItemMeta meta = pickaxe.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(this, "type");
        dataContainer.set(key, PersistentDataType.STRING, "mining");

        pickaxe.setItemMeta(meta);
    }

    private void sendSkillsInfo(Player player, PlayerData playerData) {
        int woodcuttingExp = playerData.getWoodcuttingExperience();
        int woodcuttingLevel = playerData.getWoodcuttingLevel();
        int metallurgyExp = playerData.getMetallurgyExperience();
        int metallurgyLevel = playerData.getMetallurgyLevel();
        int miningExp = playerData.getMiningExperience();
        int miningLevel = playerData.getMiningLevel();
        int alchemyExp = playerData.getAlchemyExperience();
        int alchemyLevel = playerData.getAlchemyLevel();

        //This will be displayed when the player runs /skills command
        player.sendMessage(ChatColor.DARK_GRAY + "----------");
        player.sendMessage(ChatColor.DARK_GREEN + "Skill Experience:");
        player.sendMessage(ChatColor.GREEN + "Woodcutting: Level " + woodcuttingLevel +
                " (Exp: " + woodcuttingExp + "/" + playerData.getExpRequiredForLevel(woodcuttingLevel) + ")");
        player.sendMessage(ChatColor.GOLD + "Metallurgy: Level " + metallurgyLevel +
                " (Exp: " + metallurgyExp + "/" + playerData.getExpRequiredForLevel(metallurgyLevel) + ")");
        player.sendMessage(ChatColor.GOLD + "Mining: Level " + miningLevel +
                " (Exp: " + miningExp + "/" + playerData.getExpRequiredForLevel(miningLevel) + ")");
        player.sendMessage(ChatColor.RED + "Alchemy: Level" + alchemyLevel +
                " (Exp: " + alchemyExp + "/" + playerData.getExpRequiredForLevel(alchemyLevel) + ")");
        player.sendMessage(ChatColor.DARK_GRAY + "----------");
    }
}
