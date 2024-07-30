package org.geyserplus.spigot.sevenwdev.camera;

import org.bukkit.util.Vector;

public class CameraCache {
    private Vector location;
    private Vector rotation;
    private Offset offset;

    public CameraCache(Vector location, Vector rotation, Offset offset) {
        this.location = location;
        this.rotation = rotation;
        this.offset = offset;
    }

    // Getters and setters
    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public Vector getRotation() {
        return rotation;
    }

    public void setRotation(Vector rotation) {
        this.rotation = rotation;
    }

    public Offset getOffset() {
        return offset;
    }

    public void setOffset(Offset offset) {
        this.offset = offset;
    }
}

class Offset {
    private Vector location;
    private Vector rotation;

    public Offset(Vector location, Vector rotation) {
        this.location = location;
        this.rotation = rotation;
    }

    // Getters and setters
    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public Vector getRotation() {
        return rotation;
    }

    public void setRotation(Vector rotation) {
        this.rotation = rotation;
    }
}