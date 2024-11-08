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

package com.deathmotion.antivpn.listener;

import com.deathmotion.antivpn.AntiVPNPlatform;
import com.deathmotion.antivpn.data.Constants;
import com.deathmotion.antivpn.models.Settings;
import com.deathmotion.antivpn.util.AVVersion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.concurrent.TimeUnit;

//TODO: Make this class more generic, so it can be used in other platform implementations.
public class UpdateNotifier<P> implements Listener {
    private final AntiVPNPlatform<P> platform;
    private final Component updateComponent;


    /**
     * Constructs a new PlayerJoin with the specified {@link AntiVPNPlatform} and latestVersion of the application.
     *
     * @param platform      The platform to use.
     * @param latestVersion The latest version of the application.
     */
    public UpdateNotifier(AntiVPNPlatform<P> platform, AVVersion latestVersion) {
        this.platform = platform;

        this.updateComponent = Component.text()
                .append(Component.text("[AntiVPN] ", NamedTextColor.RED)
                        .decoration(TextDecoration.BOLD, true))
                .append(Component.text("Version " + latestVersion.toString() + " is ", NamedTextColor.GREEN))
                .append(Component.text("now available", NamedTextColor.GREEN)
                        .decorate(TextDecoration.UNDERLINED)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to download", NamedTextColor.GREEN)))
                        .clickEvent(ClickEvent.openUrl(Constants.SPIGOT_URL)))
                .append(Component.text("!", NamedTextColor.GREEN))
                .build();

        platform.getLogManager().debug("Update detected. Player join listener has been set up.");
    }

    @EventHandler
    public void onUserLogin(PlayerLoginEvent event) {
        final Settings settings = platform.getConfigManager().getSettings();
        if (!settings.getUpdateChecker().isNotifyInGame()) return;

        Player player = event.getPlayer();

        platform.getScheduler().runAsyncTaskDelayed((o) -> {
            if (platform.hasPermission(player.getUniqueId(), "AntiVpn.Notify")) {
                player.sendMessage(updateComponent);
            }
        }, 2, TimeUnit.SECONDS);
    }
}
