package org.geyserplus.spigot.sevenwdev;

import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.geyserplus.spigot.sevenwdev.camera.CameraController;
import org.geyserplus.spigot.sevenwdev.player.PlayerCamSettings;
import org.geysermc.geyser.api.bedrock.camera.*;

public class EventListener implements Listener {
    public boolean notBedrock(Player player) {
        return !CameraFree.bplayers.containsKey(player.getUniqueId());
    }

    public BedrockPlayer getPlayer(Player player) {
        return CameraFree.bplayers.getOrDefault(player.getUniqueId(), null);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (notBedrock(player)) {
            return;
        }
        BedrockPlayer bedrockPlayer = getPlayer(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (notBedrock(player)) {
            return;
        }
        BedrockPlayer bedrockPlayer = getPlayer(player);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (notBedrock(player)) {
            return;
        }
        BedrockPlayer bedrockPlayer = getPlayer(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        Player player = ev.getPlayer();
        if (notBedrock(player)) {
            return;
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent ev) {
        Player player = ev.getPlayer();
        if (notBedrock(player)) {
            return;
        }
        BedrockPlayer bedrockPlayer = getPlayer(player);
        bedrockPlayer.onPlayerLeave(ev);
        CameraFree.bplayers.remove(ev.getPlayer().getUniqueId());
    }

    public void runOnNextTick(Runnable function) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(CameraFree.plugin, function, 1L);
    }

    @EventHandler
    public void onExecuteCommand(PlayerCommandPreprocessEvent ev) {
        if ((ev.getMessage().equals("/geyser reload") || ev.getMessage().equals("/stop"))) {
            ev.setCancelled(true);
            runOnNextTick(() -> {
                ev.getPlayer().performCommand(ev.getMessage().replace("/", ""));
            });
        }
        Player player = ev.getPlayer();
        if (notBedrock(player)) {
            return;
        }
        if (ev.getMessage().equalsIgnoreCase("/camer a")) {
            ev.setCancelled(true);
            getPlayer(player).onCameraCommand();
        }
    }
}
