/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 SLLCoding <luisjk266@gmail.com>
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
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Cancellable;
import org.jetbrains.annotations.ApiStatus;

/**
 * Called when a player with the permission `staffchat.join` joins the proxy. When cancelled, the staff join message is
 * not shown.
 */
public class StaffJoinEvent extends PostLoginEvent implements Cancellable {

	private boolean cancelled = false;
	private final Server server;

	@Deprecated
	public StaffJoinEvent(ProxiedPlayer player) {
		this(player, player.getServer());
	}

	public StaffJoinEvent(ProxiedPlayer player, Server server) {
		super(player);
		this.server = server;
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
