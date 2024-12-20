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

package com.deathmotion.antivpn.util;

import com.deathmotion.antivpn.AntiVPNPlatform;
import com.deathmotion.antivpn.data.Constants;
import com.deathmotion.antivpn.interfaces.Messenger;
import com.deathmotion.antivpn.managers.LogManager;
import com.deathmotion.antivpn.models.Settings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker<P> {
    private final AntiVPNPlatform<P> platform;
    private final Settings settings;
    private final LogManager<P> logManager;
    private final Messenger messenger;

    public UpdateChecker(AntiVPNPlatform<P> platform) {
        this.platform = platform;
        this.settings = platform.getConfigManager().getSettings();
        this.logManager = platform.getLogManager();
        this.messenger = platform.getMessenger();

        if (settings.getUpdateChecker().isEnabled()) {
            checkForUpdate();
        }
    }

    public void checkForUpdate() {
        CompletableFuture.runAsync(() -> {
            try {
                AVVersion localVersion = AVVersions.CURRENT;
                AVVersion latestVersion = fetchLatestGitHubVersion();

                if (latestVersion != null) {
                    handleVersionComparison(localVersion, latestVersion);
                } else {
                    logManager.warn("Unable to fetch the latest version from GitHub.");
                }
            } catch (Exception ex) {
                logManager.warn("Failed to check for updates: " + ex.getMessage());
            }
        });
    }

    private AVVersion fetchLatestGitHubVersion() {
        try {
            URLConnection connection = new URL(Constants.GITHUB_API_URL).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String jsonResponse = reader.readLine();
            reader.close();
            JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
            return AVVersion.fromString(jsonObject.get("tag_name").getAsString().replaceFirst("^[vV]", ""));
        } catch (IOException e) {
            logManager.warn("Failed to parse AntiHealthIndicator version! Version API: " + e.getMessage());
            return null;
        }
    }

    private void handleVersionComparison(AVVersion localVersion, AVVersion latestVersion) {
        if (localVersion.isOlderThan(latestVersion)) {
            notifyUpdateAvailable(localVersion, latestVersion);
        } else if (localVersion.isNewerThan(latestVersion)) {
            notifyOnDevBuild(localVersion, latestVersion);
        }
    }

    private void notifyUpdateAvailable(AVVersion currentVersion, AVVersion newVersion) {
        if (settings.getUpdateChecker().isPrintToConsole()) {
            messenger.console(Component.text("[AntiVPN] ", NamedTextColor.DARK_GREEN)
                    .append(Component.text("Update available! ", NamedTextColor.BLUE))
                    .append(Component.text("Current version: ", NamedTextColor.WHITE))
                    .append(Component.text(currentVersion.toString(), NamedTextColor.GOLD))
                    .append(Component.text(" | New version: ", NamedTextColor.WHITE))
                    .append(Component.text(newVersion.toString(), NamedTextColor.DARK_PURPLE)));
        }
        if (settings.getUpdateChecker().isNotifyInGame()) {
            platform.addUpdateNotifier(newVersion);
        }
    }

    private void notifyOnDevBuild(AVVersion currentVersion, AVVersion newVersion) {
        if (settings.getUpdateChecker().isPrintToConsole()) {
            messenger.console(Component.text("[AntiVPN] ", NamedTextColor.DARK_GREEN)
                    .append(Component.text("Development build detected. ", NamedTextColor.WHITE))
                    .append(Component.text("Current version: ", NamedTextColor.WHITE))
                    .append(Component.text(currentVersion.toString(), NamedTextColor.AQUA))
                    .append(Component.text(" | Latest stable version: ", NamedTextColor.WHITE))
                    .append(Component.text(newVersion.toString(), NamedTextColor.DARK_AQUA)));
        }
    }
}
