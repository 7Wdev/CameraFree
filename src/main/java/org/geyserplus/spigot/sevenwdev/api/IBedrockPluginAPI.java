package org.geyserplus.spigot.sevenwdev.api;

import org.geyserplus.spigot.sevenwdev.player.PlayerDevice;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;

import java.util.UUID;

interface IBedrockPluginAPI {
    void disable();

    boolean isBedrockPlayer(UUID uuid);

    boolean sendForm(UUID uuid, Form form);

    boolean sendForm(UUID uuid, FormBuilder<?, ?, ?> form);

    PlayerDevice getPlayerDevice(UUID uuid);

    String getXboxUsername(UUID uuid);

    String getPlayerXUID(UUID uuid);

    boolean isLinked(UUID uuid);

    void hidePaperDoll(UUID uuid);
}