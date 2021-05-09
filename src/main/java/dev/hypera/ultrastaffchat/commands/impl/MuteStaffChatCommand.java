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

package dev.hypera.ultrastaffchat.commands.impl;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.commands.Command;
import dev.hypera.ultrastaffchat.utils.Common;
import dev.hypera.ultrastaffchat.utils.StaffChat;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;

public class MuteStaffChatCommand extends Command {

	public MuteStaffChatCommand() {
		super("mutestaffchat", null, "mutesc");

	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		Audience audience = UltraStaffChat.getInstance().getAdventure().sender(sender);

		if(!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-globalmute"))) {
			Common.logPrefix(UltraStaffChat.getConfig().getString("permission-globalmute"));
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission")));
			return;
		}

		if(args.length > 1) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-staffchat-usage")));
			return;
		}

		if(args.length == 0) {
			boolean toggled = StaffChat.toggleStaffChatMute();
			if(toggled) {
				audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-staffchat-message-on")));
			} else
				audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-staffchat-message-off")));
			return;
		}

		if(args[0].matches("(?i:(off|false|disable(d)?|unmute(d)?))")) {
			StaffChat.unmuteStaffChat();
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-staffchat-message-off")));
			return;
		}

		if(args[0].matches("(?i:(on|true|enable(d)?|mute(d)?))")) {
			StaffChat.muteStaffChat();
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-staffchat-message-on")));
			return;
		}

		audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-staffchat-usage")));
	}

	@Override
	public boolean isDisabled() {
		return !UltraStaffChat.getConfig().getBoolean("mute-staffchat-enabled");
	}

}
