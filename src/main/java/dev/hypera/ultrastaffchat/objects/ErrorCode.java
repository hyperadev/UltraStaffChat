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

package dev.hypera.ultrastaffchat.objects;

public enum ErrorCode {

	UNKNOWN("UNKNOWN", 1, "An unexpected error occurred."),
	REGISTER_FAILED_LISTENER("REGISTER_LISTENER", 2, "An error occurred while trying to register a listener."),
	REGISTER_FAILED_COMMAND("REGISTER_COMMAND", 3, "An error occurred while trying to register a command."),
	CONFIG_GENERATE_FAILED("CONFIG_GENERATE", 4, "An error occurred while trying to generate a configuration file."),
	CONFIG_LOAD_FAILED("CONFIG_LOAD", 5, "An error occurred while trying to load a configuration file. This may be caused by the YAML being invalid."),
	OUTDATED_CONFIG("CONFIG_OUTDATED", 6, "The configuration file is too old for UltraStaffChat to read.");

	private final String code;
	private final int id;
	private final String message;

	ErrorCode(String code, int id, String message) {
		this.code = code;
		this.id = id;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

}
