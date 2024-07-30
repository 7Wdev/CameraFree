package org.geyserplus;

public enum ServerType {
    BUNGEE,
    /**
     * @deprecated Unsupported
     */
    @Deprecated
    EXTENSION,
    LEAVESMC,
    PAPER,
    PUFFER,
    SPIGOT,
    VELOCITY;

    public static ServerType type;

    public static ServerType get() {
        return type;
    }
}