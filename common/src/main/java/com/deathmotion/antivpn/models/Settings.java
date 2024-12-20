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

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Settings {
    private boolean Debug = false;

    private UpdateChecker UpdateChecker = new UpdateChecker();

    private GeoBlocking GeoBlocking = new GeoBlocking();

    @Getter
    @Setter
    public static class UpdateChecker {
        private boolean Enabled = true;
        private boolean PrintToConsole = true;
        private boolean NotifyInGame = true;
    }

    @Getter
    @Setter
    public static class GeoBlocking {
        private boolean Enabled = false;
        private boolean BlockCountries = true;
        private List<String> Countries;
    }
}
