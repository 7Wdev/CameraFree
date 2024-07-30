package org.geyserplus.spigot.sevenwdev.menu;

import org.geyserplus.spigot.sevenwdev.CameraFree;
import org.geyserplus.spigot.sevenwdev.camera.CameraController;
import org.geyserplus.spigot.sevenwdev.BedrockPlayer;
import org.geyserplus.spigot.sevenwdev.form.BedrockContextMenu;
import org.geyserplus.spigot.sevenwdev.form.BedrockForm;
import org.geyserplus.spigot.sevenwdev.form.elements.Button;
import org.geyserplus.spigot.sevenwdev.form.elements.Dropdown;
import org.geyserplus.spigot.sevenwdev.form.elements.Label;
import org.geyserplus.spigot.sevenwdev.form.elements.Slider;
import org.geyserplus.spigot.sevenwdev.form.elements.TextInput;
import org.geyserplus.spigot.sevenwdev.form.elements.Toggle;
import org.geyserplus.spigot.sevenwdev.player.PlayerCamSettings;
import java.util.*;

public class CameraMenu extends BedrockForm {
        private boolean didPresetChange = false;

        public CameraMenu(BedrockPlayer bplayer) {
                super("Camera Settings Menu");

                PlayerCamSettings settings = PlayerCamSettings.get(bplayer.player.getUniqueId());

                String currentPreset = settings.getCurrentPreset();
                CameraFree.getPluginLogger().info("Current Preset: " + currentPreset);
                add(new Dropdown("Preset Settings (Reset all settings)",
                                Arrays.asList("Custom", "Shoulder Camera", "Dynamic Camera", "Long Distance Camera",
                                                "Center Camera"),
                                currentPreset, (value) -> {
                                        if (currentPreset != value) {
                                                didPresetChange = true;
                                                settings.applyPreset(value);
                                                settings.setCurrentPreset(value);
                                        }
                                }));

                add(new Toggle("Dynamic First Person", settings.isDynamicFirstPerson(), (value) -> {

                        if (value != settings.isDynamicFirstPerson() && !didPresetChange) {
                                settings.setDynamicFirstPerson(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("Vertical Smooth Transition", settings.isVerticalSmoothTransition(), (value) -> {
                        if (value != settings.isVerticalSmoothTransition() && !didPresetChange) {
                                settings.setVerticalSmoothTransition(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Vertical Smooth Speed", 1, 60, 1, settings.getVerticalSmoothSpeed(), (value) -> {
                        if (value != settings.getVerticalSmoothSpeed() && !didPresetChange) {
                                settings.setVerticalSmoothSpeed(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("Advance Camera Rotation", settings.isAdvanceCameraRotation(), (value) -> {
                        if (value != settings.isAdvanceCameraRotation() && !didPresetChange) {
                                settings.setAdvanceCameraRotation(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("Follow Camera Movement Speed", settings.isFollowCameraMovemnetSpeed(), (value) -> {
                        if (value != settings.isFollowCameraMovemnetSpeed() && !didPresetChange) {
                                settings.setFollowCameraMovemnetSpeed(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Follow Camera Speed Strength", 1, 100, 1,
                                settings.getFollowCameraMovemnetSpeedStrength(), (value) -> {
                                        if (value != settings.getFollowCameraMovemnetSpeedStrength()
                                                        && !didPresetChange) {
                                                settings.setFollowCameraMovemnetSpeedStrength(value);
                                                settings.setCurrentPreset("Custom");
                                        }
                                }));
                add(new Dropdown("Scope Perspective", Arrays.asList("None", "Aim Mode", "First Person"),
                                settings.getScopePerspective(), (value) -> {
                                        if (value != settings.getScopePerspective() && !didPresetChange) {
                                                settings.setScopePerspective(value);
                                                settings.setCurrentPreset("Custom");
                                        }
                                }));
                add(new Slider("Aim Camera Distance", -5, 5, 1, settings.getAimCameraDistance(), (value) -> {
                        if (value != settings.getAimCameraDistance() && !didPresetChange) {
                                settings.setAimCameraDistance(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("Target Stabilizer", settings.isTargetStabilizer(), (value) -> {
                        if (value != settings.isTargetStabilizer() && !didPresetChange) {
                                settings.setTargetStabilizer(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("Peek When Sneak", settings.isPeekWhenSneak(), (value) -> {
                        if (value != settings.isPeekWhenSneak() && !didPresetChange) {
                                settings.setPeekWhenSneak(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Peek Camera Distance", -4, 10, 1, settings.getPeekCameraDistance(), (value) -> {
                        if (value != settings.getPeekCameraDistance() && !didPresetChange) {
                                settings.setPeekCameraDistance(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Riding Camera Dist", 0, 6, 1, settings.getRidingCameraDist(), (value) -> {
                        if (value != settings.getRidingCameraDist() && !didPresetChange) {
                                settings.setRidingCameraDist(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Riding Camera Dist Speed", 0, 20, 1, settings.getRidingCameraDistSpeed(), (value) -> {
                        if (value != settings.getRidingCameraDistSpeed() && !didPresetChange) {
                                settings.setRidingCameraDistSpeed(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Dropdown("Crosshair Type", Arrays.asList("Dynamic", "Type 1", "Type 2", "Type 3",
                                "None"), settings.getCrosshairType(),
                                (value) -> {
                                        if (value != settings.getCrosshairType() && !didPresetChange) {
                                                settings.setCrosshairType(value);
                                                settings.setCurrentPreset("Custom");
                                        }
                                }));
                add(new Toggle("Free Look", settings.isFreeLook(), (value) -> {
                        if (value != settings.isFreeLook() && !didPresetChange) {
                                settings.setFreeLook(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("MainhandItem First Person", settings.isMainhandItemFirstPerson(), (value) -> {
                        if (value != settings.isMainhandItemFirstPerson() && !didPresetChange) {
                                settings.setMainhandItemFirstPerson(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new TextInput("Mainhand Item Id", settings.getMainhandItemId(), (value) -> {
                        if (value != settings.getMainhandItemId() && !didPresetChange) {
                                settings.setMainhandItemId(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Additional Distance", -15, 40, 1, settings.getAdditionalDistance(), (value) -> {
                        if (value != settings.getAdditionalDistance() && !didPresetChange) {
                                settings.setAdditionalDistance(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("X Camera Offset", -10, 10, 1, settings.getXCameraOffset(), (value) -> {
                        if (value != settings.getXCameraOffset() && !didPresetChange) {
                                settings.setXCameraOffset(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Y Camera Offset", -10, 10, 1, settings.getYCameraOffset(), (value) -> {
                        if (value != settings.getYCameraOffset() && !didPresetChange) {
                                settings.setYCameraOffset(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Slider("Camera Mode Movement Speed", 1, 20, 1, settings.getCameraModeMovementSpeed(),
                                (value) -> {
                                        if (value != settings.getCameraModeMovementSpeed() && !didPresetChange) {
                                                settings.setCameraModeMovementSpeed(value);
                                                settings.setCurrentPreset("Custom");
                                        }
                                }));
                add(new Slider("Camera Mode Maximal Distance", 1, 52, 1, settings.getCameraModeMaximalDistance(),
                                (value) -> {
                                        if (value != settings.getCameraModeMaximalDistance() && !didPresetChange) {
                                                settings.setCameraModeMaximalDistance(value);
                                                settings.setCurrentPreset("Custom");
                                        }
                                }));
                add(new Toggle("360° Camera Mode Rotation", settings.isTwopi_cameraModeRotation(), (value) -> {
                        if (value != settings.isTwopi_cameraModeRotation() && !didPresetChange) {
                                settings.setTwopi_cameraModeRotation(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));
                add(new Toggle("Show Camera Tooltip", settings.isShowCameraTooltip(), (value) -> {
                        if (value != settings.isShowCameraTooltip() && !didPresetChange) {
                                settings.setShowCameraTooltip(value);
                                settings.setCurrentPreset("Custom");
                        }
                }));

                this.show(bplayer);
        }
}