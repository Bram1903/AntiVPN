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

package com.deathmotion.antivpn.schedulers;

import com.deathmotion.antivpn.AVBukkit;
import com.deathmotion.antivpn.interfaces.Scheduler;
import com.deathmotion.antivpn.schedulers.impl.FoliaScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BukkitScheduler implements Scheduler {

    private final AVBukkit plugin;

    public BukkitScheduler(AVBukkit plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runTask(@NotNull Consumer<Object> task) {
        FoliaScheduler.getGlobalRegionScheduler().run(plugin, task);
    }

    @Override
    public void runAsyncTask(@NotNull Consumer<Object> task) {
        FoliaScheduler.getAsyncScheduler().runNow(plugin, task);
    }

    @Override
    public void runAsyncTaskDelayed(@NotNull Consumer<Object> task, long delay, @NotNull TimeUnit timeUnit) {
        FoliaScheduler.getAsyncScheduler().runDelayed(plugin, task, delay, timeUnit);
    }

    @Override
    public void runAsyncTaskAtFixedRate(@NotNull Consumer<Object> task, long delay, long period, @NotNull TimeUnit timeUnit) {
        FoliaScheduler.getAsyncScheduler().runAtFixedRate(plugin, task, delay, period, timeUnit);
    }
}
