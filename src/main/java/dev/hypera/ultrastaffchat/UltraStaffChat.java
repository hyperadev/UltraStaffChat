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

package dev.hypera.ultrastaffchat;

import dev.hypera.ultrastaffchat.commands.CommandManager;
import dev.hypera.ultrastaffchat.config.Config;
import dev.hypera.ultrastaffchat.listeners.ListenerManager;
import dev.hypera.ultrastaffchat.objects.ErrorCode;
import dev.hypera.ultrastaffchat.utils.Common;
import dev.hypera.ultrastaffchat.utils.Debug;
import dev.hypera.ultrastaffchat.utils.Discord;
import dev.hypera.updatelib.UpdateLib;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class UltraStaffChat extends Plugin {

	private static UltraStaffChat instance;
	private static final Config config = new Config();

	private BungeeAudiences adventure;
	private Metrics metrics;
	private UpdateLib updateLib;

	@Override
	public void onEnable() {
		try {
			Logger.getLogger("org.reflections").setLevel(Level.OFF);
			Debug.start();

			instance = this;
			Common.logPrefix("&aStarting...");

			metrics = new Metrics(this, Common.getbStatsId());

			if(!config.load("config.yml")) {
				return;
			}

			if(config.getVersion() < Common.getConfigVersion()) {
				Common.error(ErrorCode.OUTDATED_CONFIG, "Outdated configuration file - View https://docs.hypera.dev/docs/ultrastaffchat-bungeecord/updating/" + Common.getConfigVersion() + " for more information on updating", new IllegalStateException("Outdated configuration file"));
				onDisable();
				return;
			}

			adventure = BungeeAudiences.create(this);
			updateLib = UpdateLib.builder()
					.version(getDescription().getVersion())
					.resource(Common.getResourceId())
					.handler(status -> {
						if (status.isAvailable())
							Common.logPrefix("&cAn update is available! " + getDescription().getVersion() + " -> " + status.getDistributedVersion());
					}).build();

			ListenerManager.setup();
			CommandManager.setup();
			Discord.setup();

			Debug.finishStart();

			Common.log(
					"&r &r &r",
					"&c  __  ___________",
					"&c / / / / __/ ___/ &r &c&lUltraStaffChat&r &8BungeeCord",
					"&c/ /_/ /\\ \\/ /__&c &r &c &r &8Running Version &c" + getDescription().getVersion(),
					"&c\\____/___/\\___/",
					"&r &r &r"
			);
			Common.logPrefix("&aSuccessfully started. &8Took " + Debug.getStartLength() + "ms");
		} catch (Exception ex) {
			Common.error(ErrorCode.UNKNOWN, "Error while starting.", ex);
		}
	}

	@Override
	public void onDisable() {
		if(adventure != null) {
			adventure.close();
			adventure = null;
		}
		Common.log("&cDisabled");
	}

	public static UltraStaffChat getInstance() {
		return instance;
	}

	public static Config getConfig() {
		return config;
	}

	public BungeeAudiences getAdventure() {
		if(adventure == null)
			throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
		return adventure;
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public UpdateLib getUpdateLib() {
		if(updateLib == null)
			throw new IllegalStateException("Cannot retrieve UpdateLib while the plugin is not enabled");
		return updateLib;
	}

}
