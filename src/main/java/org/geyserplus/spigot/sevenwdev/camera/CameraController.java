package org.geyserplus.spigot.sevenwdev.camera;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.C;
import org.geysermc.geyser.api.bedrock.camera.CameraData;
import org.geysermc.geyser.api.bedrock.camera.CameraEaseType;
import org.geysermc.geyser.api.bedrock.camera.CameraPerspective;
import org.geysermc.geyser.api.bedrock.camera.CameraPosition;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geyserplus.spigot.sevenwdev.CameraFree;
import org.geyserplus.spigot.sevenwdev.BedrockPlayer;
import org.geyserplus.spigot.sevenwdev.player.PlayerCamSettings;
import org.geyserplus.spigot.sevenwdev.camera.CameraCacheManager;
import org.geyserplus.spigot.sevenwdev.camera.CameraCache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CameraController {

    private Object player_aim;

    private Object player_rot_view;
    private Object player_look;

    private Object player_in_action;
    private Object player_fpp;
    private Object player_rot;
    private Object player_rot_temp;
    private Object transision_rot;
    private Object transision_lean;
    private Object mount_speed;
    private Object aim_dist;
    private Object lean;

    public boolean notBedrock(Player player) {
        return !CameraFree.bplayers.containsKey(player.getUniqueId());
    }

    public BedrockPlayer getPlayer(Player player) {
        return CameraFree.bplayers.getOrDefault(player.getUniqueId(), null);
    }

    private static BukkitRunnable cameraUpdateTask;

    public static void startCameraUpdateTask() {
        cameraUpdateTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!CameraFree.bplayers.containsKey(player.getUniqueId())) {
                        continue;
                    }
                    if (CameraFree.bplayers.get(player.getUniqueId()).isCameraEnabled()) {
                        updateCameraSettings(CameraFree.bplayers.get(player.getUniqueId()));
                    }
                }
            }
        };
        cameraUpdateTask.runTaskTimer(CameraFree.plugin, 0L, 1L); // Run every tick (20 times per second)
    }

    public static void stopCameraUpdateTask() {
        if (cameraUpdateTask != null) {
            cameraUpdateTask.cancel();
            cameraUpdateTask = null; // Reset the task instance
        }
    }

    public static void updateCameraSettings(BedrockPlayer bplayer) {
        BedrockPlayer bedrockPlayer = bplayer;
        PlayerCamSettings settings = PlayerCamSettings.get(bedrockPlayer.player.getUniqueId());
        CameraData cameraData = bplayer.getCamera();
        
        //TODO make sure cache is deleted and not overloaded.
        // Addon behavior translation from javascript to java.

        float peek_dist = settings.getPeekCameraDistance();
        float aim_dist_option = settings.getAimCameraDistance();
        float max_dist = settings.getCameraModeMaximalDistance();
        float camera_speed = settings.getCameraModeMovementSpeed();

        float additional_x = settings.getXCameraOffset();
        float additional_y = settings.getYCameraOffset();
        float rideSpeed = settings.getRidingCameraDistSpeed();
        float set = (rideSpeed / 10 - 1) * 0.09f;
        float follow_strength = settings.getFollowCameraMovemnetSpeedStrength();
        
        if(CameraCacheManager.getCameraCache(bplayer) != null) {
            if (bplayer.isSneaking()) {
                bplayer.setSneaking(false);
                CameraCacheManager.removeCameraCache(bplayer);
                // Update UI
                // TODO setUI(bplayer, "", "camera_tooltip_update");
                if (bplayer.isCameraEnabled()) {
                    clearCameraInstructions(bplayer);
                    // TODO setUI(bplayer, "textures/gui/crosshair_3", "crosshair_update");
                }
                return;
            }
            if (Math.abs(bplayer.getVelocity().getY()) > 0.01) {
                return;
            }
            CameraCache camera_data = CameraCacheManager.getCameraCache(bplayer);
            Vector view = bplayer.getViewDirection();
            double replace_y = Math.atan2(view.getX(), view.getZ());
            Vector rot = bplayer.getRotation();
            rot.setZ(0);
            Vector buffer_rot = CameraCacheManager.getCameraCache(bplayer).getRotation();
            buffer_rot.setZ(0);
            Vector delta_camera = bplayer.getLocation().toVector()
                    .subtract(CameraCacheManager.getCameraCache(bplayer).getLocation());
            double speed = delta_camera.length();
            double speed_rot = rot.distance(buffer_rot);

            if (speed > 0 || speed_rot > 0.01) {
                Vector delta_camera_rotation = new Vector();
                delta_camera_rotation.setX(rot.getX() - buffer_rot.getX());
                delta_camera_rotation.setY(rot.getY() - buffer_rot.getY());
                delta_camera_rotation.setX(delta_camera_rotation.getX() + 360);
                delta_camera_rotation.setY(delta_camera_rotation.getY() + 360);
                if(delta_camera_rotation.getY() > 180) {
                    delta_camera_rotation.setY(-(360 - delta_camera_rotation.getY()));
                }

                if(delta_camera_rotation.getX() > 180) {
                    delta_camera_rotation.setX(-(360 - delta_camera_rotation.getX()));
                }

                Offset offset = CameraCacheManager.getCameraCache(bplayer).getOffset();

                if(speed > 0) {
                    double motion = -(Math.atan2(delta_camera.getX(), delta_camera.getZ()) - replace_y);
                    double motion_x = Math.cos(motion);
                    double motion_y = Math.sin(motion);

                    double rotX = (offset.getRotation().getX() + 45) * Math.PI/180;
                    double rotY = (offset.getRotation().getY()) * Math.PI/180;
                    speed *= motion_x;
                    
                    Vector aa = new Vector(0, 
                      (Math.cos(rotX)*speed-Math.sin(rotX)*speed),
                      (Math.sin(rotX)*speed+Math.cos(rotX)*speed));
                    
                    Vector calc_loc = new Vector((Math.cos(rotY)*aa.getX()-Math.sin(rotY)*aa.getZ()),
                      aa.getY(),
                      (Math.sin(rotY)*aa.getX()+Math.cos(rotY)*aa.getZ()));
                    
                    calc_loc.setX(calc_loc.getX()*camera_speed);
                    calc_loc.setY((calc_loc.getY() + motion_y*0.1)*camera_speed);
                    
                    Vector estimate_loc = new Vector(calc_loc.getX() + offset.getLocation().getX(),
                      calc_loc.getY() + offset.getLocation().getY(),
                      calc_loc.getZ() + offset.getLocation().getZ());
                    
                    Block block = bplayer.getBlockFromPlayerWorld(estimate_loc);
                    if(!block.getType().isSolid() & estimate_loc.distance((CameraCacheManager.getCameraCache(bplayer).getLocation())) < max_dist) {
                        CameraCacheManager.getCameraCache(bplayer).getOffset().getLocation().setX(CameraCacheManager.getCameraCache(bplayer).getOffset().getLocation().getX() + calc_loc.getX());
                        CameraCacheManager.getCameraCache(bplayer).getOffset().getLocation().setY(CameraCacheManager.getCameraCache(bplayer).getOffset().getLocation().getY() + calc_loc.getY());
                        CameraCacheManager.getCameraCache(bplayer).getOffset().getLocation().setZ(CameraCacheManager.getCameraCache(bplayer).getOffset().getLocation().getZ() + calc_loc.getZ());
                    }
                }

                if(speed_rot > 0.01) {
                    double estimate_rot = (offset.getRotation().getX() + delta_camera_rotation.getX()*camera_speed*0.3);
                    if(settings.isTwopi_cameraModeRotation() == false){
                        if(Math.sin((estimate_rot - 90) / 180 * Math.PI) < 0) {
                            CameraCacheManager.getCameraCache(bplayer).getOffset().getRotation().setX(estimate_rot);
                        }
                    } else {
                            CameraCacheManager.getCameraCache(bplayer).getOffset().getRotation().setX(estimate_rot);
                    }

                    CameraCacheManager.getCameraCache(bplayer).getOffset().getRotation().setY(offset.getRotation().getY() + delta_camera_rotation.getY()*camera_speed*0.3);
                }
                Vector L =  camera_data.getLocation(); //Location
                Vector R= camera_data.getRotation();//Rotation
                Location plocation = new Location(bplayer.getWorld(),L.getX(),L.getY(),L.getZ(),(float)R.getX(),(float)R.getY());
                bplayer.player.teleport(plocation);
            }
            return;
        }

        if(bplayer.isCameraEnabled()) {
            return;
        }
        String set_crosshair = "textures/gui/crosshair_0";
    }

    private static void setUI(BedrockPlayer bplayer, String string, String string2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUI'");
    }

    public static void clearCameraInstructions(BedrockPlayer bplayer) {
        if (bplayer != null) {
            bplayer.getCamera().clearCameraInstructions();
            // TODO setUI(bplayer, "textures/gui/crosshair_3", "crosshair_update");
        }
    }
}