/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 Christian F <christianfdev@gmail.com>
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

package dev.hypera.ultrastaffchat.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class StaffChat {

	private static HashMap<UUID, Boolean> mutedPlayers = new HashMap<>();
	private static HashMap<UUID, Boolean> toggledPlayers = new HashMap<>();
	private static boolean staffChatMuted = false;

	public static void disableMessages(ProxiedPlayer p) {
		if(!mutedPlayers.containsKey(p.getUniqueId())) {
			mutedPlayers.put(p.getUniqueId(), true);
			return;
		}
		if(mutedPlayers.get(p.getUniqueId()).equals(false))
			mutedPlayers.replace(p.getUniqueId(), false, true);
	}

	public static void enableMessages(ProxiedPlayer p) {
		if(mutedPlayers.containsKey(p.getUniqueId()) && mutedPlayers.get(p.getUniqueId()).equals(true))
			mutedPlayers.replace(p.getUniqueId(), true, false);
	}

	public static boolean toggleMessages(ProxiedPlayer p) {
		if(hasMessagesDisabled(p)) {
			enableMessages(p);
		} else {
			disableMessages(p);
		}
		return hasMessagesDisabled(p);
	}

	public static boolean hasMessagesDisabled(ProxiedPlayer p) {
		return mutedPlayers.containsKey(p.getUniqueId()) && mutedPlayers.get(p.getUniqueId()).equals(true);
	}

	public static boolean staffChatIsMuted() {
		return staffChatMuted;
	}

	public static void muteStaffChat() {
		staffChatMuted = true;
	}

	public static void unmuteStaffChat() {
		staffChatMuted = false;
	}

	public static boolean toggleStaffChatMute() {
		if(staffChatIsMuted()) {
			unmuteStaffChat();
			return false;
		} else
			muteStaffChat();
		return true;
	}

	public static boolean toggleChatStaffChat(ProxiedPlayer p) {
		if(!toggledPlayers.containsKey(p.getUniqueId()) || toggledPlayers.get(p.getUniqueId()).equals(false)) {
			if(!toggledPlayers.containsKey(p.getUniqueId()))
				toggledPlayers.put(p.getUniqueId(), true);
			if(toggledPlayers.get(p.getUniqueId()).equals(false))
				toggledPlayers.replace(p.getUniqueId(), false, true);
			return true;
		}

		toggledPlayers.replace(p.getUniqueId(), true, false);
		return false;
	}

	public static void enableChatStaffChat(ProxiedPlayer p) {
		if(toggledPlayers.containsKey(p.getUniqueId()) && toggledPlayers.get(p.getUniqueId()).equals(false))
			toggledPlayers.replace(p.getUniqueId(), false, true);
		if(!toggledPlayers.containsKey(p.getUniqueId()))
			toggledPlayers.put(p.getUniqueId(), true);
	}

	public static void disableChatStaffChat(ProxiedPlayer p) {
		if(!toggledPlayers.containsKey(p.getUniqueId())) {
			toggledPlayers.put(p.getUniqueId(), false);
			return;
		}
		if(toggledPlayers.get(p.getUniqueId()).equals(true))
			toggledPlayers.replace(p.getUniqueId(), true, false);
	}

	public static boolean hasChatStaffChatEnabled(ProxiedPlayer p) {
		return toggledPlayers.containsKey(p.getUniqueId()) && toggledPlayers.get(p.getUniqueId()).equals(true);
	}

}