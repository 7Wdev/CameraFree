package org.geyserplus.spigot.sevenwdev;

import org.geyserplus.spigot.sevenwdev.api.APIType;
import org.geyserplus.spigot.sevenwdev.menu.CameraMenu;
import org.geyserplus.spigot.sevenwdev.player.PlayerDevice;
import org.geyserplus.spigot.sevenwdev.player.PlayerInputType;
import org.geyserplus.spigot.sevenwdev.player.PlayerPlatform;
import org.geyserplus.spigot.sevenwdev.player.PlayerUIProfile;
import org.geyserplus.spigot.sevenwdev.raycast.RayCast;

import com.velocitypowered.api.proxy.player.TabList;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.geysermc.geyser.api.bedrock.camera.CameraData;

import java.io.ObjectInputFilter.Config;
import java.util.*;

public class BedrockPlayer {
    public Player player;
    public static Random random = new Random();
    public boolean lookingAtEntity = false;
    
    public PlayerDevice device;
    public PlayerInputType inputType;
    public PlayerPlatform platform;
    public PlayerUIProfile uiProfile;
    public String bedrockUsername;
    private boolean cameraEnabled = false;

    public String xuid;
    public CameraData cameradata;

    BedrockPlayer(Player player) {
        this.player = player;
        this.bedrockUsername = CameraFree.bedrockAPI.getXboxUsername(this);
        this.device = PlayerDevice.getPlayerDevice(this);
        this.inputType = PlayerInputType.getPlayerInputType(this);
        this.platform = PlayerPlatform.getPlayerPlatform(this);
        this.uiProfile = PlayerUIProfile.getPlayerUIProfile(this);
        this.xuid = CameraFree.bedrockAPI.getPlayerXUID(this);
        this.cameradata = CameraFree.bedrockAPI.getCamera(this);
        this.save();
        
        if (CameraFree.bedrockAPI.supports(APIType.GEYSER)) {
            this.save();
        }
    }

    public PersistentDataContainer playerSaveData() {
        return player.getPersistentDataContainer();
    }

    public boolean hasData(String key) {
        try {
            return playerSaveData().has(NamespacedKey.fromString(key, CameraFree.plugin));
        } catch (Exception ignored) {
            return false;
        }
    }

    public <P, C> void setData(String key, PersistentDataType<P, C> type, C value) {
        playerSaveData().set(NamespacedKey.fromString(key, CameraFree.plugin), type, value);
    }

    public <P, C> C getData(String key, PersistentDataType<P, C> type) {
        return playerSaveData().get(NamespacedKey.fromString(key, CameraFree.plugin), type);
    }

    public void save() { 
    }
    
    public void update() {
        if (!Objects.requireNonNull(player.getAddress()).getHostString().equals("127.0.0.1")) {
            calculateAveragePing();
        }
    }

    Entity lookingEntity;

    public void checkLookingAtEntity() {
        lookingEntity = getTargetEntity(player);
        lookingAtEntity = lookingEntity != null;
    }

    public Entity getTargetEntity(Player player) {
        Location loc = player.getEyeLocation();
        RayTraceResult entityCast = player.getWorld().rayTraceEntities(loc, loc.getDirection(),
                Objects.requireNonNull(player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE)).getBaseValue(),
                entity -> {
                    if (entity instanceof Player player1) {
                        return player1.getUniqueId() != player.getUniqueId();
                    } else if (entity.isDead()) {
                        return false;
                    } else {
                        return entity.getType().isAlive();
                    }
                });
        if (entityCast == null) {
            return null;
        }

