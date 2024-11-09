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

package com.deathmotion.antivpn.storage.impl;

import com.deathmotion.antivpn.models.AddressInfo;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

public class MemoryCache {
    private final ExpiringMap<String, AddressInfo> cache = ExpiringMap.builder()
            .expiration(10, TimeUnit.MINUTES)
            .build();

    public void put(String key, AddressInfo value) {
        cache.put(key, value);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public AddressInfo get(String key) {
        return cache.get(key);
    }
}
