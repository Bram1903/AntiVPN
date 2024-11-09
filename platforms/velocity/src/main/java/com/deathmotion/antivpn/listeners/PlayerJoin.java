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

import com.deathmotion.antivpn.AVVelocity;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;

public class PlayerJoin {
    private final AVVelocity plugin;

    public PlayerJoin(AVVelocity plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onPlayerConnect(PreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        Optional<InetAddress> addressOptional = event.getConnection().getVirtualHost().map(InetSocketAddress::getAddress);

        addressOptional.ifPresent(address ->
                plugin.getAv().getConnectionService().handlePreLogin(uuid, address)
        );
    }

    @Subscribe
    public void onPlayerConnect(PostLoginEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        InetAddress address = event.getPlayer().getRemoteAddress().getAddress();

        if (plugin.getAv().getConnectionService().handleLogin(uuid, address)) {
            event.getPlayer().disconnect(plugin.getAv().getMessages().vpnDetected());
        }
    }
}
