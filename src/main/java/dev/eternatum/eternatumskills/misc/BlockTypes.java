package dev.eternatum.eternatumskills.misc;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class BlockTypes {
    private static final List<Material> miningBlockTypes = Arrays.asList(
            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.IRON_ORE,
            Material.COPPER_ORE,
            Material.GOLD_ORE,
            Material.DIAMOND_ORE,
            Material.COAL_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS
    );

    private static final List<Material> woodcuttingBlockTypes = Arrays.asList(
            Material.OAK_LOG,
            Material.BIRCH_LOG,
            Material.SPRUCE_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.CRIMSON_STEM,
            Material.WARPED_STEM
    );

    public static boolean isMiningBlockType(Material material) {
        return miningBlockTypes.contains(material);
    }

    public static boolean isWoodcuttingBlockType(Material material) {
        return woodcuttingBlockTypes.contains(material);
    }
}
