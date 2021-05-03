/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.ultrastaffchat.listeners;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.objects.ErrorCode;
import dev.hypera.ultrastaffchat.utils.Common;
import net.md_5.bungee.api.ProxyServer;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ListenerManager {

    public static void setup() {
        Reflections reflections = new Reflections("dev.hypera.ultrastaffchat.listeners.impl");

        Set<Class<? extends Listener>> allListeners =
                reflections.getSubTypesOf(Listener.class);

        for (Class<? extends Listener> listenerClass : allListeners) {
            try {
                Constructor<? extends Listener> constructor = listenerClass.getConstructor();
                constructor.setAccessible(true);
                ProxyServer.getInstance().getPluginManager().registerListener(UltraStaffChat.getInstance(), constructor.newInstance());
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                Common.error(ErrorCode.REGISTER_FAILED_LISTENER, "Failed to register listener.", ex);
            }
        }
    }

}
