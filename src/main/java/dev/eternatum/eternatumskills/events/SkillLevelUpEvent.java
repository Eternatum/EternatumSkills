package dev.eternatum.eternatumskills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillLevelUpEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String skillName;
    private final int newLevel;
    private final int previousLevel;

    public SkillLevelUpEvent(Player player, String skillName, int newLevel, int previousLevel) {
        this.player = player;
        this.skillName = skillName;
        this.newLevel = newLevel;
        this.previousLevel = previousLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
