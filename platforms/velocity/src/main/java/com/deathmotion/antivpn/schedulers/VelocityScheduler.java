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

import com.deathmotion.antivpn.AVVelocity;
import com.deathmotion.antivpn.interfaces.Scheduler;
import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class VelocityScheduler implements Scheduler {

    private final AVVelocity plugin;
    private final ProxyServer proxy;

    @Inject
    public VelocityScheduler(AVVelocity plugin, ProxyServer server) {
        this.plugin = plugin;
        this.proxy = server;
    }

    @Override
    public void runTask(@NotNull Consumer<Object> task) {
        this.proxy.getScheduler()
                .buildTask(this.plugin, () -> task.accept(null))
                .schedule();
    }

    @Override
    public void runTaskDelayed(@NotNull Consumer<Object> task, long delay) {
        // Convert delay from ticks to milliseconds (1 tick = 50 ms)
        long delayInMillis = delay * 50;

        this.proxy.getScheduler()
                .buildTask(this.plugin, () -> task.accept(null))
                .delay(delayInMillis, TimeUnit.MILLISECONDS)
                .schedule();
    }

    @Override
    public void runAsyncTask(@NotNull Consumer<Object> task) {
        this.proxy.getScheduler()
                .buildTask(this.plugin, () -> task.accept(null))
                .schedule();
    }

    @Override
    public void runAsyncTaskDelayed(@NotNull Consumer<Object> task, long delay, @NotNull TimeUnit timeUnit) {
        this.proxy.getScheduler()
                .buildTask(this.plugin, () -> task.accept(null))
                .delay(delay, timeUnit)
                .schedule();
    }

    @Override
    public void runAsyncTaskAtFixedRate(@NotNull Consumer<Object> task, long delay, long period, @NotNull TimeUnit timeUnit) {
        this.proxy.getScheduler()
                .buildTask(this.plugin, () -> task.accept(null))
                .delay(delay, timeUnit)
                .repeat(period, timeUnit)
                .schedule();
    }
}
