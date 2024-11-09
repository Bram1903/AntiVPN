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

package com.deathmotion.antivpn.listeners;

import com.deathmotion.antivpn.AVBungee;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetAddress;
import java.util.UUID;

public class PlayerJoin implements Listener {
    private final AVBungee plugin;

    public PlayerJoin(AVBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPreJoin(PreLoginEvent event) {
        UUID uuid = event.getConnection().getUniqueId();
        InetAddress address = event.getConnection().getVirtualHost().getAddress();

        plugin.getAv().getConnectionService().handlePreLogin(uuid, address);
    }

    @EventHandler
    public void onPreJoin(PostLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        InetAddress address = event.getPlayer().getPendingConnection().getVirtualHost().getAddress();

        if (plugin.getAv().getConnectionService().handleLogin(uuid, address)) {
            event.getPlayer().disconnect(LegacyComponentSerializer.legacySection().serialize(plugin.getAv().getMessages().vpnDetected()));
        }
    }
}
