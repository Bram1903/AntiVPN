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

package com.deathmotion.antivpn.managers;

import com.deathmotion.antivpn.AntiVPNPlatform;
import com.deathmotion.antivpn.models.Settings;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConfigManager<P> {
    private final AntiVPNPlatform<P> platform;

    @Getter
    private Settings settings;

    public ConfigManager(AntiVPNPlatform<P> platform) {
        this.platform = platform;
        saveDefaultConfiguration();
        loadConfig();
    }

    private void saveDefaultConfiguration() {
        File pluginDirectory = new File(platform.getPluginDirectory());
        File configFile = new File(pluginDirectory, "config.yml");

        if (!pluginDirectory.exists() && !pluginDirectory.mkdirs()) {
            platform.getLogManager().severe("Failed to create plugin directory: " + pluginDirectory.getAbsolutePath());
            return;
        }

        if (!configFile.exists()) {
            try (InputStream inputStream = getClass().getResourceAsStream("/config.yml")) {
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath());
                } else {
                    platform.getLogManager().severe("Default configuration file not found in resources!");
                }
            } catch (IOException e) {
                platform.getLogManager().severe("Failed to save default configuration file: " + e.getMessage());
            }
        }
    }

    private void loadConfig() {
        File configFile = new File(platform.getPluginDirectory(), "config.yml");

        if (!configFile.exists()) {
            platform.getLogManager().severe("Config file not found!");
            return;
        }

        try (InputStream inputStream = Files.newInputStream(configFile.toPath())) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            this.settings = new Settings();
            setConfigOptions(yamlData, this.settings);
        } catch (IOException e) {
            platform.getLogManager().severe("Failed to load configuration: " + e.getMessage());
            platform.commonOnDisable();
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    private void setConfigOptions(Map<String, Object> yamlData, Settings settings) {
        settings.setDebug(getBoolean(yamlData, "debug.enabled", false));

        Settings.GeoBlocking geoBlocking = new Settings.GeoBlocking();
        geoBlocking.setEnabled(getBoolean(yamlData, "geo-blocking.enabled", false));
        geoBlocking.setBlockCountries(getBoolean(yamlData, "geo-blocking.block-countries", true));
        geoBlocking.setCountries(getStringList(yamlData, "geo-blocking.countries"));
        settings.setGeoBlocking(geoBlocking);
    }

    private boolean getBoolean(Map<String, Object> yamlData, String key, boolean defaultValue) {
        Object value = findNestedValue(yamlData, key.split("\\."), defaultValue);
        return value instanceof Boolean ? (Boolean) value : defaultValue;
    }

    private List<String> getStringList(Map<String, Object> yamlData, String key) {
        Object value = findNestedValue(yamlData, key.split("\\."), null);
        if (value instanceof List) {
            return (List<String>) value;
        }
        return Collections.emptyList();
    }

    private String getString(Map<String, Object> yamlData, String key, String defaultValue) {
        Object value = findNestedValue(yamlData, key.split("\\."), defaultValue);
        return value instanceof String ? (String) value : defaultValue;
    }

    private Object findNestedValue(Map<String, Object> yamlData, String[] keys, Object defaultValue) {
        Object value = yamlData;
        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(key);
            } else {
                platform.getLogManager().severe("Invalid config structure for key: " + String.join(".", keys));
                return defaultValue;
            }
        }
        return value != null ? value : defaultValue;
    }
}

