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

package dev.hypera.ultrastaffchat.managers;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.utils.Debug;
import dev.hypera.ultrastaffchat.utils.TimeUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.lang.reflect.Field;
import java.util.Date;

public class DebugManager {

    public static String generateDebugLog() {
        return String.join("\n", new String[] {
                "&c&lUltraStaffChat &7Debug Log",
                "&7 - Version: &f" + UltraStaffChat.getInstance().getDescription().getVersion(),
                "&7 - &7Config Version: &f" + UltraStaffChat.getConfig().getVersion(),
                "&7> &cRunning on&7: &fBungeeCord",
                "&7 - Version: &f" + ProxyServer.getInstance().getVersion()
        });
    }

    public static String generateAdvancedDebugLog() {
        Date created = new Date();

        StringBuilder stringBuilder = new StringBuilder();
        ProxyServer.getInstance().getPluginManager().getPlugins().forEach(plugin -> stringBuilder.append(getPluginInfo(plugin)).append("\n\n"));

        String[] log = {
                "# UltraStaffChat Advanced Debug Log",
                "# Generated " + TimeUtils.formatDateTime(created) + ".",
                " ",
                "# UltraStaffChat Information",
                "Version: " + UltraStaffChat.getInstance().getDescription().getVersion(),
                "Config Version: " + UltraStaffChat.getConfig().getVersion(),
                "Running since: " + TimeUtils.formatDateTime(Debug.getStartDate()),
                "Start time: " + Debug.getStartLength() + "ms",
                " ",
                "# BungeeCord Information",
                "Version: " + ProxyServer.getInstance().getVersion(),
                "Online Mode: " + ProxyServer.getInstance().getConfig().isOnlineMode(),
                "Players Online: " + ProxyServer.getInstance().getPlayers().size(),
                "Managed Servers: " + ProxyServer.getInstance().getServers().size(),
                "Plugin Count: " + ProxyServer.getInstance().getPluginManager().getPlugins().size(),
                " ",
                "# Java Information",
                "Version: " + System.getProperty("java.version"),
                " ",
                "# Operating System Information",
                "Name: " + System.getProperty("os.name"),
                "Arch: " + System.getProperty("os.arch"),
                "Version: " + System.getProperty("os.version"),
                " ",
                "# System Information",
                "Total Memory: " + Runtime.getRuntime().totalMemory(),
                "Max Memory: " + Runtime.getRuntime().maxMemory(),
                "Free Memory: " + Runtime.getRuntime().freeMemory(),
                "Cores: " + Runtime.getRuntime().availableProcessors(),
                " ",
                "# Enabled Plugins",
                stringBuilder.toString(),
                " ",
                "# Miscellaneous Information",
                "bStats Enabled: " + getbStatsEnabled()

        };

        return String.join("\n", log);
    }

    private static String getPluginInfo(Plugin plugin) {
        return String.join("\n", new String[] {
                plugin.getDescription().getName() + " [" + plugin.getDescription().getVersion() + "]",
                "  - Main: " + plugin.getDescription().getMain(),
                "  - Author: " + (null == plugin.getDescription().getAuthor() ? "Unknown" : plugin.getDescription().getAuthor()),
                "  - Dependencies: " + (null == plugin.getDescription().getDepends() || plugin.getDescription().getDepends().size() < 1 ? "None" : plugin.getDescription().getDepends().toString()),
                "  - Soft Dependencies: " + (null == plugin.getDescription().getSoftDepends() || plugin.getDescription().getSoftDepends().size() < 1 ? "None" : plugin.getDescription().getSoftDepends().toString())
        });
    }

    private static boolean getbStatsEnabled() {
        try {
            Metrics metrics = UltraStaffChat.getInstance().getMetrics();
            Field enabled = metrics.getClass().getDeclaredField("enabled");
            enabled.setAccessible(true);
            return (boolean) enabled.get(metrics);
        } catch (Exception ex) {
            return false;
        }
    }

}
