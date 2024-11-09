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

package com.deathmotion.antivpn.storage;

import com.deathmotion.antivpn.models.AddressInfo;
import com.deathmotion.antivpn.storage.impl.MemoryCache;

public class StorageProvider {

    private final MemoryCache memoryCache;

    public StorageProvider() {
        memoryCache = new MemoryCache();
    }

    public void putAddressInfo(String ip, AddressInfo addressInfo) {
        memoryCache.put(ip, addressInfo);
    }

    public AddressInfo getAddressInfo(String ip) {
        return memoryCache.get(ip);
    }
}
