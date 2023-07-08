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
package dev.hypera.ultrastaffchat.commands.impl;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.commands.Command;
import dev.hypera.ultrastaffchat.utils.Common;
import dev.hypera.ultrastaffchat.utils.StaffChat;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffChatMuteCommand extends Command {

	public StaffChatMuteCommand() {
		super("staffchatmute", null, "scmute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Audience audience = UltraStaffChat.getInstance().getAdventure().sender(sender);

		if(!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-mute"))) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission")));
			return;
		}

		if(!(sender instanceof ProxiedPlayer)) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("ingame-only")));
			return;
		}

		if(args.length > 1) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-usage")));
			return;
		}


		ProxiedPlayer p = (ProxiedPlayer) sender;
		if(args.length == 0) {
			boolean toggled = StaffChat.toggleMessages(p);
			if(toggled) {
				audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-message-on")));
			} else
				audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-message-off")));
			return;
		}

		if(args[0].matches("(?i:(off|false|disable(d)?|unmute(d)?))")) {
			StaffChat.enableMessages(p);
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-message-off")));
			return;
		}

		if(args[0].matches("(?i:(on|true|enable(d)?|mute(d)?))")) {
			StaffChat.disableMessages(p);
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-message-on")));
			return;
		}

		audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("mute-usage")));
	}

	@Override
	public boolean isDisabled() {
		return !UltraStaffChat.getConfig().getBoolean("mute-enabled");
	}

}
