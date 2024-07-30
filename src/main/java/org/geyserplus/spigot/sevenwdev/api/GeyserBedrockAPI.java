package org.geyserplus.spigot.sevenwdev.api;

import org.geyserplus.spigot.sevenwdev.player.PlayerDevice;
import org.geyserplus.spigot.sevenwdev.player.PlayerInputType;
import org.geyserplus.spigot.sevenwdev.player.PlayerUIProfile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.event.subscribe.OwnedSubscriber;
import org.geysermc.geyser.api.bedrock.camera.CameraData;
import org.geysermc.geyser.api.bedrock.camera.GuiElement;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geysermc.geyser.api.pack.PackCodec;

import java.io.ObjectInputFilter.Config;
import java.util.*;
import java.util.logging.Filter;

@SuppressWarnings("unused")
public class GeyserBedrockAPI extends BedrockPluginAPI implements org.geysermc.geyser.api.event.EventRegistrar {
    private final org.geysermc.geyser.api.GeyserApi api = org.geysermc.geyser.api.GeyserApi.api();

    public GeyserBedrockAPI() {
        super();
        tryRegisterEventBus();
    }

    @Override
    public void disable() {
    }

    ArrayList<OwnedSubscriber<?, ?>> subscribers = new ArrayList<>();

    private void tryRegisterEventBus() {
        subscribers.add(
                api.eventBus().subscribe(this, org.geysermc.geyser.api.event.bedrock.SessionDisconnectEvent.class,
                        (ev) -> {
                            if ((ev.disconnectReason().equals("disconnectionScreen.resourcePack"))
                                    || (ev.disconnectReason().equals("Bedrock client disconnected")
                                            && ev.connection().javaUuid() == null)) {
                                  //for testing will remove  later
                            }
                        }));
    }

    @Override
    public boolean isBedrockPlayer(UUID uuid) {
        return api.isBedrockPlayer(uuid);
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
        return switch (Objects.requireNonNull(api.connectionByUuid(uuid)).platform()) {
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
        return switch (Objects.requireNonNull(api.connectionByUuid(uuid)).inputMode()) {
            case CONTROLLER -> PlayerInputType.CONTROLLER;
            case TOUCH -> PlayerInputType.TOUCH;
            case KEYBOARD_MOUSE -> PlayerInputType.KEYBOARD_MOUSE;
            case VR -> PlayerInputType.VR;
            default -> PlayerInputType.UNKNOWN;
        };
    }

    @Override
    public PlayerUIProfile getPlayerUIProfile(UUID uuid) {
        return switch (Objects.requireNonNull(api.connectionByUuid(uuid)).uiProfile()) {
            case CLASSIC -> PlayerUIProfile.CLASSIC;
            case POCKET -> PlayerUIProfile.POCKET;
        };
    }

    @Override
    public String getXboxUsername(UUID uuid) {
        return Objects.requireNonNull(api.connectionByUuid(uuid)).bedrockUsername();
    }

    @Override
    public String getPlayerXUID(UUID uuid) {
        return Objects.requireNonNull(api.connectionByUuid(uuid)).xuid();
    }

    @Override
    public boolean isLinked(UUID uuid) {
        return Objects.requireNonNull(api.connectionByUuid(uuid)).isLinked();
    }

    @Override
    public void hidePaperDoll(UUID uuid) {
        GeyserConnection connection = api.connectionByUuid(uuid);
        if (connection != null)
            if (!connection.camera().isHudElementHidden(GuiElement.PAPER_DOLL)) {
                connection.camera().hideElement(GuiElement.PAPER_DOLL);
            }
    }

    @Override
    public CameraData getCamera(UUID uuid) {
        GeyserConnection connection = api.connectionByUuid(uuid);
        if (connection != null) {
            return connection.camera();
        }
        return null;
    }
}