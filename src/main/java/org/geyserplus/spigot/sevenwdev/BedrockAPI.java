package org.geyserplus.spigot.sevenwdev;

import org.geyserplus.spigot.sevenwdev.api.APIType;
import org.geyserplus.spigot.sevenwdev.api.BedrockPluginAPI;
import org.geyserplus.spigot.sevenwdev.api.FloodgateBedrockAPI;
import org.geyserplus.spigot.sevenwdev.api.GeyserBedrockAPI;
import org.geyserplus.spigot.sevenwdev.player.PlayerDevice;
import org.geyserplus.spigot.sevenwdev.player.PlayerInputType;
import org.geyserplus.spigot.sevenwdev.player.PlayerUIProfile;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.form.Form;
import org.geysermc.geyser.api.bedrock.camera.CameraData;

import java.io.ObjectInputFilter.Config;
import java.util.HashMap;
import java.util.UUID;

public class BedrockAPI {
    private BedrockPluginAPI apiInstance = null;
    public HashMap<APIType, BedrockPluginAPI> apiInstances = new HashMap<>();
    public APIType apiType = null;
    public boolean foundGeyserClasses = false;

    private boolean classExists(String path) {
        boolean exists = false;
        try {
            Class<?> apiClass = Class.forName(path);
            exists = true;
        } catch (ClassNotFoundException ignored) {
        }
        return exists;
    }

    public BedrockAPI() {
        if (classExists("org.geysermc.floodgate.api.FloodgateApi")) {
            apiType = APIType.FLOODGATE;
            apiInstances.put(APIType.FLOODGATE, new FloodgateBedrockAPI());
            foundGeyserClasses = true;
        }
        if (classExists("org.geysermc.api.GeyserApiBase")) {
            apiType = APIType.GEYSER;
            apiInstances.put(APIType.GEYSER, new GeyserBedrockAPI());
            foundGeyserClasses = true;
        }
        if (supports(APIType.FLOODGATE)) {
            apiInstance = apiInstances.get(APIType.FLOODGATE);
        } else if (supports(APIType.GEYSER)) {
            apiInstance = apiInstances.get(APIType.GEYSER);
        }
    }

    public void setDefaultApiInstance(APIType type) {
        apiType = type;
        apiInstance = apiInstances.get(type);
    }

    public boolean isBedrockPlayer(@NonNull UUID uuid) {
        return apiInstance.isBedrockPlayer(uuid);
    }

    public boolean sendForm(@NonNull UUID uuid, @NonNull Form form) {
        return apiInstance.sendForm(uuid, form);
    }

    public PlayerDevice getPlayerDevice(BedrockPlayer bedrockPlayer) {
        return apiInstance.getPlayerDevice(bedrockPlayer.player.getUniqueId());
    }

    public PlayerInputType getPlayerInputType(BedrockPlayer bedrockPlayer) {
        return apiInstance.getPlayerInputType(bedrockPlayer.player.getUniqueId());
    }

    public PlayerUIProfile getPlayerUIProfile(BedrockPlayer bedrockPlayer) {
        return apiInstance.getPlayerUIProfile(bedrockPlayer.player.getUniqueId());
    }

    public String getXboxUsername(BedrockPlayer bedrockPlayer) {
        return apiInstance.getXboxUsername(bedrockPlayer.player.getUniqueId());
    }

    public String getPlayerXUID(BedrockPlayer bedrockPlayer) {
        return apiInstance.getPlayerXUID(bedrockPlayer.player.getUniqueId());
    }

    public boolean isLinked(BedrockPlayer bedrockPlayer) {
        return apiInstance.isLinked(bedrockPlayer.player.getUniqueId());
    }

    public boolean supports(APIType apiType) {
        return apiInstances.containsKey(apiType);
    }

    public CameraData getCamera(BedrockPlayer bedrockPlayer) {
        return apiInstance.getCamera(bedrockPlayer.player.getUniqueId());
    }
}