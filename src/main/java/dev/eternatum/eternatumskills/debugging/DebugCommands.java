package dev.eternatum.eternatumskills.debugging;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DebugCommands implements CommandExecutor {
    private PlayerDataManager playerDataManager;

    public DebugCommands(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Invalid command format. Usage: /debugskills <add|remove> <player> <skill> <amount>");
            return true;
        }

        String action = args[0].toLowerCase();
        String playerName = args[1];
        String skill = args[2].toLowerCase();
        int amount;

        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid amount. Please provide a valid integer value.");
            return true;
        }

        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player name. The player must be online.");
            return true;
        }

        UUID playerId = player.getUniqueId();
        PlayerData playerData = playerDataManager.getPlayerData(playerId);

        if (playerData == null) {
            sender.sendMessage(ChatColor.RED + "The specified player doesn't have any skill data.");
            return true;
        }

        if (action.equals("add")) {
            if (skill.equals("woodcutting")) {
                playerData.addWoodcuttingExperience(amount);
                sender.sendMessage(ChatColor.GREEN + "Added " + amount + " experience to Woodcutting skill for player " + playerName + ".");
            } else if (skill.equals("metallurgy")) {
                playerData.addMetallurgyExperience(amount);
                sender.sendMessage(ChatColor.GREEN + "Added " + amount + " experience to Metallurgy skill for player " + playerName + ".");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid skill. Supported skills: Woodcutting, Metallurgy.");
            }
        } else if (action.equals("remove")) {
            if (skill.equals("woodcutting")) {
                int currentExperience = playerData.getWoodcuttingExperience();
                int removedExperience = Math.min(currentExperience, amount);
                playerData.setWoodcuttingExperience(currentExperience - removedExperience);
                sender.sendMessage(ChatColor.GREEN + "Removed " + removedExperience + " experience from Woodcutting skill for player " + playerName + ".");
            } else if (skill.equals("metallurgy")) {
                int currentExperience = playerData.getMetallurgyExperience();
                int removedExperience = Math.min(currentExperience, amount);
                playerData.setMetallurgyExperience(currentExperience - removedExperience);
                sender.sendMessage(ChatColor.GREEN + "Removed " + removedExperience + " experience from Metallurgy skill for player " + playerName + ".");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid skill. Supported skills: Woodcutting, Metallurgy.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid action. Supported actions: add, remove.");
        }

        return true;
    }
}
