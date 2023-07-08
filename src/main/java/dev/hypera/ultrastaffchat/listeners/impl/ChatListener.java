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
package dev.hypera.ultrastaffchat.listeners.impl;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.listeners.Listener;
import dev.hypera.ultrastaffchat.managers.StaffChatManager;
import dev.hypera.ultrastaffchat.utils.Common;
import dev.hypera.ultrastaffchat.utils.StaffChat;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.event.EventHandler;

public class ChatListener extends Listener {

	@EventHandler
	public void onChat(ChatEvent e) {
		if(e.isCommand() || e.isProxyCommand())
			return;
		if((e.getSender() instanceof ProxiedPlayer) && StaffChat.hasChatStaffChatEnabled((ProxiedPlayer) e.getSender())) {
			e.setCancelled(true);
			if(StaffChat.staffChatIsMuted()) {
				if(!((ProxiedPlayer) e.getSender()).hasPermission(UltraStaffChat.getConfig().getString("permission-bypass"))) {
					UltraStaffChat.getInstance().getAdventure().sender((ProxiedPlayer) e.getSender()).sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("staffchat-muted")));
					return;
				}
			}
			StaffChatManager.broadcastMessage((ProxiedPlayer) e.getSender(), e.getMessage());
			return;
		}

		if(!(e.getSender() instanceof ProxiedPlayer))
			return;

		ProxiedPlayer p = (ProxiedPlayer) e.getSender();

		if(p.hasPermission(UltraStaffChat.getConfig().getString("permission-talk"))) {
			String prefix = UltraStaffChat.getConfig().getString("staffchat-prefix");
			if(!prefix.equals("") && e.getMessage().startsWith(prefix) && e.getMessage().replaceFirst(prefix, "").length() > 0) {
				e.setCancelled(true);
				if(StaffChat.staffChatIsMuted()) {
					if(!((ProxiedPlayer) e.getSender()).hasPermission(UltraStaffChat.getConfig().getString("permission-bypass"))) {
						UltraStaffChat.getInstance().getAdventure().sender((ProxiedPlayer) e.getSender()).sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("staffchat-muted")));
						return;
					}
				}
				String message = e.getMessage().substring(prefix.length());
				StaffChatManager.broadcastMessage((ProxiedPlayer) e.getSender(), message);
				e.setCancelled(true);
			}
		}
	}

}
