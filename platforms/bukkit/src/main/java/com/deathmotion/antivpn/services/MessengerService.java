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

import com.deathmotion.antivpn.AVBukkit;
import com.deathmotion.antivpn.interfaces.Messenger;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;

import java.util.UUID;

public class MessengerService implements Messenger {
    private final BukkitAudiences adventure;

    public MessengerService(AVBukkit plugin) {
        this.adventure = plugin.getAdventure();
    }

    @Override
    public void message(UUID uuid, Component message) {
        adventure.player(uuid).sendMessage(message);
    }

    @Override
    public void broadcast(Component message) {
        adventure.all().sendMessage(message);
    }

    @Override
    public void broadcast(Component message, String permission) {
        adventure.permission(permission).sendMessage(message);
    }

    @Override
    public void broadcastPlayers(Component message) {
        adventure.players().sendMessage(message);
    }

    @Override
    public void broadcastPlayers(Component message, String permission) {
        adventure.filter(audience -> audience != adventure.console() && audience.hasPermission(permission)).sendMessage(message);
    }

    @Override
    public void console(Component message) {
        adventure.console().sendMessage(message);
    }
}
