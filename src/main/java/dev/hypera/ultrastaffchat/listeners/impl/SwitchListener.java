/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 Joshua Sing <joshua@hypera.dev>, SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.ultrastaffchat.listeners.impl;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.listeners.Listener;
import dev.hypera.ultrastaffchat.managers.StaffChatManager;
import dev.hypera.ultrastaffchat.utils.Common;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.event.EventHandler;

public class SwitchListener extends Listener {

	@EventHandler
	public void onServerSwitch(ServerSwitchEvent event) {
		if (event.getFrom() != null && Common.hasPermission(event.getPlayer(), UltraStaffChat.getConfig().getString("permission-switch"))) StaffChatManager.broadcastSwitch(event.getPlayer(), event.getFrom(), event.getPlayer().getServer());
		else if (Common.hasPermission(event.getPlayer(), UltraStaffChat.getConfig().getString("permission-join"))) StaffChatManager.broadcastJoin(event.getPlayer(), event.getPlayer().getServer());
	}

}
