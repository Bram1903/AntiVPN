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

package com.deathmotion.antivpn.services.adapters.impl;

import com.deathmotion.antivpn.AntiVPNPlatform;
import com.deathmotion.antivpn.models.AddressInfo;
import com.deathmotion.antivpn.models.api.VPNApi;
import com.deathmotion.antivpn.models.api.iprisk.ErrorResponse;
import com.deathmotion.antivpn.models.api.iprisk.IPRisk;
import com.deathmotion.antivpn.services.adapters.APIAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IPRiskAdapter<P> implements APIAdapter {
    private static final String API_URL = "https://api.iprisk.info/v1/";

    private final AntiVPNPlatform<P> platform;
    private final Gson gson;

    public IPRiskAdapter(AntiVPNPlatform<P> platform) {
        this.platform = platform;
        this.gson = new Gson();
    }

    @Override
    public AddressInfo getAddressInfo(String ipAddress) {
        String urlString = API_URL + ipAddress;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            try (Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    IPRisk ipRisk = gson.fromJson(reader, IPRisk.class);
                    return createAddressInfo(ipRisk);
                } else {
                    handleErrorResponse(reader, responseCode);
                }
            }
        } catch (IOException | JsonSyntaxException e) {
            platform.getLogManager().warn("An error occurred while calling the IPRisk API: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private void handleErrorResponse(Reader reader, int responseCode) throws IOException {
        ErrorResponse errorResponse = gson.fromJson(reader, ErrorResponse.class);
        if (errorResponse != null && errorResponse.getError() != null) {
            platform.getLogManager().warn("IPRisk API error: " + errorResponse.getError().getMessage());
        } else {
            platform.getLogManager().warn("Failed to retrieve data from IPRisk API. Response code: " + responseCode);
        }
    }

    private AddressInfo createAddressInfo(IPRisk ipRisk) {
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setIpAddress(ipRisk.getIpAddress());
        addressInfo.setVpnApi(VPNApi.IPRISK);
        addressInfo.setDataCenter(ipRisk.isDataCenter());
        addressInfo.setOpenProxy(ipRisk.isOpenProxy());
        addressInfo.setVpn(ipRisk.isVpn());
        addressInfo.setTags(ipRisk.getTags());
        addressInfo.setElapsedMs(ipRisk.getElapsedMs());

        AddressInfo.Network addressNetwork = mapNetwork(ipRisk.getNetwork());
        addressInfo.setNetwork(addressNetwork);

        AddressInfo.Geo addressGeo = mapGeo(ipRisk.getGeo());
        addressInfo.setGeo(addressGeo);

        return addressInfo;
    }

    private AddressInfo.Network mapNetwork(IPRisk.Network network) {
        if (network == null) return null;
        AddressInfo.Network addressNetwork = new AddressInfo.Network();
        addressNetwork.setRoute(network.getRoute());
        addressNetwork.setAsNumber(network.getAsNumber());
        addressNetwork.setAsOrg(network.getAsOrg());
        addressNetwork.setAsOrgAlt(network.getAsOrgAlt());
        return addressNetwork;
    }

    private AddressInfo.Geo mapGeo(IPRisk.Geo geo) {
        if (geo == null) return null;
        AddressInfo.Geo addressGeo = new AddressInfo.Geo();
        addressGeo.setContinent(geo.getContinent());
        addressGeo.setContinentCode(geo.getContinentCode());
        addressGeo.setCountry(geo.getCountry());
        addressGeo.setCountryCode(geo.getCountryCode());
        addressGeo.setCountryFlagEmoji(geo.getCountryFlagEmoji());
        addressGeo.setInEu(geo.isInEu());
        addressGeo.setRegion(geo.getRegion());
        addressGeo.setRegionCode(geo.getRegionCode());
        addressGeo.setCity(geo.getCity());
        addressGeo.setPostalCode(geo.getPostalCode());
        addressGeo.setTimeZone(geo.getTimeZone());
        addressGeo.setLatitude(geo.getLatitude());
        addressGeo.setLongitude(geo.getLongitude());
        addressGeo.setAccuracyRadius(geo.getAccuracyRadius());
        addressGeo.setMetroCode(geo.getMetroCode());
        return addressGeo;
    }
}


