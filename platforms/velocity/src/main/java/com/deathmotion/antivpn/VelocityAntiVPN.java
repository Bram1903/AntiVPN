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

import com.deathmotion.antivpn.listeners.UpdateNotifier;
import com.deathmotion.antivpn.util.AVVersion;
import com.google.inject.Inject;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.nio.file.Path;
import java.util.UUID;

public class VelocityAntiVPN extends AntiVPNPlatform<ProxyServer> {

    private final AVVelocity plugin;
    private final ProxyServer server;
    private final Path dataDirectory;

    @Inject
    public VelocityAntiVPN(AVVelocity plugin, ProxyServer server, @DataDirectory Path dataDirectory) {
        this.plugin = plugin;
        this.server = server;
        this.dataDirectory = dataDirectory;
    }

    @Override
    public ProxyServer getPlatform() {
        return this.server;
    }

    @Override
    public boolean hasPermission(UUID sender, String permission) {
        Player player = this.server.getPlayer(sender).orElse(null);
        if (player == null) return false;

        return player.hasPermission(permission);
    }

    @Override
    public String getPluginDirectory() {
        return this.dataDirectory.toAbsolutePath().toString();
    }

    @Override
    public void kickPlayer(UUID uuid, Component reason) {
        server.getPlayer(uuid).ifPresent(player -> player.disconnect(reason));
    }

    @Override
    public void addUpdateNotifier(AVVersion latestVersion) {
        server.getEventManager().register(this, new UpdateNotifier(this.plugin, latestVersion));
    }
}
