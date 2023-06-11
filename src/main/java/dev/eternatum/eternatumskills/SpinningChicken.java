package dev.eternatum.eternatumskills;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SpinningChicken extends JavaPlugin implements Listener {

    private ArrayList<Player> spinningPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spinningchicken")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }
            Player player = (Player) sender;
            ItemStack chickenStick = new ItemStack(Material.STICK);
            ItemMeta meta = chickenStick.getItemMeta();
            meta.setDisplayName("Spinning Chicken Stick");
            chickenStick.setItemMeta(meta);
            player.getInventory().addItem(chickenStick);
            player.sendMessage("You have received a Spinning Chicken Stick!");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (spinningPlayers.contains(player)) {
            event.setCancelled(true);
            EntityType entityType = EntityType.CHICKEN;
            player.getWorld().spawnEntity(player.getLocation(), entityType);
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item != null && item.getType() == Material.STICK) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getDisplayName().equals("Spinning Chicken Stick")) {
                event.setCancelled(true);
                spinningPlayers.add(player);
                player.sendMessage("Right click to spawn spinning chickens!");
                new BukkitRunnable() {
                    int tick = 0;

                    @Override
                    public void run() {
                        tick++;
                        if (tick >= 60) {
                            spinningPlayers.remove(player);
                            player.sendMessage("Your Spinning Chicken Stick has stopped spinning.");
                            this.cancel();
                        }
                    }
                }.runTaskTimer(this, 0L, 1L);
            }
        }
    }
}
