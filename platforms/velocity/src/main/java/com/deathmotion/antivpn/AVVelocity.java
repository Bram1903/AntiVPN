package com.deathmotion.antivpn;

import com.deathmotion.antivpn.schedulers.VelocityScheduler;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public class AVVelocity {
    private final ProxyServer server;
    private final VelocityAntiVPN av;

    @Inject
    public AVVelocity(ProxyServer server, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.av = new VelocityAntiVPN(server, dataDirectory);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent ignoredEvent) {
        av.commonOnInitialize();
        av.scheduler = new VelocityScheduler(this, this.server);
        av.commonOnEnable();
    }

    @Subscribe()
    public void onProxyShutdown(ProxyShutdownEvent ignoredEvent) {
        av.commonOnDisable();
    }
}