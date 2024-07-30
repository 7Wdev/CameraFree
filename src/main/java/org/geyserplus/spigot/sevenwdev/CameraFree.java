package org.geyserplus.spigot.sevenwdev;

import org.geyserplus.InitializeLogger;
import org.geyserplus.spigot.sevenwdev.api.APIType;
import org.geyserplus.spigot.sevenwdev.camera.CameraController;
import org.geyserplus.spigot.sevenwdev.commands.CameraCommand;
import org.geyserplus.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public final class CameraFree extends JavaPlugin {
    public static CameraFree plugin;
    public static Logger logger;
    public static InitializeLogger initLog;
    public static BedrockAPI bedrockAPI;

    public CameraFree() {
        ServerType.type = ServerType.LEAVESMC;
    }

    @Override
    public void onEnable() {
        plugin = this;
        logger = this.getLogger();
        initLog = new InitializeLogger((s) -> logger.warning(s), (s) -> logger.info(s));
        initLog.start();
        bedrockAPI = new BedrockAPI();
        if (bedrockAPI.foundGeyserClasses) {
            StringBuilder types = new StringBuilder();
            for (APIType type : bedrockAPI.apiInstances.keySet()) {
                types.append(type.toString() + ", ");
            }
            initLog.info("API Types: " + types.substring(0, types.length() - 2));
        } else {
            initLog.warn(
                    "CameraFree could not initialize! This means that Floodgate or Geyser was not in your plugins folder.");
            initLog.endNoDone();
            this.setEnabled(false);
            return;
        }
        Objects.requireNonNull(this.getCommand("camera")).setExecutor(new CameraCommand());
        initLog.logTask("Registering events...",
                () -> getServer().getPluginManager().registerEvents(new EventListener(), this),
                "Events registered!"); 
        initLog.info("[Author]: Ade Issawe");
        initLog.info("Enabling CameraFree...");
        initLog.logTask("registering task...", () -> CameraController.startCameraUpdateTask(), "started task!");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::update, 0L, 0L);
        initLog.end();
    }

    public static ConcurrentHashMap<UUID, BedrockPlayer> bplayers = new ConcurrentHashMap<>();
    
    public static InitializeLogger getPluginLogger() {
        return initLog;
    }

    public void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (bedrockAPI.isBedrockPlayer(player.getUniqueId()) && !bplayers.containsKey(player.getUniqueId())) {
                bplayers.putIfAbsent(player.getUniqueId(), new BedrockPlayer(player));
            }
        }
        bplayers.keySet().forEach((uuid) -> {
            if (!Bukkit.getOfflinePlayer(uuid).isOnline()) {
                bplayers.remove(uuid);
            }
        });
        for (BedrockPlayer bplayer : bplayers.values()) {
            bplayer.update();
        }
    }

    @Override
    public void onDisable() {
        logger = this.getLogger();
        initLog = new InitializeLogger((s) -> logger.warning(s), (s) -> logger.info(s));
        initLog.start();
        initLog.logTask("aborting task...", () -> CameraController.stopCameraUpdateTask(), "aborted task!");
        initLog.warn("Shutting down CameraFree...");
        initLog.end(); 
    }
}