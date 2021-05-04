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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.hypera.ultrastaffchat.UltraStaffChat;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PasteUtils {

	private static final String PASTE_BASE = "https://paste.hypera.dev/";


	/**
	 * Create a new paste. It is recommended to run this async to avoid slowing down the main thread.
	 *
	 * @param content Paste content.
	 *
	 * @return Paste Url.
	 * @throws Exception Anything that goes wrong while creating the paste.
	 */
	public static String createPaste(String... content) throws Exception {
		URL url = new URL(PASTE_BASE + "/documents");
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
		httpsURLConnection.setRequestMethod("POST");
		httpsURLConnection.setRequestProperty("User-Agent", "UltraStaffChat v" + UltraStaffChat.getInstance().getDescription().getVersion() + " Paste Generator");
		httpsURLConnection.setRequestProperty("Content-Type", "text/plain");
		httpsURLConnection.setDoOutput(true);

		OutputStream outputStream = httpsURLConnection.getOutputStream();
		outputStream.write(String.join("\n", content).getBytes());
		outputStream.flush();
		outputStream.close();

		if(httpsURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStreamReader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			JsonElement jsonElement = new JsonParser().parse(bufferedReader);
			JsonObject object = jsonElement.getAsJsonObject();
			if(!object.has("key"))
				throw new IllegalStateException("Server did not response with a paste key");

			String pasteUrl = PASTE_BASE + object.get("key").getAsString() + ".txt";

			inputStreamReader.close();
			bufferedReader.close();

			return pasteUrl;
		}

		return null;
	}

}