        RayTraceResult blockCast = player.getWorld().rayTraceBlocks(loc, loc.getDirection(),
                Objects.requireNonNull(player.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE)).getBaseValue());

        if (blockCast == null) {
            return entityCast.getHitEntity();
        }
        if (entityCast.getHitEntity() == null) {
            return null;
        }
        if (blockCast.getHitBlock() == null) {
            return entityCast.getHitEntity();
        }
        if (entityCast.getHitPosition().toLocation(player.getWorld()).distance(loc) >= blockCast.getHitPosition()
                .toLocation(player.getWorld()).distance(loc)
                && blockCast.getHitBlock().getType() != Material.AIR) {
            return null;
        }
        return entityCast.getHitEntity();
    }

    public boolean isTool() {
        ItemStack item = player.getInventory().getItemInMainHand();
        return item.getType().isItem() && (
                item.getTranslationKey().contains("_axe")
                        || item.getTranslationKey().contains("_pickaxe")
                        || item.getTranslationKey().contains("_shovel")
                        || item.getTranslationKey().contains("_sword")
                        || item.getTranslationKey().contains("mace")
                // || item.getTranslationKey().contains("_hoe")
                // hoes dont have attack speed for some reason
        );
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public void setSneaking(boolean sneaking) {
        player.setSneaking(sneaking);
    }

    public Vector getVelocity() {
        return player.getVelocity();
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public Location getHeadLocation() {
        return player.getEyeLocation();
    }

    public Vector getViewDirection() {
        return player.getEyeLocation().getDirection();
    }

    public void setLocation(Location location) {
        player.teleport(location);
    }

    public Vector getRotation() {
        Location loc = player.getLocation();
        return loc.getDirection();
    }

    public void setRotation(Vector rotation) {
        Location loc = player.getLocation();
        loc.setDirection(rotation);
        //NOTE Minecraft does not use roll (z-axis) for player rotation, so it is ignored
        player.teleport(loc);
    }

    public float getPitch() {
        return player.getLocation().getPitch();
    }

    public void setPitch(float pitch) {
        Location loc = player.getLocation();
        loc.setPitch(pitch);
        player.teleport(loc);
    }

    public float getYaw() {
        return player.getLocation().getYaw();
    }

    public void setYaw(float yaw) {
        Location loc = player.getLocation();
        loc.setYaw(yaw);
        player.teleport(loc);
    }

    public boolean isArmor() {
        ItemStack item = player.getInventory().getItemInMainHand();
        return item.getType().isItem() && (
                item.getTranslationKey().contains("_helmet")
                        || item.getTranslationKey().contains("_chestplate")
                        || item.getTranslationKey().contains("_leggings")
                        || item.getTranslationKey().contains("_boots")
        );
    }
    
    public Block getBlockFromPlayerWorld(Vector pos) {
        org.bukkit.World world = player.getWorld();
        Location location = new Location(world, pos.getX(), pos.getY(), pos.getZ());
        Block block = world.getBlockAt(location);

        return block;
    }

    public void onCameraCommand() {
        new CameraMenu(this);
    }

    private int lastPing = 0;

    private final List<Integer> pingValues = new ArrayList<>();

    public float averagePing = 0.0f;

    private void calculateAveragePing() {
        int ping = player.getPing();
        if (lastPing != ping) {
            pingValues.add(ping);
            if (pingValues.size() > 10) {
                pingValues.remove(0);
            }
            int total = 0;
            for (int pingVal : pingValues) {
                total += pingVal;
            }
            averagePing = (float) total / pingValues.size();
            lastPing = ping;
        }
    }
    
    private void runCommand(String string) {
        player.performCommand(string);
    }

    public void onPlayerMoveEvent(PlayerMoveEvent ev) {

    }

    public void onPlayerLeave(PlayerQuitEvent ev) {
    }

    public CameraData getCamera() {
        return this.cameradata; 
    }

    public boolean isCameraEnabled() {
        return cameraEnabled;
    }

    public void setCameraEnabled(boolean cameraEnabled) {
        this.cameraEnabled = cameraEnabled;
    }
    public World getWorld()
    {

        return player.getWorld();
    }

    public ItemStack getItemInMainHand()
    {
        return this.player.getInventory().getItemInMainHand();
    }

    public boolean isInWater()
    {
        return this.player.isInWater();
    }

    public boolean isRiding()
    {
        return this.player.isInsideVehicle();
    }

    public Entity getRidingEntity()
    {
        return this.player.getVehicle();
    }
    
   public Block getBlockFromRay(Vector pov, double maxDistance) {
    World w = this.player.getWorld();
    Location eyeLocation = this.player.getEyeLocation();
    Block hitBlock = RayCast.raycastBlock(eyeLocation, pov, maxDistance);
    return hitBlock;
   }
      
    
}

