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

import com.deathmotion.antivpn.AVBukkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class BukkitUser extends CommonUser {

    private final AVBukkit plugin;
    private final Player player;

    public BukkitUser(AVBukkit plugin, Player player) {
        super(player.getUniqueId(), player.getName(), Objects.requireNonNull(player.getAddress()).getAddress());

        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void sendMessage(Component message) {
        plugin.getAdventure().player(player).sendMessage(message);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void kickPlayer(Component reason) {
        if (!Bukkit.isPrimaryThread()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.kickPlayer(LegacyComponentSerializer.legacySection().serialize(reason));
                }
            }.runTask(plugin);
        } else player.kickPlayer(LegacyComponentSerializer.legacySection().serialize(reason));
    }
}
