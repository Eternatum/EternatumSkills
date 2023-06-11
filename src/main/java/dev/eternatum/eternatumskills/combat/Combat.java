package dev.eternatum.eternatumskills.combat;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class Combat implements Listener {
    private final Plugin plugin;
    private final PlayerDataManager playerDataManager;
    private final Random random;

    public Combat(Plugin plugin, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.random = new Random();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            PlayerData playerData = playerDataManager.getPlayerData(player.getUniqueId());

            if (playerData != null && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                int damage = (int) event.getFinalDamage();
                int expGained = calculateExpGained(damage);

                playerData.addCombatExperience(expGained);
                player.sendMessage(ChatColor.GREEN + "You gained " + expGained + " combat experience!");

                // Show hologram with the experience gained
                // Implement hologram logic here
            }
        }
    }

    private int calculateExpGained(int damage) {
        // Adjust the calculation based on your desired experience gain formula
        return damage / 2;
    }
}
