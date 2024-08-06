package org.geyserplus.spigot.sevenwdev.raycast;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class RayCast {

    /**
     * Performs a raycast in the given world, starting at the given location and moving in the specified direction.
     * Stops when it encounters a non-passable block or reaches the maximum distance.
     *
     * @param startLocation The starting location of the raycast.
     * @param direction The direction vector for the raycast.
     * @param maxDistance The maximum distance to check.
     * @return The first non-passable block hit, or null if no block is hit within the maximum distance.
     */
    public static Block raycastBlock(Location startLocation, Vector direction, double maxDistance) {
        World world = startLocation.getWorld();
        Vector start = startLocation.toVector();
        Vector normalizedDirection = direction.normalize();

        for (double i = 0; i <= maxDistance; i += 0.5) {
            Vector currentPosition = start.clone().add(normalizedDirection.clone().multiply(i));
            Block block = world.getBlockAt(currentPosition.getBlockX(), currentPosition.getBlockY(), currentPosition.getBlockZ());

            // Check if the block is non-passable (e.g., not air or similar)
            if (!isPassable(block)) {
                return block;
            }
        }

        return null;
    }

    /**
     * Checks if a block is passable (i.e., the ray can go through it).
     *
     * @param block The block to check.
     * @return True if the block is passable, false otherwise.
     */
    private static boolean isPassable(Block block) {
        Material material = block.getType();
        return material.isTransparent() || material == Material.AIR;
    }
}

