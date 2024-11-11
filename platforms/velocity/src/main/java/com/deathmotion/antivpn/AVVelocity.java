package com.deathmotion.antivpn;

import com.deathmotion.antivpn.data.Constants;
import com.deathmotion.antivpn.listeners.PlayerJoin;
import com.deathmotion.antivpn.schedulers.VelocityScheduler;
import com.deathmotion.antivpn.services.MessengerService;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.bstats.charts.SimplePie;
import org.bstats.velocity.Metrics;

import java.nio.file.Path;

@Getter
public class AVVelocity {
    private final ProxyServer server;
    private final VelocityAntiVPN av;
    private final Metrics.Factory metricsFactory;

    @Inject
    public AVVelocity(ProxyServer server, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
        this.server = server;
        this.av = new VelocityAntiVPN(this, server, dataDirectory);
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent ignoredEvent) {
        av.commonOnInitialize();
        av.scheduler = new VelocityScheduler(this, this.server);
        av.messenger = new MessengerService(this);
        av.commonOnEnable();

        server.getEventManager().register(this, new PlayerJoin(this));
        enableMetrics();
    }

    @Subscribe()
    public void onProxyShutdown(ProxyShutdownEvent ignoredEvent) {
        av.commonOnDisable();
    }

    private void enableMetrics() {
        Metrics metrics = metricsFactory.make(this, Constants.bStatsPluginId);
        metrics.addCustomChart(new SimplePie("antivpn_platform", () -> "Velocity"));
    }
}