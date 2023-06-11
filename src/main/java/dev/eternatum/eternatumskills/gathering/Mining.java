package dev.eternatum.eternatumskills.gathering;

import dev.eternatum.eternatumskills.PlayerData;
import dev.eternatum.eternatumskills.PlayerDataManager;
import dev.eternatum.eternatumskills.listeners.PersistentDataContainers;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Mining implements Listener {
    private PlayerDataManager playerDataManager;
    private Plugin plugin;


    public Mining(PlayerDataManager playerDataManager, Plugin plugin) {
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
    }

    // Experience values
    int OBSIDIAN_EXP = 30;
    int IRON_EXP = 5;
    int COAL_EXP = 3;
    int GOLD_EXP = 12;
    int DIAMOND_EXP = 40;
    int COPPER_EXP = 3;
    int REDSTONE_EXP = 25;
    int LAPIS_EXP = 25;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Location blockLocation = block.getLocation();

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();



                    // Calculate experience gained based on block type
                    int experience = 0;
                    ChatColor stoneColor = ChatColor.of("#FFFFFF");
                    String stoneTypeName = "";
                    switch (block.getType()) {
                        case IRON_ORE:
                        case DEEPSLATE_IRON_ORE:
                            experience = IRON_EXP;
                            stoneColor = ChatColor.of("#A19D94");
                            stoneTypeName = "Iron";
                            break;
                        case GOLD_ORE:
                        case DEEPSLATE_GOLD_ORE:
                            experience = GOLD_EXP;
                            stoneColor = ChatColor.of("#FFD700");
                            stoneTypeName = "Gold";
                            break;
                        case COAL_ORE:
                        case DEEPSLATE_COAL_ORE:
                            experience = COAL_EXP;
                            stoneColor = ChatColor.of("#36454F");
                            stoneTypeName = "Coal";
                            break;
                        case DIAMOND_ORE:
                        case DEEPSLATE_DIAMOND_ORE:
                            experience = DIAMOND_EXP;
                            stoneColor = ChatColor.of("#B9F2FF");
                            stoneTypeName = "Diamond";
                            break;
                    }

                    // Get the player's unique ID
                    UUID playerId = player.getUniqueId();

                    // Retrieve or create player data
                    PlayerData playerData = playerDataManager.getPlayerData(playerId);
                    if (playerData == null) {
                        playerData = new PlayerData(playerId);
                        playerDataManager.addPlayerData(playerData);
                    }

                    // Add experience to the player's data
                    playerData.addMiningExperience(experience);

                    // Create and customize the armor stand for feedback
                    ArmorStand armorStand = (ArmorStand) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.ARMOR_STAND);
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);
                    armorStand.setCustomName(ChatColor.GOLD + "⛏ +" + stoneColor + experience);
                    armorStand.setCustomNameVisible(true);

                    // Remove the armor stand after 1 second
                    Bukkit.getScheduler().runTaskLater(plugin, armorStand::remove, 60);

                }
            }
        }
    }
}
