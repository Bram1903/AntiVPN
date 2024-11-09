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

package com.deathmotion.antivpn.models.api.iprisk;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public class IPRisk {
    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("data_center")
    private boolean dataCenter;

    @SerializedName("open_proxy")
    private boolean openProxy;

    @SerializedName("vpn")
    private boolean vpn;

    @SerializedName("tags")
    private Map<String, Object> tags;

    @SerializedName("network")
    private Network network;

    @SerializedName("geo")
    private Geo geo;

    @SerializedName("elapsed_ms")
    private double elapsedMs;

    @Data
    public static class Network {
        @SerializedName("route")
        private String route;

        @SerializedName("as_number")
        private int asNumber;

        @SerializedName("as_org")
        private String asOrg;

        @SerializedName("as_org_alt")
        private String asOrgAlt;
    }

    @Data
    public static class Geo {
        @SerializedName("continent")
        private String continent;

        @SerializedName("continent_code")
        private String continentCode;

        @SerializedName("country")
        private String country;

        @SerializedName("country_code")
        private String countryCode;

        @SerializedName("country_flag_emoji")
        private String countryFlagEmoji;

        @SerializedName("is_in_eu")
        private boolean isInEu;

        @SerializedName("region")
        private String region;

        @SerializedName("region_code")
        private String regionCode;

        @SerializedName("city")
        private String city;

        @SerializedName("postal_code")
        private String postalCode;

        @SerializedName("time_zone")
        private String timeZone;

        @SerializedName("latitude")
        private double latitude;

        @SerializedName("longitude")
        private double longitude;

        @SerializedName("accuracy_radius")
        private int accuracyRadius;

        @SerializedName("metro_code")
        private int metroCode;
    }
}
