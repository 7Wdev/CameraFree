package org.geyserplus.spigot.sevenwdev.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.floodgate.api.event.skin.SkinApplyEvent;
import org.geysermc.geyser.api.bedrock.camera.CameraData;
import org.geysermc.geyser.api.connection.GeyserConnection;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import org.geyserplus.spigot.sevenwdev.player.PlayerDevice;
import org.geyserplus.spigot.sevenwdev.player.PlayerInputType;
import org.geyserplus.spigot.sevenwdev.player.PlayerUIProfile;

@SuppressWarnings("unused")
public class FloodgateBedrockAPI extends BedrockPluginAPI {
    private final org.geysermc.floodgate.api.FloodgateApi api = org.geysermc.floodgate.api.FloodgateApi.getInstance();

    public FloodgateBedrockAPI() {
        super();
        tryRegisterEventBus();
    }

    @Override
    public void disable() {

    }

    private void tryRegisterEventBus() {
    }

    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return api.isFloodgatePlayer(uuid);
    }

    @Override
    public boolean sendForm(UUID uuid, Form form) {
        return api.sendForm(uuid, form);
    }

    @Override
    public boolean sendForm(UUID uuid, FormBuilder<?, ?, ?> form) {
        return this.sendForm(uuid, form.build());
    }

    @Override
    public PlayerDevice getPlayerDevice(UUID uuid) {
        return switch (Objects.requireNonNull(api.getPlayer(uuid)).getDeviceOs()) {
            case GOOGLE -> PlayerDevice.ANDROID;
            case IOS -> PlayerDevice.IOS;
            case OSX -> PlayerDevice.OSX;
            case AMAZON -> PlayerDevice.AMAZON;
            case GEARVR -> PlayerDevice.GEARVR;
            case HOLOLENS -> PlayerDevice.HOLOLENS;
            case UWP, WIN32 -> PlayerDevice.WINDOWS;
            case DEDICATED -> PlayerDevice.DEDICATED;
            case TVOS -> PlayerDevice.TVOS;
            case PS4 -> PlayerDevice.PLAYSTATION;
            case NX -> PlayerDevice.SWITCH;
            case XBOX -> PlayerDevice.XBOX;
            case WINDOWS_PHONE -> PlayerDevice.WINDOWS_PHONE;
            default -> PlayerDevice.UNKNOWN;
        };
    }

    @Override
    public PlayerInputType getPlayerInputType(UUID uuid) {
        return switch (Objects.requireNonNull(api.getPlayer(uuid)).getInputMode()) {
            case CONTROLLER -> PlayerInputType.CONTROLLER;
            case TOUCH -> PlayerInputType.TOUCH;
            case KEYBOARD_MOUSE -> PlayerInputType.KEYBOARD_MOUSE;
            case VR -> PlayerInputType.VR;
            default -> PlayerInputType.UNKNOWN;
        };
    }

    @Override
    public PlayerUIProfile getPlayerUIProfile(UUID uuid) {
        return switch (Objects.requireNonNull(api.getPlayer(uuid)).getUiProfile()) {
            case CLASSIC -> PlayerUIProfile.CLASSIC;
            case POCKET -> PlayerUIProfile.POCKET;
        };
    }

    @Override
    public String getXboxUsername(UUID uuid) {
        return Objects.requireNonNull(api.getPlayer(uuid)).getUsername();
    }

    @Override
    public String getPlayerXUID(UUID uuid) {
        return Objects.requireNonNull(api.getPlayer(uuid)).getXuid();
    }

    @Override
    public boolean isLinked(UUID uuid) {
        return Objects.requireNonNull(api.getPlayer(uuid)).isLinked();
    }

    @Override
    public void hidePaperDoll(UUID uuid) {
        return;
    }

    @Override
    public CameraData getCamera(UUID uuid) {
        org.geysermc.geyser.api.GeyserApi api = org.geysermc.geyser.api.GeyserApi.api();
        GeyserConnection connection = api.connectionByUuid(uuid);
        if (connection != null) {
            return connection.camera();
        }
        return null;        
    }
}
