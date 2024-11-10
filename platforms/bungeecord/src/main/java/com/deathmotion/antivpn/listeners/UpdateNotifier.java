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
import com.deathmotion.antivpn.models.Settings;
import com.deathmotion.antivpn.util.AVVersion;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class UpdateNotifier implements Listener {
    private final AVBungee plugin;
    private final Component updateComponent;

    public UpdateNotifier(AVBungee plugin, AVVersion latestVersion) {
        this.plugin = plugin;
        this.updateComponent = plugin.getAv().getMessageCreator().updateAvailable(latestVersion);
    }

    @EventHandler()
    public void onPlayerConnect(PostLoginEvent event) {
        final Settings settings = plugin.getAv().getConfigManager().getSettings();
        if (!settings.getUpdateChecker().isNotifyInGame()) return;

        ProxiedPlayer player = event.getPlayer();

        plugin.getAv().getScheduler().runAsyncTaskDelayed((o) -> {
            if (plugin.getAv().hasPermission(player.getUniqueId(), "AntiVpn.Update.Notify")) {
                plugin.getAdventure().player(player).sendMessage(updateComponent);
            }
        }, 2, TimeUnit.SECONDS);
    }
}
