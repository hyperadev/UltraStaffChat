/*
 * UltraStaffChat BungeeCord - A 100% Customizable StaffChat Plugin for BungeeCord!
 * Copyright (C) 2021 Christian F <christianfdev@gmail.com>
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
import dev.hypera.ultrastaffchat.utils.DiscordWebhook.EmbedObject;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;

import java.awt.*;
import java.io.IOException;

public class Discord {

    private static final String footer = "UltraStaffChat - Bungeecord v" + UltraStaffChat.getInstance().getDescription().getVersion();
    private static final String footerUrl = "https://i.hypera.dev/assets/hypera-icon-white.png";
    private static boolean enabled = false;
    private static String hookURL = "";

    public static void setup() {
        if (!UltraStaffChat.getConfig().getBoolean("discord-enabled").equals(true)) return;
        if (UltraStaffChat.getConfig().getString("discord-webhook") == null || UltraStaffChat.getConfig().getString("discord-webhook").contains("XXXXXXXXXXXXXXXXXX")) {
            Common.logPrefix("&cError: Discord messages are enabled but the webhook URL has not been configured!");
            return;
        }

        hookURL = UltraStaffChat.getConfig().getString("discord-webhook");
        enabled = true;
    }

    public static void reload() {
        enabled = false;
        setup();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void broadcastDiscordJoin(ProxiedPlayer p) {
        if (!isEnabled()) return;
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                DiscordWebhook hook = getHook();
                if (UltraStaffChat.getConfig().getBoolean("discord-embed").equals(false)) {
                    Configuration textFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-join-format");
                    hook.setContent(join_leavePlaceholders(textFormat.getString("text"), p));
                    hook.execute();
                    return;
                }

                EmbedObject embed = createEmbed();
                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-join-format.embed");
                embed.setColor(Color.decode(embedFormat.getString("color")));
                embed.setDescription(join_leavePlaceholders(embedFormat.getString("description"), p));
                embed.setTitle(join_leavePlaceholders(embedFormat.getString("title"), p));
                embed.setThumbnail(embedFormat.getString("thumbnail"));
                embed.setImage(embedFormat.getString("image"));
                embed.setAuthor(embedFormat.getString("author.name"), embedFormat.getString("author.url"), embedFormat.getString("image"));
                embed.setUrl(embedFormat.getString("url"));
                for (String key : embedFormat.getSection("fields").getKeys()) {
                    Configuration sec = UltraStaffChat.getConfig().getConfiguration().getSection("discord-join-format.embed.fields." + key);
                    embed.addField(join_leavePlaceholders(sec.getString("name"), p), join_leavePlaceholders(sec.getString("value"), p), sec.getBoolean("inline"));
                }
                hook.addEmbed(embed);
                hook.execute();
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordLeave(ProxiedPlayer p) {
        if (!isEnabled()) return;
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                DiscordWebhook hook = getHook();

                if (UltraStaffChat.getConfig().getBoolean("discord-embed").equals(false)) {
                    Configuration textFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-leave-format");
                    hook.setContent(join_leavePlaceholders(textFormat.getString("text"), p));
                    hook.execute();
                    return;
                }

                EmbedObject embed = createEmbed();
                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-leave-format.embed");
                embed.setColor(Color.decode(embedFormat.getString("color")));
                embed.setDescription(join_leavePlaceholders(embedFormat.getString("description"), p));
                embed.setTitle(join_leavePlaceholders(embedFormat.getString("title"), p));
                embed.setThumbnail(embedFormat.getString("thumbnail"));
                embed.setImage(embedFormat.getString("image"));
                embed.setAuthor(embedFormat.getString("author.name"), embedFormat.getString("author.url"), embedFormat.getString("image"));
                embed.setUrl(embedFormat.getString("url"));
                for (String key : embedFormat.getSection("fields").getKeys()) {
                    Configuration sec = UltraStaffChat.getConfig().getConfiguration().getSection("discord-leave-format.embed.fields." + key);
                    embed.addField(join_leavePlaceholders(sec.getString("name"), p), join_leavePlaceholders(sec.getString("value"), p), sec.getBoolean("inline"));
                }
                hook.addEmbed(embed);
                hook.execute();
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordSwitch(ProxiedPlayer p, String from, String to) {
        if (!isEnabled()) return;
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                DiscordWebhook hook = getHook();

                if (UltraStaffChat.getConfig().getBoolean("discord-embed").equals(false)) {
                    Configuration textFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-switch-format");
                    hook.setContent(switchPlaceholders(textFormat.getString("text"), p, from, to));
                    try {
                        hook.execute();
                    } catch (IOException e) {
                        Common.log(e.toString());
                    }
                    return;
                }

                EmbedObject embed = createEmbed();
                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-switch-format.embed");
                embed.setColor(Color.decode(embedFormat.getString("color")));
                embed.setDescription(switchPlaceholders(embedFormat.getString("description"), p, from, to));
                embed.setTitle(switchPlaceholders(embedFormat.getString("title"), p, from, to));
                embed.setThumbnail(embedFormat.getString("thumbnail"));
                embed.setImage(embedFormat.getString("image"));
                embed.setAuthor(embedFormat.getString("author.name"), embedFormat.getString("author.url"), embedFormat.getString("image"));
                embed.setUrl(embedFormat.getString("url"));
                for (String key : embedFormat.getSection("fields").getKeys()) {
                    Configuration sec = UltraStaffChat.getConfig().getConfiguration().getSection("discord-switch-format.embed.fields." + key);
                    embed.addField(switchPlaceholders(sec.getString("name"), p, from, to), switchPlaceholders(sec.getString("value"), p, from, to), sec.getBoolean("inline"));
                }
                hook.addEmbed(embed);
                hook.execute();
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordStaffChatMessage(CommandSender s, String message) {
        if (!isEnabled()) return;
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                DiscordWebhook hook = getHook();

                if (UltraStaffChat.getConfig().getBoolean("discord-embed").equals(false)) {
                    Configuration textFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-format");
                    hook.setContent(messagePlaceholders(textFormat.getString("text"), s, message));
                    try {
                        hook.execute();
                    } catch (IOException e) {
                        Common.log(e.toString());
                    }
                    return;
                }

                EmbedObject embed = createEmbed();
                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-format.embed");
                embed.setColor(Color.decode(embedFormat.getString("color")));
                embed.setDescription(messagePlaceholders(embedFormat.getString("description"), s, message));
                embed.setTitle(messagePlaceholders(embedFormat.getString("title"), s, message));
                embed.setThumbnail(embedFormat.getString("thumbnail"));
                embed.setImage(embedFormat.getString("image"));
                embed.setAuthor(embedFormat.getString("author.name"), embedFormat.getString("author.url"), embedFormat.getString("image"));
                embed.setUrl(embedFormat.getString("url"));
                for (String key : embedFormat.getSection("fields").getKeys()) {
                    Configuration sec = UltraStaffChat.getConfig().getConfiguration().getSection("discord-format.embed.fields." + key);
                    embed.addField(messagePlaceholders(sec.getString("name"), s, message), messagePlaceholders(sec.getString("value"), s, message), sec.getBoolean("inline"));
                }
                hook.addEmbed(embed);
                hook.execute();
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordAFKEnable(ProxiedPlayer p) {
        if (!isEnabled()) return;
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                DiscordWebhook hook = getHook();

                if (UltraStaffChat.getConfig().getBoolean("discord-embed").equals(false)) {
                    Configuration textFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-afk-enable-format");
                    hook.setContent(join_leavePlaceholders(textFormat.getString("text"), p));
                    hook.execute();
                    return;
                }

                EmbedObject embed = createEmbed();
                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-afk-enable-format.embed");
                embed.setColor(Color.decode(embedFormat.getString("color")));
                embed.setDescription(afkPlaceholders(embedFormat.getString("description"), p));
                embed.setTitle(afkPlaceholders(embedFormat.getString("title"), p));
                embed.setThumbnail(embedFormat.getString("thumbnail"));
                embed.setImage(embedFormat.getString("image"));
                embed.setAuthor(embedFormat.getString("author.name"), embedFormat.getString("author.url"), embedFormat.getString("image"));
                embed.setUrl(embedFormat.getString("url"));
                for (String key : embedFormat.getSection("fields").getKeys()) {
                    Configuration sec = UltraStaffChat.getConfig().getConfiguration().getSection("discord-afk-enable-format.embed.fields." + key);
                    embed.addField(afkPlaceholders(sec.getString("name"), p), afkPlaceholders(sec.getString("value"), p), sec.getBoolean("inline"));
                }
                hook.addEmbed(embed);
                hook.execute();
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordAFKDisable(ProxiedPlayer p) {
        if (!isEnabled()) return;
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                DiscordWebhook hook = getHook();

                if (UltraStaffChat.getConfig().getBoolean("discord-embed").equals(false)) {
                    Configuration textFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-afk-disable-format");
                    hook.setContent(join_leavePlaceholders(textFormat.getString("text"), p));
                    hook.execute();
                    return;
                }

                EmbedObject embed = createEmbed();
                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration().getSection("discord-afk-disable-format.embed");
                embed.setColor(Color.decode(embedFormat.getString("color")));
                embed.setDescription(afkPlaceholders(embedFormat.getString("description"), p));
                embed.setTitle(afkPlaceholders(embedFormat.getString("title"), p));
                embed.setThumbnail(embedFormat.getString("thumbnail"));
                embed.setImage(embedFormat.getString("image"));
                embed.setAuthor(embedFormat.getString("author.name"), embedFormat.getString("author.url"), embedFormat.getString("image"));
                embed.setUrl(embedFormat.getString("url"));
                for (String key : embedFormat.getSection("fields").getKeys()) {
                    Configuration sec = UltraStaffChat.getConfig().getConfiguration().getSection("discord-afk-disable-format.embed.fields." + key);
                    embed.addField(afkPlaceholders(sec.getString("name"), p), afkPlaceholders(sec.getString("value"), p), sec.getBoolean("inline"));
                }
                hook.addEmbed(embed);
                hook.execute();
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    private static String messagePlaceholders(String string, CommandSender s, String m) {
        return string.replaceAll("\\{message}", m).replaceAll("\\{player}", Common.getDisplayNameSafe(s)).replaceAll("\\{server}", Common.getServerSafe(s));
    }

    private static String join_leavePlaceholders(String string, ProxiedPlayer p) {
        return string.replaceAll("\\{player}", Common.getDisplayNameSafe(p)).replaceAll("\\{server}", Common.getServerSafe(p));
    }

    private static String switchPlaceholders(String string, ProxiedPlayer p, String from, String to) {
        return string.replaceAll("\\{player}", Common.getDisplayNameSafe(p)).replaceAll("\\{to}", to).replaceAll("\\{from}", from);
    }

    private static String afkPlaceholders(String string, ProxiedPlayer p) {
        return string.replaceAll("\\{player}", Common.getDisplayNameSafe(p));
    }

    private static DiscordWebhook getHook() {
        DiscordWebhook hook = new DiscordWebhook(hookURL);
        hook.setAvatarUrl(UltraStaffChat.getConfig().getString("discord-image"));
        hook.setUsername(UltraStaffChat.getConfig().getString("discord-username"));
        return hook;
    }

    private static DiscordWebhook.EmbedObject createEmbed() {
        return new DiscordWebhook.EmbedObject().setFooter(footer, footerUrl);
    }
}
