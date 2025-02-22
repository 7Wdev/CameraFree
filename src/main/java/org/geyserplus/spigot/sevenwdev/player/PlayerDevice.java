package org.geyserplus.spigot.sevenwdev.player;

import org.geyserplus.spigot.sevenwdev.BedrockPlayer;
import org.geyserplus.spigot.sevenwdev.CameraFree;
import org.bukkit.entity.Player;

public enum PlayerDevice {
    UNKNOWN("Unknown"),
    ANDROID("Android"),
    IOS("iOS"),
    OSX("macOS"),
    AMAZON("Amazon"),
    GEARVR("Gear VR"),
    HOLOLENS("Hololens"),
    WINDOWS("Windows"),
    DEDICATED("Dedicated"),
    TVOS("Apple TV"),
    PLAYSTATION("PlayStation"), // Named "PlayStation" and not "PS4" due to backwards compatibility on
                                // PlayStation 5.
    // Mojang recently announced PS5 support was in the works, so this may change in
    // the future.
    // My guess is that it will be supported in 1.21 (Tricky Trials). Leaving this
    // here so if it ever happens I predicted it!!!!!
    // GUESS WHAT HAPPENED THEY RELEASED A PREVIEW VERSION OF IT FOR PS5 LMAOOO
    PLAYSTATIONFIVE("PlayStation 5"),
    SWITCH("Switch"),
    XBOX("Xbox"), // Named "Xbox" and not "Xbox One" due to backwards compatibility on Xbox Series
                  // X/S.
    WINDOWS_PHONE("Windows Phone"),
    JAVA("Java");

    public final String displayName;

    PlayerDevice(String displayName) {
        this.displayName = displayName;
    }

    public static PlayerDevice getPlayerDevice(BedrockPlayer bedrockPlayer) {
        return CameraFree.bedrockAPI.getPlayerDevice(bedrockPlayer);
    }

    public static PlayerDevice getPlayerDevice(Player player) {
        if (CameraFree.bedrockAPI.isBedrockPlayer(player.getUniqueId())) {
            return CameraFree.bedrockAPI.getPlayerDevice(CameraFree.bplayers.get(player.getUniqueId()));
        } else {
            return JAVA;
        }
    }
}
