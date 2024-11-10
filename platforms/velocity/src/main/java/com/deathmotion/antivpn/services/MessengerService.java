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

package com.deathmotion.antivpn.services;

import com.deathmotion.antivpn.AVVelocity;
import com.deathmotion.antivpn.interfaces.Messenger;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class MessengerService implements Messenger {
    private final AVVelocity plugin;

    public MessengerService(AVVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void message(UUID uuid, Component message) {
        plugin.getServer().getPlayer(uuid).ifPresent(player -> player.sendMessage(message));
    }

    @Override
    public void broadcast(Component message) {
        plugin.getServer().getAllPlayers().forEach(player -> player.sendMessage(message));
        plugin.getServer().sendMessage(message);
    }

    @Override
    public void broadcast(Component message, String permission) {
        plugin.getServer().getAllPlayers().stream()
                .filter(player -> player.hasPermission(permission))
                .forEach(player -> player.sendMessage(message));

        plugin.getServer().sendMessage(message);
    }

    @Override
    public void broadcastPlayers(Component message) {
        plugin.getServer().getAllPlayers().forEach(player -> player.sendMessage(message));
    }

    @Override
    public void broadcastPlayers(Component message, String permission) {
        plugin.getServer().getAllPlayers().stream()
                .filter(player -> player.hasPermission(permission))
                .forEach(player -> player.sendMessage(message));
    }

    @Override
    public void console(Component message) {
        plugin.getServer().sendMessage(message);
    }
}
