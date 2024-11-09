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

package com.deathmotion.antivpn.util;

import com.deathmotion.antivpn.AntiVPNPlatform;
import com.deathmotion.antivpn.data.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class Messages<P> {
    private final AntiVPNPlatform<P> platform;

    public Messages(AntiVPNPlatform<P> platform) {
        this.platform = platform;
    }

    public Component updateAvailable(AVVersion latestVersion) {
        return Component.text("[AntiVPN] ", NamedTextColor.RED)
                .append(Component.text("Version " + latestVersion.toString() + " is ", NamedTextColor.GREEN))
                .append(Component.text("now available", NamedTextColor.GREEN)
                        .decorate(TextDecoration.UNDERLINED)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to download", NamedTextColor.GREEN)))
                        .clickEvent(ClickEvent.openUrl(Constants.SPIGOT_URL))
                )
                .append(Component.text("!", NamedTextColor.GREEN));
    }

    public Component vpnDetected() {
        return Component.text("VPN Detected", NamedTextColor.RED)
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text("Your IP is not residential.", NamedTextColor.GOLD))
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text("If you believe this is a mistake, please contact a server administrator.", NamedTextColor.GRAY));
    }
}
