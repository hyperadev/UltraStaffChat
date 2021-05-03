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

package dev.hypera.ultrastaffchat.utils;

import java.util.Date;

public class Debug {

	private static Date startDate;

	private static long startTime;
	private static long startFinishTime;
	private static long startLength;

	public static void start() {
		startTime = System.currentTimeMillis();
	}

	public static void finishStart() {
		startFinishTime = System.currentTimeMillis();
		startLength = startFinishTime - startTime;

		startDate = new Date();
	}

	public static Date getStartDate() {
		return startDate;
	}

	public static long getStartTime() {
		return startTime;
	}

	public static long getStartFinishTime() {
		return startFinishTime;
	}

	public static long getStartLength() {
		return startLength;
	}

}
