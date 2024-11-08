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

package com.deathmotion.antivpn;


import com.deathmotion.antivpn.interfaces.Scheduler;
import com.deathmotion.antivpn.managers.ConfigManager;
import com.deathmotion.antivpn.managers.LogManager;
import com.deathmotion.antivpn.util.AVVersion;
import com.deathmotion.antivpn.util.Messages;
import com.deathmotion.antivpn.util.UpdateChecker;
import lombok.Getter;
import net.kyori.adventure.text.Component;

import java.util.UUID;

@Getter
public abstract class AntiVPNPlatform<P> {
    protected ConfigManager<P> configManager;
    protected LogManager<P> logManager;

    protected Messages<P> messages;

    protected Scheduler scheduler;

    /**
     * Called when the platform is initialized.
     */
    public void commonOnInitialize() {
        logManager = new LogManager<>(this);
        configManager = new ConfigManager<>(this);
    }

    /**
     * Called when the platform is enabled.
     */
    public void commonOnEnable() {
        messages = new Messages<>(this);

        // TODO: Uncomment this when the first version is released
        //new UpdateChecker<>(this);

        logManager.info("AntiVPN is enabled!");
    }

    /**
     * Called when the platform gets disabled.
     */
    public void commonOnDisable() {
    }

    /**
     * Gets the platform.
     *
     * @return The platform.
     */
    public abstract P getPlatform();

    /**
     * Checks if a sender has a certain permission.
     *
     * @param sender     The UUID of the entity to check.
     * @param permission The permission string to check.
     * @return true if the entity has the permission, false otherwise.
     */
    public abstract boolean hasPermission(UUID sender, String permission);

    /**
     * Sends a console message.
     *
     * @param message The message to send.
     */
    public abstract void sendConsoleMessage(Component message);

    /**
     * Gets the plugin directory.
     *
     * @return The plugin directory.
     */
    public abstract String getPluginDirectory();

    /**
     * Adds an update notifier.
     *
     * @param latestVersion The latest version.
     */
    public abstract void addUpdateNotifier(AVVersion latestVersion);
}