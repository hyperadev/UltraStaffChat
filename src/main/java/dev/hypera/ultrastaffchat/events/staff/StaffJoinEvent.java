/*
 * This file is a part of UltraStaffChat (https://github.com/HyperaDev/UltraStaffChat).
 *
 * Copyright (C) 2021-2023 The UltraStaffChat Authors.
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package dev.hypera.ultrastaffchat.events.staff;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player with the permission `staffchat.join` joins the proxy. When cancelled, the staff join message is
 * not shown.
 */
public class StaffJoinEvent extends Event implements Cancellable {

	private boolean cancelled = false;
	private final ProxiedPlayer player;
	private final Server server;

	@Deprecated
	public StaffJoinEvent(@NotNull ProxiedPlayer player) {
		this(player, player.getServer());
	}

	public StaffJoinEvent(@NotNull ProxiedPlayer player, Server server) {
		this.player = player;
		this.server = server;
	}

	public @NotNull ProxiedPlayer getPlayer() {
		return player;
	}

	@ApiStatus.Experimental
	public Server getServer() {
		return server;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

}
