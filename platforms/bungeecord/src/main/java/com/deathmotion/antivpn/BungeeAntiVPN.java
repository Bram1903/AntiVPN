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
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

@Getter
public class BungeeAntiVPN extends AntiVPNPlatform<Plugin> {

    private final AVBungee plugin;

    public BungeeAntiVPN(AVBungee plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin getPlatform() {
        return this.plugin;
    }

    @Override
    public boolean hasPermission(UUID sender, String permission) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(sender);
        if (player == null) return false;

        return player.hasPermission(permission);
    }

    @Override
    public void sendConsoleMessage(Component message) {
        plugin.getAudiences().console().sendMessage(message);
    }

    @Override
    public String getPluginDirectory() {
        return this.plugin.getDataFolder().getAbsolutePath();
    }

    @Override
    public void kickPlayer(UUID uuid, Component reason) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
        if (player != null) {
            player.disconnect(LegacyComponentSerializer.legacySection().serialize(reason));
        }
    }

    @Override
    public void addUpdateNotifier(AVVersion latestVersion) {
        ProxyServer.getInstance().getPluginManager().registerListener(this.plugin, new UpdateNotifier(this.plugin, latestVersion));
    }
}
