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

import lombok.Data;

import java.util.Map;

@Data
public class AddressInfo {
    private String ipAddress;
    private boolean dataCenter;
    private boolean openProxy;
    private boolean vpn;
    private Map<String, Object> tags;
    private Network network;
    private Geo geo;
    private double elapsedMs;

    @Data
    public static class Network {
        private String route;
        private int asNumber;
        private String asOrg;
        private String asOrgAlt;
    }

    @Data
    public static class Geo {
        private String continent;
        private String continentCode;
        private String country;
        private String countryCode;
        private String countryFlagEmoji;
        private boolean isInEu;
        private String region;
        private String regionCode;
        private String city;
        private String postalCode;
        private String timeZone;
        private double latitude;
        private double longitude;
        private int accuracyRadius;
        private int metroCode;
    }
}
