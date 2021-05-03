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

package dev.hypera.ultrastaffchat.config;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.objects.ErrorCode;
import dev.hypera.ultrastaffchat.utils.Common;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class Config {

	private String fileName = null;
	private Configuration configuration;
	private File configurationFile;
	private File dataFolder;

	public boolean load(String fileName) {
		this.fileName = fileName;
		this.dataFolder = UltraStaffChat.getInstance().getDataFolder();

		if(!dataFolder.exists())
			dataFolder.mkdirs();

		configurationFile = new File(dataFolder, fileName);

		if(!configurationFile.exists()) {
			try {
				InputStream inputStream = UltraStaffChat.getInstance().getResourceAsStream(fileName);
				Files.copy(inputStream, configurationFile.toPath());
				inputStream.close();
			} catch (Exception ex) {
				Common.error(ErrorCode.CONFIG_GENERATE_FAILED, "Failed to generate " + fileName + ".", ex);
				return false;
			}
		}

		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configurationFile);
		} catch (Exception ex) {
			Common.error(ErrorCode.CONFIG_LOAD_FAILED, "Failed to load " + fileName + ".", ex);
			return false;
		}

		return true;
	}

	public boolean reload() {
		return load(fileName);
	}

	public String getString(String path) {
		if(null == configuration) return null;
		return configuration.getString(path);
	}

	public Boolean getBoolean(String path) {
		if(null == configuration) return null;
		return configuration.getBoolean(path);
	}

	public Integer getInteger(String path) {
		if(null == configuration) return null;
		return configuration.getInt(path);
	}

	public List<String> getStringList(String path) {
		if(null == configuration) return null;
		return configuration.getStringList(path);
	}

	public int getVersion() {
		if(null == configuration) return -1;
		return configuration.getInt("version");
	}

	public String getFileName() {
		return fileName;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public File getConfigurationFile() {
		return configurationFile;
	}
	
}
