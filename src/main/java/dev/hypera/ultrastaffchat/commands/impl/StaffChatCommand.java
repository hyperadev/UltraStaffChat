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
import dev.hypera.ultrastaffchat.managers.StaffChatManager;
import dev.hypera.ultrastaffchat.utils.Common;
import dev.hypera.ultrastaffchat.utils.StaffChat;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;

public class StaffChatCommand extends Command {

	public StaffChatCommand() {
		super("staffchat", null, "sc");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Audience audience = UltraStaffChat.getInstance().getAdventure().sender(sender);

		if(!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-talk"))) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission")));
			return;
		}

		if(args.length == 0) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("staffchat-usage")));
			return;
		}

		if(StaffChat.staffChatIsMuted()) {
			if(!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-bypass"))) {
				audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("staffchat-muted")));
				return;
			}
		}

		StringBuilder message = new StringBuilder();
		for(String word : args) {
			message.append(word).append(" ");
		}
		message.deleteCharAt(message.length() - 1);
		StaffChatManager.broadcastMessage(sender, message.toString());
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
