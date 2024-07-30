package org.geyserplus.spigot.sevenwdev.player;

import org.geyserplus.spigot.sevenwdev.BedrockPlayer;
import org.geyserplus.spigot.sevenwdev.CameraFree;

public enum PlayerUIProfile {
    CLASSIC("Classic"),
    POCKET("Pocket");

    public final String displayName;

    PlayerUIProfile(String displayName) {
        this.displayName = displayName;
    }

    public static PlayerUIProfile getPlayerUIProfile(BedrockPlayer bedrockPlayer) {
        return CameraFree.bedrockAPI.getPlayerUIProfile(bedrockPlayer);
    }
}
