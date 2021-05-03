/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 Joshua Sing <joshua@hypera.dev>
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
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.atomic.AtomicInteger;

public class StaffListCommand extends Command {

	public StaffListCommand() {
		super("stafflist", null, "sclist", "slist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Audience audience = UltraStaffChat.getInstance().getAdventure().sender(sender);

		if (!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-list"))) {
			audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission")));
			return;
		}

		AtomicInteger count = new AtomicInteger(0);
		StringBuilder stringBuilder = new StringBuilder();
		ProxyServer.getInstance().getPlayers().stream().filter(player -> player.hasPermission(UltraStaffChat.getConfig().getString("permission-staff"))).forEach(player -> {
			count.getAndIncrement();
			stringBuilder.append(UltraStaffChat.getConfig().getString("stafflist-line").replace("{player}", player.getName()).replace("{server}", player.getServer().getInfo().getName())).append("\n");
		});

		if(count.get() < 1) {
			audience.sendMessage(Common.message("stafflist-offline"));
			return;
		}

		audience.sendMessage(Common.adventurise(Common.messageRaw("stafflist-header").replace("{count}", String.valueOf(count.get())) + "\n" + stringBuilder.deleteCharAt(stringBuilder.length() - 1)));
	}

	@Override
	public boolean isDisabled() {
		return !UltraStaffChat.getConfig().getBoolean("stafflist-enabled");
	}

}
