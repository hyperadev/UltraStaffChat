/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 Joshua Sing <joshua@hypera.dev>, Christian F <christianfdev@gmail.com>
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

package dev.hypera.ultrastaffchat.managers;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.events.staff.*;
import dev.hypera.ultrastaffchat.utils.Discord;
import dev.hypera.ultrastaffchat.utils.MD_;
import dev.hypera.ultrastaffchat.utils.StaffChat;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import static dev.hypera.ultrastaffchat.utils.Common.adventurise;
import static dev.hypera.ultrastaffchat.utils.Common.messageRaw;

public class StaffChatManager {

	public static void broadcastMessage(CommandSender sender, String m) {
		String message = (sender.hasPermission(UltraStaffChat.getConfig().getString("permission-chat-format")) ? MD_.parseMarkdown(m) : m);
		UltraStaffChat.getInstance().getAdventure().filter(person -> {
			if(!person.hasPermission(messageRaw("permission-read")))
				return false;
			if(person instanceof ProxiedPlayer && StaffChat.hasMessagesDisabled((ProxiedPlayer) person))
				return false;
			StaffChatEvent staffChatEvent = new StaffChatEvent(sender, person, message);
			ProxyServer.getInstance().getPluginManager().callEvent(staffChatEvent);
			return !staffChatEvent.isCancelled();
		}).sendMessage(adventurise(messageRaw("staffchat-format").replace("{player}", getName(sender)).replace("{server}", getServerSafe(sender)).replace("{message}", message)));
		Discord.broadcastDiscordStaffChatMessage(sender, m);
	}

	public static void broadcastAFK(ProxiedPlayer player, boolean afk) {
		if(!UltraStaffChat.getConfig().getBoolean("afk-enabled"))
			return;
		StaffToggleAFKEvent staffToggleAFKEvent = new StaffToggleAFKEvent(player, afk);
		ProxyServer.getInstance().getPluginManager().callEvent(staffToggleAFKEvent);
		if(staffToggleAFKEvent.isCancelled())
			return;
		Audience audience = UltraStaffChat.getInstance().getAdventure().permission(messageRaw("permission-afk"));
		audience.sendMessage(adventurise((afk ? messageRaw("afk-broadcast") : messageRaw("no-afk-broadcast")).replace("{player}", player.getName())));
		if(afk) {
			Discord.broadcastDiscordAFKEnable(player);
		} else {
			Discord.broadcastDiscordAFKDisable(player);
		}
	}

	public static void broadcastJoin(ProxiedPlayer player) {
		if(!UltraStaffChat.getConfig().getBoolean("join-enabled"))
			return;
		StaffJoinEvent staffJoinEvent = new StaffJoinEvent(player);
		ProxyServer.getInstance().getPluginManager().callEvent(staffJoinEvent);
		if(staffJoinEvent.isCancelled())
			return;
		Audience audience = UltraStaffChat.getInstance().getAdventure().permission(messageRaw("permission-join"));
		audience.sendMessage(adventurise(messageRaw("join-message").replace("{player}", player.getName()).replace("{server}", getServerSafe(player))));
		Discord.broadcastDiscordJoin(player);
	}

	public static void broadcastLeave(ProxiedPlayer player) {
		if(!UltraStaffChat.getConfig().getBoolean("leave-enabled"))
			return;
		StaffLeaveEvent staffLeaveEvent = new StaffLeaveEvent(player);
		ProxyServer.getInstance().getPluginManager().callEvent(staffLeaveEvent);
		if(staffLeaveEvent.isCancelled())
			return;
		Audience audience = UltraStaffChat.getInstance().getAdventure().permission(messageRaw("permission-leave"));
		audience.sendMessage(adventurise(messageRaw("leave-message").replace("{player}", player.getName())));
		Discord.broadcastDiscordLeave(player);
	}

	public static void broadcastSwitch(ProxiedPlayer player, String from, String to) {
		if(!UltraStaffChat.getConfig().getBoolean("switch-enabled"))
			return;
		StaffSwitchServerEvent staffSwitchServerEvent = new StaffSwitchServerEvent(player, from, to);
		ProxyServer.getInstance().getPluginManager().callEvent(staffSwitchServerEvent);
		if(staffSwitchServerEvent.isCancelled())
			return;
		Audience audience = UltraStaffChat.getInstance().getAdventure().permission(messageRaw("permission-switch"));
		Discord.broadcastDiscordSwitch(player, from, to);
		audience.sendMessage(adventurise(messageRaw("switch-message").replace("{player}", player.getName()).replace("{from}", from).replace("{to}", to)));
	}

	private static String getName(CommandSender sender) {
		if(sender instanceof ProxiedPlayer)
			return ((ProxiedPlayer) sender).getName();
		else
			return "Console";
	}

	private static String getServerSafe(CommandSender sender) {
		if(sender instanceof ProxiedPlayer)
			return (null == ((ProxiedPlayer) sender).getServer() ? "Unknown" : ((ProxiedPlayer) sender).getServer().getInfo().getName());
		else
			return "Global";
	}

}
