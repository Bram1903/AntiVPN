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

package com.deathmotion.antivpn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.kyori.adventure.text.Component;

import java.net.InetAddress;
import java.util.UUID;

@AllArgsConstructor
@Data
public abstract class CommonUser {
    private final UUID uuid;
    private final String username;
    private final InetAddress address;

    public abstract void sendMessage(Component message);

    public abstract boolean hasPermission(String permission);

    public abstract void kickPlayer(Component reason);
}
