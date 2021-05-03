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

package dev.hypera.ultrastaffchat.commands.impl;

import dev.hypera.ultrastaffchat.UltraStaffChat;
import dev.hypera.ultrastaffchat.commands.Command;
import dev.hypera.ultrastaffchat.utils.Common;
import dev.hypera.ultrastaffchat.utils.Discord;
import dev.hypera.ultrastaffchat.utils.PasteUtils;
import dev.hypera.ultrastaffchat.utils.TimeUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.CommandSender;

import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.time.Instant;

import static dev.hypera.ultrastaffchat.managers.DebugManager.generateAdvancedDebugLog;
import static dev.hypera.ultrastaffchat.managers.DebugManager.generateDebugLog;

public class UltraStaffChatCommand extends Command {

    public UltraStaffChatCommand() {
        super("ultrastaffchat", null, "usc");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Audience audience = UltraStaffChat.getInstance().getAdventure().sender(sender);

        if(args.length > 0) {
            if (args[0].matches("(?i:(ab(t|out)|credit(s)?|contibutor(s)?))")) {
                audience.sendMessage(Component.text()
                        .append(
                                Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD).clickEvent(ClickEvent.openUrl("https://www.spigotmc.org/resources/" + Common.getResourceId() + "/")),
                                Component.text().content(" Version ").color(NamedTextColor.GRAY).build(),
                                Component.text().content(UltraStaffChat.getInstance().getDescription().getVersion()).color(NamedTextColor.RED).build(),
                                Component.text().content(" was created by:\n").color(NamedTextColor.GRAY).build(),
                                LegacyComponentSerializer.legacyAmpersand().deserialize("&7 - &c" + String.join("\n &7- &c", Common.getContributors()))
                        )
                );
                return;
            }

            if (args[0].matches("(?i:(r(eload)?))")) {
                if (!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-reload"))) {audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission"))); return;}
                audience.sendMessage(Component.text()
                        .append(
                                Component.text().content("[").color(NamedTextColor.GRAY),
                                Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                                Component.text().content("] ").color(NamedTextColor.GRAY),
                                Component.text().content("Reloading configuration files...").color(NamedTextColor.GREEN)
                        )
                );
                UltraStaffChat.getConfig().reload();
                Discord.reload();
                audience.sendMessage(Component.text()
                        .append(
                                Component.text().content("[").color(NamedTextColor.GRAY),
                                Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                                Component.text().content("] ").color(NamedTextColor.GRAY),
                                Component.text().content("Successfully reloaded configuration files").color(NamedTextColor.GREEN)
                        )
                );
                return;
            }

            if (args[0].matches("(?i:(debug))")) {
                if (!sender.hasPermission(UltraStaffChat.getConfig().getString("permission-debug"))) {audience.sendMessage(Common.adventurise(UltraStaffChat.getConfig().getString("no-permission"))); return;}
                String log;
                String filename = "USC-" + TimeUtils.formatFileDateTime(Date.from(Instant.now())) + "-Debug.txt";;
                boolean advanced = false;
                if (args.length > 1) {
                    for (String arg : args) {
                        if (arg.matches("(?i:(--a(dv(anced)?)?))")) {
                            advanced = true;
                            break;
                        }
                    }
                }

                if (advanced) {
                    audience.sendMessage(Component.text().append(
                            Component.text().content("[").color(NamedTextColor.GRAY),
                            Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                            Component.text().content("] ").color(NamedTextColor.GRAY),
                            Component.text().content("Generating advanced debug log...").color(NamedTextColor.WHITE)
                    ));
                    log = generateAdvancedDebugLog();
                } else {
                    audience.sendMessage(Component.text().append(
                            Component.text().content("[").color(NamedTextColor.GRAY),
                            Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                            Component.text().content("] ").color(NamedTextColor.GRAY),
                            Component.text().content("Generating debug log...").color(NamedTextColor.WHITE)
                    ));
                    log = generateDebugLog();
                }

                try {
                    File logFolder = new File(UltraStaffChat.getInstance().getDataFolder() + "/logs");
                    if (!logFolder.exists()) logFolder.mkdirs();

                    File logFile = new File(logFolder + "/" + filename);
                    if (logFile.createNewFile()) {
                        FileWriter logWriter = new FileWriter(logFile);
                        logWriter.write(log);
                        logWriter.close();
                    } else throw new Exception();

                    String pasteURL = PasteUtils.createPaste(log);
                    if (pasteURL == null) throw new Exception();
                    if (advanced) {
                        audience.sendMessage(Component.text().append(
                                Component.text().content("[").color(NamedTextColor.GRAY),
                                Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                                Component.text().content("] ").color(NamedTextColor.GRAY),
                                Component.text().content("Successfully generated debug log ").color(NamedTextColor.GREEN),
                                Component.text().content("[View]").color(NamedTextColor.GRAY).clickEvent(ClickEvent.openUrl(pasteURL)).hoverEvent(HoverEvent.showText(Component.text().content(pasteURL)))
                        ));
                    } else {
                        audience.sendMessage(Component.text().append(
                                LegacyComponentSerializer.legacy('&').deserialize(log).clickEvent(ClickEvent.openUrl(pasteURL))
                        ));
                    }
                } catch (Exception e) {
                    Common.logPrefix("Failed to generate debug log!");
                    audience.sendMessage(Component.text().append(
                            Component.text().content("[").color(NamedTextColor.GRAY),
                            Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD),
                            Component.text().content("] ").color(NamedTextColor.GRAY),
                            Component.text().content("Failed to generate debug log").color(NamedTextColor.RED)
                    ));
                    return;
                }
                return;
            }
        }

        audience.sendMessage(Component.text()
                .append(
                        Component.text().content("UltraStaffChat").color(NamedTextColor.RED).decorate(TextDecoration.BOLD).clickEvent(ClickEvent.openUrl("https://www.spigotmc.org/resources/" + Common.getResourceId() + "/")),
                        Component.text().content(" Version ").color(NamedTextColor.GRAY).build(),
                        Component.text().content(UltraStaffChat.getInstance().getDescription().getVersion()).color(NamedTextColor.RED).build(),
                        Component.text().content("\nSupport").color(NamedTextColor.RED).build(),
                        Component.text().content(": ").color(NamedTextColor.GRAY).build(),
                        Component.text().content("https://discord.hypera.dev").color(NamedTextColor.AQUA).clickEvent(ClickEvent.openUrl("https://discord.hypera.dev")).build()
                )
        );
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
