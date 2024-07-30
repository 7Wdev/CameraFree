package org.geyserplus.spigot.sevenwdev.api;

import org.geyserplus.spigot.sevenwdev.player.PlayerDevice;
import org.geyserplus.spigot.sevenwdev.player.PlayerInputType;
import org.geyserplus.spigot.sevenwdev.player.PlayerUIProfile;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.geyser.api.bedrock.camera.CameraData;

import java.util.UUID;

public abstract class BedrockPluginAPI implements IBedrockPluginAPI {
    public BedrockPluginAPI() {

    }

    public abstract boolean isBedrockPlayer(UUID uuid);

    public abstract boolean sendForm(UUID uuid, Form form);

    public abstract boolean sendForm(UUID uuid, FormBuilder<?, ?, ?> form);

    public abstract PlayerDevice getPlayerDevice(UUID uuid);

    public abstract PlayerInputType getPlayerInputType(UUID uuid);

    public abstract PlayerUIProfile getPlayerUIProfile(UUID uuid);

    public abstract String getXboxUsername(UUID uuid);

    public abstract String getPlayerXUID(UUID uuid);

    public abstract CameraData getCamera(UUID uuid);

    public abstract boolean isLinked(UUID uuid);

    public abstract void hidePaperDoll(UUID uuid);
}
