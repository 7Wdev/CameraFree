package org.geyserplus.spigot.sevenwdev.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCamSettings {
    private static Map<UUID, PlayerCamSettings> settings = new HashMap<>();

    private boolean dynamicFirstPerson = true;
    private boolean verticalSmoothTransition = true;
    private float verticalSmoothSpeed = 10;
    private boolean advanceCameraRotation = true;
    private boolean followCameraMovemnetSpeed = false;
    private float followCameraMovemnetSpeedStrength = 15;
    private String scopePerspective = "Aim Mode";
    private float aimCameraDistance = 1;
    private boolean targetStabilizer = true;
    private boolean peekWhenSneak = true;
    private float peekCameraDistance = 1;
    private float ridingCameraDist = 2;
    private float ridingCameraDistSpeed = 10;
    private String crosshairType = "Dynamic";
    private boolean freeLook = true;
    private boolean mainhandItemFirstPerson = false;
    private String mainhandItemId = "Input Here";
    private float additionalDistance = 0;
    private float xCameraOffset = 0;
    private float yCameraOffset = 0;
    private float cameraModeMovementSpeed = 5;
    private float cameraModeMaximalDistance = 20;
    private boolean twopi_cameraModeRotation = false;
    private boolean showCameraTooltip = false;

    private String currentPreset = "Custom";

    // Preset configurations
    private static final Map<String, Map<String, Object>> presets = new HashMap<>();

    static {
        //Custom
        Map<String, Object> customPreset = new HashMap<>();
        presets.put("Custom", customPreset);
         
        //Shoulder Camera
        Map<String, Object> shoulderCameraPreset = new HashMap<>();
        shoulderCameraPreset.put("dynamicFirstPerson", true);
        shoulderCameraPreset.put("verticalSmoothTransition", true);
        shoulderCameraPreset.put("verticalSmoothSpeed", 10);
        shoulderCameraPreset.put("advanceCameraRotation", true);
        shoulderCameraPreset.put("followCameraMovemnetSpeed", false);
        shoulderCameraPreset.put("followCameraMovemnetSpeedStrength", 15);
        shoulderCameraPreset.put("scopePerspective", "Aim Mode");
        shoulderCameraPreset.put("aimCameraDistance", 1);
        shoulderCameraPreset.put("targetStabilizer", true);
        shoulderCameraPreset.put("peekWhenSneak", true);
        shoulderCameraPreset.put("peekCameraDistance", 1);
        shoulderCameraPreset.put("ridingCameraDist", 2);
        shoulderCameraPreset.put("ridingCameraDistSpeed", 10);
        shoulderCameraPreset.put("crosshairType", "Dynamic");
        shoulderCameraPreset.put("freeLook", true);
        shoulderCameraPreset.put("mainhandItemFirstPerson", false);
        shoulderCameraPreset.put("mainhandItemId", "");
        shoulderCameraPreset.put("additionalDistance", 0);
        shoulderCameraPreset.put("xCameraOffset", 0);
        shoulderCameraPreset.put("yCameraOffset", 0);
        shoulderCameraPreset.put("cameraModeMovementSpeed", 5);
        shoulderCameraPreset.put("cameraModeMaximalDistance", 20);
        shoulderCameraPreset.put("twopi_cameraModeRotation", false);
        shoulderCameraPreset.put("showCameraTooltip", false);
        presets.put("Shoulder Camera", shoulderCameraPreset);
        
        //Dynamic Camera
        Map<String, Object> dynamicCameraPreset = new HashMap<>();
        dynamicCameraPreset.put("dynamicFirstPerson", true);
        dynamicCameraPreset.put("verticalSmoothTransition", true);
        dynamicCameraPreset.put("verticalSmoothSpeed", 10);
        dynamicCameraPreset.put("advanceCameraRotation", true);
        dynamicCameraPreset.put("followCameraMovemnetSpeed", false);
        dynamicCameraPreset.put("followCameraMovemnetSpeedStrength", 15);
        dynamicCameraPreset.put("scopePerspective", "Aim Mode");
        dynamicCameraPreset.put("aimCameraDistance", 1);
        dynamicCameraPreset.put("targetStabilizer", true);
        dynamicCameraPreset.put("peekWhenSneak", true);
        dynamicCameraPreset.put("peekCameraDistance", 3);
        dynamicCameraPreset.put("ridingCameraDist", 2);
        dynamicCameraPreset.put("ridingCameraDistSpeed", 10);
        dynamicCameraPreset.put("crosshairType", "Dynamic");
        dynamicCameraPreset.put("freeLook", true);
        dynamicCameraPreset.put("mainhandItemFirstPerson", false);
        dynamicCameraPreset.put("mainhandItemId", "");
        dynamicCameraPreset.put("additionalDistance", 0);
        dynamicCameraPreset.put("xCameraOffset", 0);
        dynamicCameraPreset.put("yCameraOffset", 0);
        dynamicCameraPreset.put("cameraModeMovementSpeed", 4);
        dynamicCameraPreset.put("cameraModeMaximalDistance", 20);
        dynamicCameraPreset.put("twopi_cameraModeRotation", false);
        dynamicCameraPreset.put("showCameraTooltip", true);
        presets.put("Dynamic Camera", dynamicCameraPreset);

        //Long Distance Camera
        Map<String, Object> longDistanceCameraPreset = new HashMap<>();
        longDistanceCameraPreset.put("dynamicFirstPerson", true);
        longDistanceCameraPreset.put("verticalSmoothTransition", true);
        longDistanceCameraPreset.put("verticalSmoothSpeed", 60);
        longDistanceCameraPreset.put("advanceCameraRotation", false);
        longDistanceCameraPreset.put("followCameraMovemnetSpeed", true);
        longDistanceCameraPreset.put("followCameraMovemnetSpeedStrength", 43);
        longDistanceCameraPreset.put("scopePerspective", "None");
        longDistanceCameraPreset.put("aimCameraDistance", 5);
        longDistanceCameraPreset.put("targetStabilizer", true);
        longDistanceCameraPreset.put("peekWhenSneak", false);
        longDistanceCameraPreset.put("peekCameraDistance", 0);
        longDistanceCameraPreset.put("ridingCameraDist", 6);
        longDistanceCameraPreset.put("ridingCameraDistSpeed", 20);
        longDistanceCameraPreset.put("crosshairType", "Dynamic");
        longDistanceCameraPreset.put("freeLook", true);
        longDistanceCameraPreset.put("mainhandItemFirstPerson", false);
        longDistanceCameraPreset.put("mainhandItemId", "");
        longDistanceCameraPreset.put("additionalDistance", 40);
        longDistanceCameraPreset.put("xCameraOffset", 0);
        longDistanceCameraPreset.put("yCameraOffset", 2);
        longDistanceCameraPreset.put("cameraModeMovementSpeed", 5);
        longDistanceCameraPreset.put("cameraModeMaximalDistance", 21);
        longDistanceCameraPreset.put("twopi_cameraModeRotation", false);
        longDistanceCameraPreset.put("showCameraTooltip", true);
        presets.put("Long Distance Camera", longDistanceCameraPreset);
        
        //Center Camera
        Map<String, Object> centerCameraPreset = new HashMap<>();
        centerCameraPreset.put("dynamicFirstPerson", true);
        centerCameraPreset.put("verticalSmoothTransition", true);
        centerCameraPreset.put("verticalSmoothSpeed", 10);
        centerCameraPreset.put("advanceCameraRotation", false);
        centerCameraPreset.put("followCameraMovemnetSpeed", true);
        centerCameraPreset.put("followCameraMovemnetSpeedStrength", 16);
        centerCameraPreset.put("scopePerspective", "None");
        centerCameraPreset.put("aimCameraDistance", 1);
        centerCameraPreset.put("targetStabilizer", true);
        centerCameraPreset.put("peekWhenSneak", true);
        centerCameraPreset.put("peekCameraDistance", 0);
        centerCameraPreset.put("ridingCameraDist", 1);
        centerCameraPreset.put("ridingCameraDistSpeed", 10);
        centerCameraPreset.put("crosshairType", "Dynamic");
        centerCameraPreset.put("freeLook", false);
        centerCameraPreset.put("mainhandItemFirstPerson", false);
        centerCameraPreset.put("mainhandItemId", "");
        centerCameraPreset.put("additionalDistance", 0);
        centerCameraPreset.put("xCameraOffset", 0);
        centerCameraPreset.put("yCameraOffset", 1);
        centerCameraPreset.put("cameraModeMovementSpeed", 5);
        centerCameraPreset.put("cameraModeMaximalDistance", 21);
        centerCameraPreset.put("twopi_cameraModeRotation", false);
        centerCameraPreset.put("showCameraTooltip", true);
        presets.put("Center Camera", centerCameraPreset);
    }

    public static PlayerCamSettings get(UUID uuid) {
        return settings.computeIfAbsent(uuid, k -> new PlayerCamSettings());
    }

    public void applyPreset(String preset) {
        if (presets.containsKey(preset)) {
            Map<String, Object> presetSettings = presets.get(preset);
            dynamicFirstPerson = (Boolean) presetSettings.getOrDefault("dynamicFirstPerson", dynamicFirstPerson);
            verticalSmoothTransition = (Boolean) presetSettings.getOrDefault("verticalSmoothTransition",
                    verticalSmoothTransition);
            verticalSmoothSpeed = ((Number) presetSettings.getOrDefault("verticalSmoothSpeed", verticalSmoothSpeed))
                    .floatValue();
            advanceCameraRotation = (Boolean) presetSettings.getOrDefault("advanceCameraRotation",
                    advanceCameraRotation);
            followCameraMovemnetSpeed = (Boolean) presetSettings.getOrDefault("followCameraMovemnetSpeed",
                    followCameraMovemnetSpeed);
            followCameraMovemnetSpeedStrength = ((Number) presetSettings
                    .getOrDefault("followCameraMovemnetSpeedStrength", followCameraMovemnetSpeedStrength)).floatValue();
            scopePerspective = (String) presetSettings.getOrDefault("scopePerspective", scopePerspective);
            aimCameraDistance = ((Number) presetSettings.getOrDefault("aimCameraDistance", aimCameraDistance))
                    .floatValue();
            targetStabilizer = (Boolean) presetSettings.getOrDefault("targetStabilizer", targetStabilizer);
            peekWhenSneak = (Boolean) presetSettings.getOrDefault("peekWhenSneak", peekWhenSneak);
            peekCameraDistance = ((Number) presetSettings.getOrDefault("peekCameraDistance", peekCameraDistance))
                    .floatValue();
            ridingCameraDist = ((Number) presetSettings.getOrDefault("ridingCameraDist", ridingCameraDist))
                    .floatValue();
            ridingCameraDistSpeed = ((Number) presetSettings.getOrDefault("ridingCameraDistSpeed",
                    ridingCameraDistSpeed)).floatValue();
            crosshairType = (String) presetSettings.getOrDefault("crosshairType", crosshairType);
            freeLook = (Boolean) presetSettings.getOrDefault("freeLook", freeLook);
            mainhandItemFirstPerson = (Boolean) presetSettings.getOrDefault("mainhandItemFirstPerson",
                    mainhandItemFirstPerson);
            mainhandItemId = (String) presetSettings.getOrDefault("mainhandItemId", mainhandItemId);
            additionalDistance = ((Number) presetSettings.getOrDefault("additionalDistance", additionalDistance))
                    .floatValue();
            xCameraOffset = ((Number) presetSettings.getOrDefault("xCameraOffset", xCameraOffset)).floatValue();
            yCameraOffset = ((Number) presetSettings.getOrDefault("yCameraOffset", yCameraOffset)).floatValue();
            cameraModeMovementSpeed = ((Number) presetSettings.getOrDefault("cameraModeMovementSpeed",
                    cameraModeMovementSpeed)).floatValue();
            cameraModeMaximalDistance = ((Number) presetSettings.getOrDefault("cameraModeMaximalDistance",
                    cameraModeMaximalDistance)).floatValue();
            twopi_cameraModeRotation = (Boolean) presetSettings.getOrDefault("twopi_cameraModeRotation",
                    twopi_cameraModeRotation);
            showCameraTooltip = (Boolean) presetSettings.getOrDefault("showCameraTooltip", showCameraTooltip);
        }
    }

    // Getters and setters for each setting
    public boolean isDynamicFirstPerson() {
        return dynamicFirstPerson;
    }

    public void setDynamicFirstPerson(boolean dynamicFirstPerson) {
        this.dynamicFirstPerson = dynamicFirstPerson;
    }

    public boolean isVerticalSmoothTransition() {
        return verticalSmoothTransition;
    }

    public void setVerticalSmoothTransition(boolean verticalSmoothTransition) {
        this.verticalSmoothTransition = verticalSmoothTransition;
    }

    public float getVerticalSmoothSpeed() {
        return verticalSmoothSpeed;
    }

    public void setVerticalSmoothSpeed(float verticalSmoothSpeed) {
        this.verticalSmoothSpeed = verticalSmoothSpeed;
    }

    public boolean isAdvanceCameraRotation() {
        return advanceCameraRotation;
    }

    public void setAdvanceCameraRotation(boolean advanceCameraRotation) {
        this.advanceCameraRotation = advanceCameraRotation;
    }

    public boolean isFollowCameraMovemnetSpeed() {
        return followCameraMovemnetSpeed;
    }

    public void setFollowCameraMovemnetSpeed(boolean followCameraMovemnetSpeed) {
        this.followCameraMovemnetSpeed = followCameraMovemnetSpeed;
    }

    public float getFollowCameraMovemnetSpeedStrength() {
        return followCameraMovemnetSpeedStrength;
    }

    public void setFollowCameraMovemnetSpeedStrength(float followCameraMovemnetSpeedStrength) {
        this.followCameraMovemnetSpeedStrength = followCameraMovemnetSpeedStrength;
    }

    public String getScopePerspective() {
        return scopePerspective;
    }

    public void setScopePerspective(String scopePerspective) {
        this.scopePerspective = scopePerspective;
    }

    public float getAimCameraDistance() {
        return aimCameraDistance;
    }

    public void setAimCameraDistance(float aimCameraDistance) {
        this.aimCameraDistance = aimCameraDistance;
    }

    public boolean isTargetStabilizer() {
        return targetStabilizer;
    }

    public void setTargetStabilizer(boolean targetStabilizer) {
        this.targetStabilizer = targetStabilizer;
    }

    public boolean isPeekWhenSneak() {
        return peekWhenSneak;
    }

    public void setPeekWhenSneak(boolean peekWhenSneak) {
        this.peekWhenSneak = peekWhenSneak;
    }

    public float getPeekCameraDistance() {
        return peekCameraDistance;
    }

    public void setPeekCameraDistance(float peekCameraDistance) {
        this.peekCameraDistance = peekCameraDistance;
    }

    public float getRidingCameraDist() {
        return ridingCameraDist;
    }

    public void setRidingCameraDist(float ridingCameraDist) {
        this.ridingCameraDist = ridingCameraDist;
    }

    public float getRidingCameraDistSpeed() {
        return ridingCameraDistSpeed;
    }

    public void setRidingCameraDistSpeed(float ridingCameraDistSpeed) {
        this.ridingCameraDistSpeed = ridingCameraDistSpeed;
    }

    public String getCrosshairType() {
        return crosshairType;
    }

    public void setCrosshairType(String crosshairType) {
        this.crosshairType = crosshairType;
    }

    public boolean isFreeLook() {
        return freeLook;
    }

    public void setFreeLook(boolean freeLook) {
        this.freeLook = freeLook;
    }

    public boolean isMainhandItemFirstPerson() {
        return mainhandItemFirstPerson;
    }

    public void setMainhandItemFirstPerson(boolean mainhandItemFirstPerson) {
        this.mainhandItemFirstPerson = mainhandItemFirstPerson;
    }

    public String getMainhandItemId() {
        return mainhandItemId;
    }

    public void setMainhandItemId(String mainhandItemId) {
        this.mainhandItemId = mainhandItemId;
    }

    public float getAdditionalDistance() {
        return additionalDistance;
    }

    public void setAdditionalDistance(float additionalDistance) {
        this.additionalDistance = additionalDistance;
    }

    public float getXCameraOffset() {
        return xCameraOffset;
    }

    public void setXCameraOffset(float xCameraOffset) {
        this.xCameraOffset = xCameraOffset;
    }

    public float getYCameraOffset() {
        return yCameraOffset;
    }

    public void setYCameraOffset(float yCameraOffset) {
        this.yCameraOffset = yCameraOffset;
    }

    public float getCameraModeMovementSpeed() {
        return cameraModeMovementSpeed;
    }

    public void setCameraModeMovementSpeed(float cameraModeMovementSpeed) {
        this.cameraModeMovementSpeed = cameraModeMovementSpeed;
    }

    public float getCameraModeMaximalDistance() {
        return cameraModeMaximalDistance;
    }

    public void setCameraModeMaximalDistance(float cameraModeMaximalDistance) {
        this.cameraModeMaximalDistance = cameraModeMaximalDistance;
    }

    public boolean isTwopi_cameraModeRotation() {
        return twopi_cameraModeRotation;
    }

    public void setTwopi_cameraModeRotation(boolean twopi_cameraModeRotation) {
        this.twopi_cameraModeRotation = twopi_cameraModeRotation;
    }

    public boolean isShowCameraTooltip() {
        return showCameraTooltip;
    }

    public void setShowCameraTooltip(boolean showCameraTooltip) {
        this.showCameraTooltip = showCameraTooltip;
    }

    public String getCurrentPreset() {
        return currentPreset;
    }

    public void setCurrentPreset(String currentPreset) {
        this.currentPreset = currentPreset;
    }

}
