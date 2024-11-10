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
import com.deathmotion.antivpn.models.Settings;
import com.deathmotion.antivpn.util.AVVersion;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;

import java.util.concurrent.TimeUnit;

public class UpdateNotifier {

    private final AVVelocity plugin;
    private final Component updateComponent;

    public UpdateNotifier(AVVelocity plugin, AVVersion latestVersion) {
        this.plugin = plugin;
        this.updateComponent = plugin.getAv().getMessageCreator().updateAvailable(latestVersion);
    }

    @Subscribe
    public void onPlayerConnect(PostLoginEvent event) {
        final Settings settings = plugin.getAv().getConfigManager().getSettings();
        if (!settings.getUpdateChecker().isNotifyInGame()) return;

        Player player = event.getPlayer();

        plugin.getAv().getScheduler().runAsyncTaskDelayed((o) -> {
            if (plugin.getAv().hasPermission(player.getUniqueId(), "AntiVpn.Update.Notify")) {
                player.sendMessage(updateComponent);
            }
        }, 2, TimeUnit.SECONDS);
    }
}

