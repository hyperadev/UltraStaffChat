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
package dev.hypera.ultrastaffchat.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

	public static String formatDateTime(Date date) {
		if(!dateFormat.getTimeZone().getID().equalsIgnoreCase("UTC"))
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}

	public static String formatFileDateTime(Date date) {
		if(!fileDateFormat.getTimeZone().getID().equalsIgnoreCase("UTC"))
			fileDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return fileDateFormat.format(date);
	}

}
