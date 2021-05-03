/*
 *  UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 *  Copyright (C) 2021 SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.ultrastaffchat.events.staff;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class StaffChatEvent extends Event implements Cancellable {

    private final CommandSender sender;
    private final CommandSender receiver;
    private String message;
    private boolean cancelled = false;

    public StaffChatEvent(CommandSender sender, CommandSender receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public CommandSender getSender() {
        return sender;
    }

    public CommandSender getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}
