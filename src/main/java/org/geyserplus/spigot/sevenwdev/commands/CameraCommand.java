package org.geyserplus.spigot.sevenwdev.commands;

import org.geyserplus.spigot.sevenwdev.BedrockPlayer;
import org.geyserplus.spigot.sevenwdev.CameraFree;
import org.geyserplus.spigot.sevenwdev.camera.CameraController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CameraCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            BedrockPlayer bplayer = CameraFree.bplayers.get(player.getUniqueId());
            if (CameraFree.bedrockAPI.isBedrockPlayer(player.getUniqueId())) {
                if (args.length > 0) {
                    switch (args[0].toLowerCase()) {
                        case "settings":
                            CameraFree.bplayers.get(player.getUniqueId()).onCameraCommand();
                            break;
                        case "toggle":
                            Boolean cameraEnabled = bplayer.isCameraEnabled();
                            if (cameraEnabled) {
                                CameraController.clearCameraInstructions(bplayer);
                                bplayer.setCameraEnabled(false);
                                player.sendMessage("Camera " + (!cameraEnabled ? "enabled" : "disabled"));
                                break;
                            } else {
                                bplayer.setCameraEnabled(true);
                                player.sendMessage("Camera " + (!cameraEnabled ? "enabled" : "disabled"));
                                break;
                            }
                        default:
                            player.sendMessage("Usage: /camera <settings|toggle>");
                            break;
                    }
                } else {
                    player.sendMessage("Usage: /camera <settings|toggle>");
                }
            }
        }
        return true;
    }
}
