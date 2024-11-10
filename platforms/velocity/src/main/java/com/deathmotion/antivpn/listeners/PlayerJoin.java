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
import com.deathmotion.antivpn.VelocityAntiVPN;
import com.deathmotion.antivpn.models.VelocityUser;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;

public class PlayerJoin {
    private final VelocityAntiVPN av;

    public PlayerJoin(AVVelocity plugin) {
        this.av = plugin.getAv();
    }

    @Subscribe
    public void onPlayerConnect(PostLoginEvent event) {
        av.getConnectionService().handleLogin(new VelocityUser(event.getPlayer()));
    }
}
