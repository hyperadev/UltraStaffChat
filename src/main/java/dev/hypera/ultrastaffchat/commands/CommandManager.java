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
package dev.hypera.ultrastaffchat.commands;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.commands.impl.MuteStaffChatCommand;
import dev.hypera.ultrastaffchat.commands.impl.StaffAfkCommand;
import dev.hypera.ultrastaffchat.commands.impl.StaffChatCommand;
import dev.hypera.ultrastaffchat.commands.impl.StaffChatMuteCommand;
import dev.hypera.ultrastaffchat.commands.impl.StaffChatToggleCommand;
import dev.hypera.ultrastaffchat.commands.impl.StaffListCommand;
import dev.hypera.ultrastaffchat.commands.impl.UltraStaffChatCommand;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;

public class CommandManager {

	public static void setup() {
		registerCommand(new MuteStaffChatCommand());
		registerCommand(new StaffAfkCommand());
		registerCommand(new StaffChatCommand());
		registerCommand(new StaffChatMuteCommand());
		registerCommand(new StaffChatToggleCommand());
		registerCommand(new StaffListCommand());
		registerCommand(new UltraStaffChatCommand());
	}

	private static void registerCommand(@NotNull Command command) {
		if (!command.isDisabled()) {
			ProxyServer.getInstance().getPluginManager()
				.registerCommand(UltraStaffChat.getInstance(), command);
		}
	}

}
