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

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedAuthor;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedField;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedFooter;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedTitle;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import dev.hypera.ultrastaffchat.UltraStaffChat;
import java.awt.Color;
import java.util.function.Function;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;

public final class Discord {

    private static final @NotNull String FOOTER_TEXT = "UltraStaffChat v" + UltraStaffChat.getInstance().getDescription().getVersion();
    private static final @NotNull String FOOTER_ICON_URL = "https://assets.hypera.dev/ultrastaffchat-icon.png";
    private static boolean enabled = false;
    private static WebhookClient webhook;

    public static void setup() {
        Configuration config = UltraStaffChat.getConfig().getConfiguration();
        if (!config.getBoolean("discord-enabled")) {
            return;
        }
        if (config.getString("discord-webhook") == null
            || config.getString("discord-webhook").contains("XXXXXXXXXXXXXXXXXX")) {
            Common.logPrefix(
                "&cError: Discord messages are enabled but the webhook URL has not been configured!");
            return;
        }

        if (config.getSection("discord-format.embed.fields").getKeys().size() > 25
            || config.getSection("discord-join-format.embed.fields").getKeys().size() > 25
            || config.getSection("discord-leave-format.embed.fields").getKeys().size() > 25
            || config.getSection("discord-switch-format.embed.fields").getKeys().size() > 25
            || config.getSection("discord-afk-enable-format.embed.fields").getKeys().size() > 25
            || config.getSection("discord-afk-disable-format.embed.fields").getKeys().size() > 25) {
            Common.logPrefix("&cError: You can have a max of 25 fields in an embed.");
            return;
        }

        webhook = new WebhookClientBuilder(config.getString("discord-webhook")).build();
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
        if (!isEnabled() || !UltraStaffChat.getConfig()
            .getConfiguration()
            .getBoolean("discord-join-messages")) {
            return;
        }
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                if (!UltraStaffChat.getConfig().getBoolean("discord-embed")) {
                    sendTextMessage(join_leavePlaceholders(UltraStaffChat.getConfig().getConfiguration()
                        .getSection("discord-join-format").getString("text"), p));
                    return;
                }

                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration()
                    .getSection("discord-join-format.embed");
                sendEmbedMessage(createEmbed(embedFormat, str -> join_leavePlaceholders(str, p)));
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordLeave(ProxiedPlayer p) {
        if (!isEnabled() || !UltraStaffChat.getConfig()
            .getConfiguration()
            .getBoolean("discord-leave-messages")) {
            return;
        }
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                if (!UltraStaffChat.getConfig().getBoolean("discord-embed")) {
                    sendTextMessage(join_leavePlaceholders(UltraStaffChat.getConfig().getConfiguration()
                        .getSection("discord-leave-format").getString("text"), p));
                    return;
                }

                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration()
                    .getSection("discord-leave-format.embed");
                sendEmbedMessage(createEmbed(embedFormat, str -> join_leavePlaceholders(str, p)));
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordSwitch(ProxiedPlayer p, String from, String to) {
        if (!isEnabled() || !UltraStaffChat.getConfig().getConfiguration()
            .getBoolean("discord-switch-messages")) {
            return;
        }
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                if (!UltraStaffChat.getConfig().getBoolean("discord-embed")) {
                    sendTextMessage(switchPlaceholders(UltraStaffChat.getConfig().getConfiguration()
                        .getSection("discord-switch-format").getString("text"), p, from, to));
                    return;
                }

                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration()
                    .getSection("discord-switch-format.embed");
                sendEmbedMessage(createEmbed(embedFormat, str -> switchPlaceholders(str, p, from, to)));
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordStaffChatMessage(CommandSender s, String message) {
        if (!isEnabled()) {
            return;
        }
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                if (!UltraStaffChat.getConfig().getBoolean("discord-embed")) {
                    sendTextMessage(messagePlaceholders(UltraStaffChat.getConfig().getConfiguration()
                        .getSection("discord-format").getString("text"), s, message));
                    return;
                }

                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration()
                    .getSection("discord-format.embed");
                sendEmbedMessage(createEmbed(embedFormat, str -> messagePlaceholders(str, s, message)));
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordAFKEnable(ProxiedPlayer p) {
        if (!isEnabled() || !UltraStaffChat.getConfig().getConfiguration()
            .getBoolean("discord-afk-enable-messages")) {
            return;
        }
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                if (!UltraStaffChat.getConfig().getBoolean("discord-embed")) {
                    sendTextMessage(afkPlaceholders(UltraStaffChat.getConfig().getConfiguration()
                        .getSection("discord-afk-enable-format").getString("text"), p));
                    return;
                }

                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration()
                    .getSection("discord-afk-enable-format.embed");
                sendEmbedMessage(createEmbed(embedFormat, s -> afkPlaceholders(s, p)));
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    public static void broadcastDiscordAFKDisable(ProxiedPlayer p) {
        if (!isEnabled() || !UltraStaffChat.getConfig().getConfiguration()
            .getBoolean("discord-afk-disable-messages")) {
            return;
        }
        ProxyServer.getInstance().getScheduler().runAsync(UltraStaffChat.getInstance(), () -> {
            try {
                if (!UltraStaffChat.getConfig().getBoolean("discord-embed")) {
                    sendTextMessage(afkPlaceholders(UltraStaffChat.getConfig().getConfiguration()
                        .getSection("discord-afk-disable-format").getString("text"), p));
                    return;
                }

                Configuration embedFormat = UltraStaffChat.getConfig().getConfiguration()
                    .getSection("discord-afk-disable-format.embed");
                sendEmbedMessage(createEmbed(embedFormat, s -> afkPlaceholders(s, p)));
            } catch (Exception e) {
                Common.log(e.toString());
            }
        });
    }

    private static @NotNull WebhookEmbedBuilder createEmbed(@NotNull Configuration format, @NotNull Function<String, String> placeholder) {
        WebhookEmbedBuilder builder = new WebhookEmbedBuilder()
            .setColor(Color.decode(format.getString("color")).getRGB())
            .setDescription(placeholder.apply(format.getString("description")))
            .setTitle(new EmbedTitle(placeholder.apply(format.getString("title")), format.getString("url")))
            .setThumbnailUrl(format.getString("thumbnail"))
            .setImageUrl(format.getString("image"))
            .setAuthor(new EmbedAuthor(
                placeholder.apply(format.getString("author.name")),
                format.getString("author.url"),
                format.getString("image")
            ));
        for (String key : format.getSection("fields").getKeys()) {
            Configuration sec = format.getSection("fields").getSection(key);
            builder.addField(new EmbedField(
                sec.getBoolean("inline"),
                placeholder.apply(sec.getString("name")),
                placeholder.apply(sec.getString("value"))
            ));
        }
        return builder;
    }

    private static void sendTextMessage(@NotNull String message) {
        webhook.send(new WebhookMessageBuilder()
            .setUsername(UltraStaffChat.getConfig().getString("discord-username"))
            .setAvatarUrl(UltraStaffChat.getConfig().getString("discord-image"))
            .setContent(message).build());
    }

    private static void sendEmbedMessage(@NotNull WebhookEmbedBuilder embed) {
        embed.setFooter(new EmbedFooter(FOOTER_TEXT, FOOTER_ICON_URL));
        webhook.send(new WebhookMessageBuilder()
            .setUsername(UltraStaffChat.getConfig().getString("discord-username"))
            .setAvatarUrl(UltraStaffChat.getConfig().getString("discord-image"))
            .addEmbeds(embed.build()).build());
    }

    private static String messagePlaceholders(String string, CommandSender s, String m) {
        return escape(string.replaceAll("\\{message}", stripColor(m))
            .replaceAll("\\{player}", Common.getDisplayNameSafe(s))
            .replaceAll("\\{server}", Common.getServerSafe(s)));
    }

    private static String join_leavePlaceholders(String string, ProxiedPlayer p) {
        return escape(string.replaceAll("\\{player}", Common.getDisplayNameSafe(p))
            .replaceAll("\\{server}", Common.getServerSafe(p)));
    }

    private static String switchPlaceholders(String string, ProxiedPlayer p, String from, String to) {
        return escape(string.replaceAll("\\{player}", Common.getDisplayNameSafe(p))
            .replaceAll("\\{to}", to)
            .replaceAll("\\{from}", from));
    }

    private static String afkPlaceholders(String string, ProxiedPlayer p) {
        return escape(string.replaceAll("\\{player}", Common.getDisplayNameSafe(p)));
    }

    private static String escape(String input) {
        return input.replaceAll("\"", "\\\\\"");
    }

    private static String stripColor(String input) {
        return input.replaceAll("(?i)[§&][0-9A-FK-ORX]", "");
    }

}
