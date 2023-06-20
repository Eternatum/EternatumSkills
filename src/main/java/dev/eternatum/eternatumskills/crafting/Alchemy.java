package dev.eternatum.eternatumskills.crafting;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Alchemy implements Listener {
    private Plugin plugin;

    public Alchemy(Plugin plugin) {
        this.plugin = plugin;
    }

    public void brewPotion(Player player, ItemStack potionItem) {
        if (potionItem.getType() != Material.POTION) {
            return;
        }

        PotionMeta potionMeta = (PotionMeta) potionItem.getItemMeta();
        PotionData potionData = potionMeta.getBasePotionData();

        if (potionData.getType() != PotionType.WATER) {
            double experience = getAlchemyExperience(potionData, potionItem);
            giveExperienceToPlayer(player, experience);
        }
    }

    private double getAlchemyExperience(PotionData potionData, ItemStack brewedPotion) {
        double experience = 0;

        if (potionData.isExtended()) {
            experience *= 1.5;
        } else if (isLingeringPotion(brewedPotion)) {
            experience *= 2.0;
        }

        return experience;
    }

    private boolean isLingeringPotion(ItemStack potionItem) {
        return potionItem.getType() == Material.LINGERING_POTION;
    }

    private void giveExperienceToPlayer(Player player, double experience) {
        // Replace this with the logic to give experience to the player
        // For example:
        // YourExperiencePlugin.giveExperience(player, experience);
        // Or use the appropriate method from your experience plugin or system


    }
}

