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
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class BukkitAntiVPN extends AntiVPNPlatform<JavaPlugin> {

    private final AVBukkit plugin;

    public BukkitAntiVPN(AVBukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public JavaPlugin getPlatform() {
        return this.plugin;
    }

    @Override
    public boolean hasPermission(UUID sender, String permission) {
        Player player = Bukkit.getPlayer(sender);
        return player != null && player.hasPermission(permission);
    }

    @Override
    public void sendConsoleMessage(Component message) {
        plugin.getAdventure().console().sendMessage(message);
    }

    @Override
    public String getPluginDirectory() {
        return this.plugin.getDataFolder().getAbsolutePath();
    }

    @Override
    public void addUpdateNotifier(AVVersion latestVersion) {
        Bukkit.getPluginManager().registerEvents(new UpdateNotifier(this.plugin, latestVersion), this.plugin);
    }
}