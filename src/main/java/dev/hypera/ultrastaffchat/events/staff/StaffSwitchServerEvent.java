/*
 *  UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 *  Copyright (C) 2021 SLLCoding <luisjk266@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.hypera.ultrastaffchat.events.staff;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.ApiStatus;

public class StaffSwitchServerEvent extends Event implements Cancellable {

	private final ProxiedPlayer player;
	private final String from;
	private final String to;
	private ServerInfo fromServer;
	private Server toServer;
	private boolean cancelled = false;

	@Deprecated
	public StaffSwitchServerEvent(ProxiedPlayer player, String from, String to) {
		this.player = player;
		this.from = from;
		this.to = to;
	}

	public StaffSwitchServerEvent(ProxiedPlayer player, ServerInfo from, Server to) {
		this(player, from.getName(), to.getInfo().getName());
		this.fromServer = from;
		this.toServer = to;
	}

	public ProxiedPlayer getPlayer() {
		return player;
	}

	@Deprecated
	public String getFrom() {
		return from;
	}

	@Deprecated
	public String getTo() {
		return to;
	}

	@ApiStatus.Experimental
	public ServerInfo getFromServer() {
		return fromServer;
	}

	@ApiStatus.Experimental
	public Server getToServer() {
		return toServer;
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
