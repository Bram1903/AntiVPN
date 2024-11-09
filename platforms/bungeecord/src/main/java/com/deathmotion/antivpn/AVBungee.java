package com.deathmotion.antivpn;

import com.deathmotion.antivpn.listeners.PlayerJoin;
import com.deathmotion.antivpn.schedulers.BungeeScheduler;
import lombok.Getter;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public final class AVBungee extends Plugin {
    private final BungeeAntiVPN av = new BungeeAntiVPN(this);
    private BungeeAudiences audiences;

    @Override
    public void onEnable() {
        audiences = BungeeAudiences.create(this);
        av.commonOnInitialize();

        av.scheduler = new BungeeScheduler(this);
        av.commonOnEnable();

        getProxy().getPluginManager().registerListener(this, new PlayerJoin(this));
    }

    @Override
    public void onDisable() {
        av.commonOnDisable();
        audiences.close();
    }
}