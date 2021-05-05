/*
 *  UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 *  Copyright (C) 2021 SLLCoding
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

package dev.hypera.ultrastaffchat.commands.impl;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.commands.Command;
import dev.hypera.ultrastaffchat.utils.Common;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffAfkCommand extends Command {

    public StaffAfkCommand() {
        super("staffchatafk", null, "scafk");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Audience audience = UltraStaffChat.getInstance().getAdventure().sender(sender);

        if(!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-afk"))) {
            audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission")));
            return;
        }

        if(!(sender instanceof ProxiedPlayer)) {
            audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("ingame-only")));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;
        boolean toggled = toggleAfk(p);
        Audience all = UltraStaffChat.getInstance().getAdventure().permission(UltraStaffChat.getConfig().getString("permission-read"));
        if(toggled) {
            all.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("afk-broadcast").replaceAll("\\{player}", p.getName())));
        } else
            all.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-afk-broadcast").replaceAll("\\{player}", p.getName())));
    }

    @Override
    public boolean isDisabled() {
        return !UltraStaffChat.getConfig().getBoolean("afk-enabled");
    }

    private final Map<UUID, Boolean> afkStatus = new HashMap<>();

    private boolean toggleAfk(ProxiedPlayer player) {
        if (!afkStatus.containsKey(player.getUniqueId())) {
            afkStatus.put(player.getUniqueId(), true);
            return true;
        } else {
            boolean oldAfk = afkStatus.get(player.getUniqueId());
            afkStatus.replace(player.getUniqueId(), !oldAfk);
            return !oldAfk;
        }
    }

}
