package org.geyserplus.spigot.sevenwdev.camera;

import org.geyserplus.spigot.sevenwdev.BedrockPlayer;

import java.util.HashMap;

public class CameraCacheManager {
    private static HashMap<BedrockPlayer, CameraCache> useCamera = new HashMap<>();

    public static void setCameraCache(BedrockPlayer bplayer, CameraCache cameraCache) {
        useCamera.put(bplayer, cameraCache);
    }

    public static CameraCache getCameraCache(BedrockPlayer bplayer) {
        return useCamera.get(bplayer);
    }

    public static void removeCameraCache(BedrockPlayer bplayer) {
        useCamera.remove(bplayer);
    }
}