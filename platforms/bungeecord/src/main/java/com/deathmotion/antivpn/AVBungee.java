package com.deathmotion.antivpn;

import com.deathmotion.antivpn.data.Constants;
import com.deathmotion.antivpn.listeners.PlayerJoin;
import com.deathmotion.antivpn.schedulers.BungeeScheduler;
import com.deathmotion.antivpn.services.MessengerService;
import lombok.Getter;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;
import org.bstats.charts.SimplePie;

@Getter
public final class AVBungee extends Plugin {
    private final BungeeAntiVPN av = new BungeeAntiVPN(this);
    private BungeeAudiences adventure;

    @Override
    public void onEnable() {
        adventure = BungeeAudiences.create(this);
        av.commonOnInitialize();
        av.scheduler = new BungeeScheduler(this);
        av.messenger = new MessengerService(this);
        av.commonOnEnable();

        getProxy().getPluginManager().registerListener(this, new PlayerJoin(this));
    }

    @Override
    public void onDisable() {
        av.commonOnDisable();
        adventure.close();
    }

    private void enableMetrics() {
        Metrics metrics = new Metrics(this, Constants.bStatsPluginId);
        metrics.addCustomChart(new SimplePie("antivpn_platform", () -> "BungeeCord"));
    }
}