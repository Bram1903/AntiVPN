/*
 * This file is part of AntiVPN - https://github.com/Bram1903/AntiVPN
 * Copyright (C) 2024 Bram and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.deathmotion.antivpn;

import com.deathmotion.antivpn.data.Constants;
import com.deathmotion.antivpn.listeners.PlayerJoin;
import com.deathmotion.antivpn.schedulers.BukkitScheduler;
import com.deathmotion.antivpn.services.MessengerService;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AVBukkit extends JavaPlugin {

    private final BukkitAntiVPN av = new BukkitAntiVPN(this);
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);

        // Initialize the platform
        av.commonOnInitialize();
        av.scheduler = new BukkitScheduler(this);
        av.messenger = new MessengerService(this);
        av.commonOnEnable();

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        enableMetrics();
    }

    @Override
    public void onDisable() {
        av.commonOnDisable();
        adventure.close();
    }

    private void enableMetrics() {
        Metrics metrics = new Metrics(this, Constants.bStatsPluginId);
        metrics.addCustomChart(new SimplePie("antivpn_platform", () -> "Bukkit"));
    }
}
