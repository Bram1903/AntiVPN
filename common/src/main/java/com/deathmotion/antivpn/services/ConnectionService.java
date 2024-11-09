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

package com.deathmotion.antivpn.services;

import com.deathmotion.antivpn.AntiVPNPlatform;
import com.deathmotion.antivpn.models.AddressInfo;
import com.deathmotion.antivpn.storage.StorageProvider;

import java.net.InetAddress;
import java.util.UUID;

public class ConnectionService<P> {

    private final AntiVPNPlatform<P> platform;
    private final StorageProvider storageProvider;
    private final APIService<P> apiService;

    public ConnectionService(AntiVPNPlatform<P> platform) {
        this.platform = platform;
        this.storageProvider = new StorageProvider();
        this.apiService = new APIService<>(platform);
    }

    public void handlePreLogin(UUID uuid, InetAddress address) {
        if (!isValidIp(uuid, address)) return;

        if (storageProvider.getAddressInfo(address.getHostAddress()) == null) {
            AddressInfo addressInfo = apiService.getAddressInfo(address.getHostAddress());

            if (addressInfo != null) {
                storageProvider.putAddressInfo(address.getHostAddress(), apiService.getAddressInfo(address.getHostAddress()));
            }
        }
    }

    public boolean handleLogin(UUID uuid, InetAddress address) {
        if (!isValidIp(uuid, address)) return false;

        AddressInfo addressInfo = storageProvider.getAddressInfo(address.getHostAddress());
        if (addressInfo == null) return false;

        return isVPN(addressInfo);
    }

    private boolean isValidIp(UUID uuid, InetAddress address) {
        // Check if the address is loop back (e.g., 127.0.0.1)
        if (address.isLoopbackAddress() || address.isAnyLocalAddress()) {
            platform.getLogManager().warn("Player " + uuid + " joined with a loopback or unspecified local address - " + address.getHostAddress());
            return false;
        }

        // Check if the address is within a local network (e.g., 192.168.x.x, 10.x.x.x, etc.)
        if (address.isSiteLocalAddress()) {
            platform.getLogManager().warn("Player " + uuid + " joined with a site-local address - " + address.getHostAddress());
            return false;
        }

        return true;
    }

    private boolean isVPN(AddressInfo addressInfo) {
        return addressInfo.isVpn() || addressInfo.isDataCenter() || addressInfo.isOpenProxy();
    }
}
