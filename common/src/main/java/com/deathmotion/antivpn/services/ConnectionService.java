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
import java.util.Optional;
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

    public void handleLogin(UUID uuid, InetAddress address) {
        platform.getScheduler().runAsyncTask((o) -> {
            if (!isValidIp(uuid, address)) {
                return;
            }

            AddressInfo addressInfo = Optional.ofNullable(storageProvider.getAddressInfo(address.getHostAddress()))
                    .orElseGet(() -> {
                        AddressInfo newAddressInfo = apiService.getAddressInfo(address.getHostAddress());
                        if (newAddressInfo != null) {
                            storageProvider.putAddressInfo(address.getHostAddress(), newAddressInfo);
                        }
                        return newAddressInfo;
                    });

            if (addressInfo == null || platform.hasPermission(uuid, "AntiVPN.Bypass")) {
                return;
            }

            if (isVPN(addressInfo)) {
                platform.getScheduler().runTask((o1) -> platform.kickPlayer(uuid, platform.getMessages().vpnDetected()));
            }
        });
    }

    private boolean isValidIp(UUID uuid, InetAddress address) {
        if (address.isLoopbackAddress() || address.isAnyLocalAddress()) {
            platform.getLogManager().warn("Player " + uuid + " joined with a loopback or unspecified local address - " + address.getHostAddress());
            return false;
        }

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

