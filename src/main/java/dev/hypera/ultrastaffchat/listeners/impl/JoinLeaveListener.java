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
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class JoinLeaveListener extends Listener {

    @EventHandler
    public void onPostJoin(PostLoginEvent event) {
        if (Common.hasPermission(event.getPlayer(), UltraStaffChat.getConfig().getString("permission-join"))) {
            // We have to delay the broadcast so we can display the server they joined.
            ProxyServer.getInstance().getScheduler().schedule(UltraStaffChat.getInstance(), () -> StaffChatManager.broadcastJoin(event.getPlayer()), 3, TimeUnit.SECONDS);
        }

        if(UltraStaffChat.getConfig().getBoolean("update-enabled") && Common.hasPermission(event.getPlayer(), Common.messageRaw("permission-update"))) {
            if(null != UltraStaffChat.getInstance().getUpdateLib().getLastResponse() && UltraStaffChat.getInstance().getUpdateLib().getLastResponse().isUpdateAvailable()) {
                UltraStaffChat.getInstance().getAdventure().player(event.getPlayer()).sendMessage(Common.adventurise(Common.messageRaw("update-message").replace("{version}", UltraStaffChat.getInstance().getUpdateLib().getLastResponse().getSpigotVersion()).replace("{current}", UltraStaffChat.getInstance().getDescription().getVersion())));
            }
        }

    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        if (Common.hasPermission(event.getPlayer(), UltraStaffChat.getConfig().getString("permission-leave"))) {
            StaffChatManager.broadcastLeave(event.getPlayer());
        }
    }

}
