package org.geyserplus.spigot.sevenwdev.camera;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.t;
import org.cloudburstmc.math.vector.*;
import org.geysermc.geyser.api.bedrock.camera.CameraData;
import org.geysermc.geyser.api.bedrock.camera.CameraEaseType;
import org.geysermc.geyser.api.bedrock.camera.CameraPerspective;
import org.geysermc.geyser.api.bedrock.camera.CameraPosition;
import org.geysermc.geyser.api.connection.GeyserConnection;
import org.geyserplus.spigot.sevenwdev.CameraFree;
import org.geyserplus.spigot.sevenwdev.BedrockPlayer;
import org.geyserplus.spigot.sevenwdev.player.PlayerCamSettings;
import java.lang.Math;

import net.md_5.bungee.api.chat.hover.content.Item;

import org.geyserplus.spigot.sevenwdev.camera.CameraCacheManager;
import org.geyserplus.spigot.sevenwdev.camera.CameraCache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

public class CameraController  {

    private static CameraController ck;
    private CameraController()
    {

    } 

    private Map<BedrockPlayer, Integer> player_aim = new HashMap<>();

    private Object player_rot_view;
    private Map<BedrockPlayer, Integer> player_look = new HashMap<>();

    private Map<BedrockPlayer, Double> player_in_action = new HashMap<>();
    private Map<BedrockPlayer,Integer> player_fpp = new HashMap<>(); 
    private Map<BedrockPlayer, Vector> player_rot = new HashMap<>();
    private Map<BedrockPlayer, Double> player_rot_temp;
    private Map<BedrockPlayer, Double> transision_rot = new HashMap<>();
    private Map<BedrockPlayer, Integer> transision_lean = new HashMap<>();
    private Map<BedrockPlayer, Double> mount_speed = new HashMap<>();
    private Map<BedrockPlayer, Double> aim_dist = new HashMap<>();
    private Map<BedrockPlayer, Integer> lean = new HashMap<>();
    public static CameraController getInstance() {
        if (ck == null) {
            ck = new CameraController();
        }
        return ck;
    }
    /**
     * 
     * @param player a player interface
     * @return a {@link Boolean} with the value true if the player is not a bedrock player otherwise false
     */
    public boolean notBedrock(Player player) {
        return !CameraFree.bplayers.containsKey(player.getUniqueId());
    }
    /**
     * <summary>
     * gets a bedrock player by their {@link UUID} using the {@link player#getUniqueId()}
     * </summary>
     * @param player an interface representing a template of a player
     * @return {@link BedrockPlayer} instance if the player does not exist a default 
     * {@link BedrockPlayer} profile will be returned
     */
    public  BedrockPlayer getPlayer(Player player) {
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
                        ck.updateCameraSettings(CameraFree.bplayers.get(player.getUniqueId()));
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

    public void updateCameraSettings(BedrockPlayer bplayer) {
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
            Vector buffer_rot = camera_data.getRotation();
            buffer_rot.setZ(0);
            Vector delta_camera = bplayer.getLocation().toVector()
                    .subtract(camera_data.getLocation());
            double speed = delta_camera.length();
            double speed_rot = rot.distance(buffer_rot);

            if (speed > 0 || speed_rot > 0.01) {
                Vector delta_camera_rotation = new Vector();
                delta_camera_rotation.setX(rot.getX() - buffer_rot.getX());
                delta_camera_rotation.setY(rot.getY() - buffer_rot.getY());
                delta_camera_rotation.setX((delta_camera_rotation.getX() + 360) % 360);
                delta_camera_rotation.setY((delta_camera_rotation.getY() + 360) % 360);
                if(delta_camera_rotation.getY() > 180) {
                    delta_camera_rotation.setY(-(360 - delta_camera_rotation.getY()));
                }

                if(delta_camera_rotation.getX() > 180) {
                    delta_camera_rotation.setX(-(360 - delta_camera_rotation.getX()));
                }

                Offset offset = camera_data.getOffset();

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
                    calc_loc.setZ(calc_loc.getZ()*camera_speed);
                    
                    Vector estimate_loc = new Vector(calc_loc.getX() + offset.getLocation().getX(),
                      calc_loc.getY() + offset.getLocation().getY(),
                      calc_loc.getZ() + offset.getLocation().getZ());
                    
                    Block block = bplayer.getBlockFromPlayerWorld(estimate_loc);
                    if(!block.getType().isSolid() & estimate_loc.distance((camera_data.getLocation())) < max_dist) {
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
                CameraPosition.Builder camP = CameraPosition.builder();
                camP.easeType(CameraEaseType.LINEAR);
                camP.easeSeconds(0.1f);
                Vector3f offVector = Vector3f.from(offset.getLocation().getX(), offset.getLocation().getY(), offset.getLocation().getZ());
                camP.position(offVector);
                camP.rotationX((int)offset.getRotation().getX());
                camP.rotationY((int)offset.getRotation().getY());
                CameraPosition builtCam= camP.build();
                cameraData.sendCameraPosition(builtCam);


            }
            return;
        }

        if(bplayer.isCameraEnabled()) {
            return;
        }
        String set_crosshair = "textures/gui/crosshair_0";
        ItemStack item = bplayer.getItemInMainHand();
        if(settings.isMainhandItemFirstPerson()){
            if(item!=null && settings.getMainhandItemId().contains(item.getType().name())) {
                cameraData.forceCameraPerspective(CameraPerspective.FIRST_PERSON);
                if(settings.getCrosshairType()=="Dynamic"){
                    set_crosshair = "textures/gui/crosshair_3";
                    player_fpp.put(bplayer, 2);
                }
            }
        }
        if(item!=null && item.getType().name() == "CROSSBOW"){
            player_aim.put(bplayer, 2);
        }
        if(item==null || (player_aim.get(bplayer) == 2 && item.getType().name() != "CROSSBOW")){
            player_aim.put(bplayer, null);

        }

        player_in_action.put(bplayer, Math.max(player_in_action.get(bplayer) - 0.025, 0));
        Location loc = bplayer.getHeadLocation();
        Vector view = bplayer.getViewDirection();
        Vector rot = bplayer.getRotation();
        Vector vel = bplayer.getVelocity();
        double speed = Math.sqrt(Math.pow(vel.getX(), 2) + Math.pow(vel.getY(), 2) + Math.pow(vel.getZ(), 2));
        double speed2d = Math.sqrt(Math.pow(vel.getX(), 2) + Math.pow(vel.getZ(), 2));
        Location pos = bplayer.getLocation();

        if(player_rot.get(bplayer) == null) {
            player_rot.put(bplayer, rot);
            transision_rot.put(bplayer, 0d);
            player_in_action.put(bplayer, 0d);
            player_look.put(bplayer, 32);
            player_rot_temp.put(bplayer, 0d);
            transision_lean.put(bplayer, 0);
            mount_speed.put(bplayer, 0d);
            aim_dist.put(bplayer, 0d);
            player_fpp.put(bplayer, 0);
            lean.put(bplayer, 1);
        }
        player_fpp.put(bplayer, Math.max(player_fpp.get(bplayer) - 1, 0));

        if(speed2d > 0.025 && settings.isFreeLook()){
            player_rot_temp.put(bplayer, (Math.atan2(vel.getX(), -vel.getZ())*180/3.14-180));
        }

        boolean is_on_water = bplayer.isInWater();
        boolean is_riding = bplayer.isRiding();
        if(is_riding){
            Entity getRide = bplayer.getRidingEntity();
            Vehicle getRidingData = (Vehicle) getRide;
            int seat = 0;
            for(var i : getRidingData.getPassengers()){
                if(i.getEntityId() == bplayer.player.getEntityId()){
                    return;
                }
                seat++;  
            }
            
            int lockRiderRotation = 90; //TODO edit according to mojang's values
            rot.setY(getRide.getLocation().getDirection().getY() + lockRiderRotation + 180);
            double rotX = (rot.getX()+45) * Math.PI/180;
            double rotY = (rot.getY()) * Math.PI/180;

            view.setX(0);
            view.setY(Math.cos(rotX)-Math.sin(rotX));
            view.setZ(Math.sin(rotX)+Math.cos(rotX));

            view.setX(Math.cos(rotY)*view.getX()-Math.sin(rotY)*view.getZ());
            view.setY(view.getY());
            view.setZ(Math.sin(rotY)*view.getX()+Math.cos(rotY)*view.getZ());

            vel = getRide.getVelocity();
        }

        if(settings.isDynamicFirstPerson()){
            double air_size = 0;
            Vector[] perspective_scan = {
                    new Vector(1, 1, 0),
                    new Vector(-1, 1, 0),
                    new Vector(0, 1, 1),
                    new Vector(0, 1, -1),
                    new Vector(1, 1, 1),
                    new Vector(1, 1, -1),
                    new Vector(-1, 1, -1),
                    new Vector(-1, 1, 1)
            };
            for(Vector pov : perspective_scan){
                if (bplayer.getBlockFromRay(pov, 4) != null) {
                    air_size++;
                }
                if(air_size == 4) return;
            }

            if(air_size > 3) {
                cameraData.forceCameraPerspective(CameraPerspective.FIRST_PERSON);
                if(settings.getCrosshairType()=="Dynamic"){
                    set_crosshair = "textures/gui/crosshair_3";
                }
                BukkitRunnable runner = new BukkitRunnable() {
                    @Override
                    public void run() {
                        bplayer.player.addScoreboardTag("cancel_third_person");
                    }
                };

                int runId = runner.runTaskTimer(CameraFree.plugin, 0L, 20L).getTaskId();
                
                BukkitTask runClear = new BukkitRunnable() {
                    @Override
                    public void run() {
                        bplayer.player.removeScoreboardTag("cancel_third_person");
                        Bukkit.getScheduler().cancelTask(runId);
                        this.cancel();
                    }
                }.runTaskLater(CameraFree.plugin, 10L);
                player_fpp.put(bplayer, 10);
            }

            if(bplayer.player.getScoreboardTags().contains("cancel_third_person")){
                clearCameraInstructions(bplayer);
                if(settings.getCrosshairType()=="Dynamic"){
                    set_crosshair = "textures/gui/crosshair_3";
                }
                player_fpp.put(bplayer, 10); 
            }
        }

        double rot_temp = Math.floor(Math.atan2(vel.getZ(), vel.getX()) * 180 / Math.PI - 90);
        if(rot_temp < -180){
            rot_temp += 360;
        }

        double delta_rot = Math.abs(rot_temp - Math.floor((rot.getY())));
        delta_rot = Math.min(delta_rot, (360 - delta_rot));

        double dir_transision = 1;

        double perspective = Math.min(Math.max((player_rot.get(bplayer)).getY() - rot.getY(), -10), 10);
        if(transision_rot.get(bplayer) > 0){
            if(perspective < transision_rot.get(bplayer)){
                dir_transision = 0.3;
            }
        } else if(transision_rot.get(bplayer) < 0){
            if(perspective > transision_rot.get(bplayer)){
                dir_transision = 0.3;
            }
        }

        if(settings.isAdvanceCameraRotation()){
            dir_transision = dir_transision*(Math.min(speed*3, 1)*0.9+0.1);
            transision_rot.put(bplayer, Math.min(Math.max((transision_rot.get(bplayer) + perspective * dir_transision * 0.5)*(0.9975 + Math.min(speed*3, 1)*0.0025), -15), 15));
        }
        player_rot.put(bplayer, rot);
        
        Map<String, Double> camera_option = new HashMap<>();
        camera_option.put("x", 0d);
        camera_option.put("y", 2d);
        camera_option.put("dist", 1d);
        camera_option.put("y_min", 0.5);
        camera_option.put("rot_x", 0d);
        camera_option.put("rot_y", 0d);
        camera_option.put("ease", 0.1);

        double smooth_y = 0.5;

        if(is_riding && settings.getRidingCameraDistSpeed() != 0){
            camera_option.put("ease", 0.2); 
            mount_speed.put(bplayer, Math.min((mount_speed.get(bplayer) + speed*(0.46+set*4))*(0.9+set), settings.getRidingCameraDist()));
            camera_option.put("dist", 1+mount_speed.get(bplayer));   
        }

        if(settings.isAdvanceCameraRotation()){
            double lastX = camera_option.get("x");
            double lastRotY = camera_option.get("y");
            camera_option.put("x", lastX + Math.max(Math.min(transision_rot.get(bplayer),10),-10)*0.75);
            camera_option.put("rot_y",lastRotY + Math.max(Math.min(transision_rot.get(bplayer),10),-10)*0.75);
        }

        double lastX = camera_option.get("x");
        double lastY = camera_option.get("y");
        camera_option.put("x", lastX + additional_x);
        camera_option.put("y", lastX + additional_y);

        camera_option.put("dist", Math.sqrt(Math.pow(additional_x+0.01, 2) + Math.pow(additional_y+0.01, 2))*0.1);
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