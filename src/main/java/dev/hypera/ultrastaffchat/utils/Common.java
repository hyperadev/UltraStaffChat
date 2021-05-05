/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 Joshua Sing <joshua@hypera.dev>, Christian F <christianfdev@gmail.com>, SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.ultrastaffchat.utils;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.config.Config;
import dev.hypera.ultrastaffchat.objects.ErrorCode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Common {

	private static final String logPrefix = "&7[&c&lUltraStaffChat&7] &f";
	private static final String errorPrefix = "&7[&4&lError&7] &4";

	private static final int bStatsId = 5040;
	private static final long resourceId = 68956;
	private static final int configVersion = 36;
	private static final String[] contributors = new String[] { "Joshua Sing", "Christian F", "SLLCoding", "Hunter Y" };

	/**
	 * Logs messages to console.
	 *
	 * @param messages The messages to be logged.
	 */
	public static void log(String... messages) {
		for(String message : messages)
			ProxyServer.getInstance().getConsole().sendMessage(colour(message));
	}

	/**
	 * Logs messages to console with a prefix.
	 *
	 * @param messages The messages to be logged.
	 */
	public static void logPrefix(String... messages) {
		for(String message : messages)
			ProxyServer.getInstance().getConsole().sendMessage(colour(logPrefix + message));
	}

	/**
	 * Logs an error log to console.
	 *
	 * @param errorCode Error code.
	 * @param message   Any information about the error.
	 * @param throwable Throwable.
	 */
	public static void error(ErrorCode errorCode, String message, Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);

		logPrefix(errorPrefix + errorCode.getMessage(), errorPrefix + "Error code: " + errorCode.getCode(), errorPrefix + "Message: " + message, errorPrefix + "Stack trace:\n" + stringWriter.toString());

		try {
			printWriter.close();
			stringWriter.close();
		} catch (Exception ignored) {

		}
	}

	/**
	 * Colourises a message and converts it to a BaseComponent.
	 *
	 * @param message The message to be colourised.
	 *
	 * @return BaseComponent Colourised message.
	 */
	public static BaseComponent[] colour(String message) {
		return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message));
	}

	/**
	 * Colourises a message.
	 *
	 * @param message The message to be colourised.
	 *
	 * @return Colourised message.
	 */
	public static String basicColour(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * Checks if a CommandSender has a permission.
	 *
	 * @param sender     The CommandSender to be checked.
	 * @param permission The permission.
	 *
	 * @return If the CommandSender has the permission.
	 */
	public static boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(permission) || sender.hasPermission("*");
	}

	/**
	 * Get SpigotMC Resource ID.
	 *
	 * @return Resource ID.
	 */
	public static long getResourceId() {
		return resourceId;
	}

	/**
	 * Get bStats ID.
	 *
	 * @return bStats ID.
	 */
	public static int getbStatsId() {
		return bStatsId;
	}

	/**
	 * Get current configuration version.
	 *
	 * @return Current config version.
	 */
	public static int getConfigVersion() {
		return configVersion;
	}

	/**
	 * Returns a list of people that have contributed to UltraStaffChat.
	 *
	 * @return contributors.
	 */
	public static String[] getContributors() {
		return contributors;
	}

	/**
	 * Get a Component message from the config.
	 *
	 * @return (Adventure Component) message.
	 */
	public static Component message(String key) {
		return adventurise(messageRaw(key));
	}

	/**
	 * Turns a message into an Adventure Component.
	 *
	 * @return (Adventure Component) message.
	 */
	public static Component adventurise(String message) {
		return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
	}

	/**
	 * Get a raw message from the config.
	 *
	 * @return (String) message.
	 */
	public static String messageRaw(String key) {
		Config config = UltraStaffChat.getConfig();
		String message = config.getString(key);
		if(message == null)
			return "&7undefined";
		return message;
	}

	public static String getDisplayNameSafe(CommandSender s) {
		if((s instanceof ProxiedPlayer)) {
			return ((ProxiedPlayer) s).getDisplayName();
		} else {
			return "Console";
		}
	}

	public static String getServerSafe(CommandSender s) {
		if((s instanceof ProxiedPlayer)) {
			return ((ProxiedPlayer) s).getServer().getInfo().getName();
		} else {
			return "Undefined";
		}
	}

}
