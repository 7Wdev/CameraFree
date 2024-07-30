package org.geyserplus.extension.sevenwdev;

import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geyserplus.InitializeLogger;
import org.geyserplus.ServerType;


public class CameraFree implements Extension {
    InitializeLogger initLog;

    @SuppressWarnings("deprecation")
    public CameraFree() {
        ServerType.type = ServerType.EXTENSION;
    }

    @Subscribe
    public void onPreInitialize(GeyserPreInitializeEvent event) {
        initLog = new InitializeLogger((s) -> logger().warning(s), (s) -> logger().info(s));
        initLog.start();
        initLog.warn("CameraFree is currently not supported as a Geyser Extension!");
        initLog.warn("If you are running Geyser on your server/proxy, place this plugin");
        initLog.warn("in your softwares's plugins folder and not the Geyser Extensions folder!");
        initLog.warn("Disabling...");
        initLog.endNoDone();
        this.setEnabled(false);
    }
}