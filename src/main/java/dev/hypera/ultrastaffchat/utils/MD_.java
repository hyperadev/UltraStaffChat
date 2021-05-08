/*
 * MIT License
 *
 * Copyright (c) 2018 Aeternum Network
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.hypera.ultrastaffchat.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MD_ implements Listener {

    public static String parseMarkdown(String message) {
        String translated = message;

        translated = replaceWith(translated, "(?<!\\\\)\\*\\*", ChatColor.COLOR_CHAR + "z", ChatColor.COLOR_CHAR + "Z");
        translated = replaceWith(translated, "(?<!\\\\)\\*", ChatColor.COLOR_CHAR + "x", ChatColor.COLOR_CHAR + "X");
        translated = replaceWith(translated, "(?<!\\\\)__", ChatColor.COLOR_CHAR + "v", ChatColor.COLOR_CHAR + "V");
        translated = replaceWith(translated, "(?<!\\\\)_", ChatColor.COLOR_CHAR + "q", ChatColor.COLOR_CHAR + "Q");
        translated = replaceWith(translated, "(?<!\\\\)~~", ChatColor.COLOR_CHAR + "m", ChatColor.COLOR_CHAR + "M");
        translated = replaceWith(translated, "(?<!\\\\)~", ChatColor.COLOR_CHAR + "w", ChatColor.COLOR_CHAR + "W");

        translated = translated.replace("\\*", "*").replace("\\_", "_").replace("\\~", "~");

        return parseParts(translated).toString();
    }

    private static StringBuilder parseParts(String translated) {
        String partForPart = (" " + translated);
        String[] parts = partForPart.split("" + ChatColor.COLOR_CHAR);
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            char colorCharacter = part.charAt(0);
            ChatColor color = ChatColor.getByChar(colorCharacter);

            String colors = getLastColors(builder.toString());
            if (color != null) {
                StringBuilder colorBuilder = new StringBuilder();
                for (String cc : colors.split(ChatColor.COLOR_CHAR + "")) {
                    if (cc.isEmpty()) {
                        continue;
                    }
                    if (isFormat(ChatColor.getByChar(cc.charAt(0)))) {
                        colorBuilder.append(ChatColor.COLOR_CHAR + cc);
                    }
                }
                builder.append(color + colorBuilder.toString());
            } else {
                if (colorCharacter == 'z') builder.append(ChatColor.BOLD);
                else if (colorCharacter == 'x') builder.append(ChatColor.ITALIC);
                else if (colorCharacter == 'v') builder.append(ChatColor.UNDERLINE);
                else if (colorCharacter == 'q') builder.append(ChatColor.ITALIC);
                else if (colorCharacter == 'm') builder.append(ChatColor.STRIKETHROUGH);
                else if (colorCharacter == 'w') builder.append(ChatColor.MAGIC);
                else if (colorCharacter == 'Z') colors = colors.replace(ChatColor.BOLD.toString(), "");
                else if (colorCharacter == 'X') colors = colors.replace(ChatColor.ITALIC.toString(), "");
                else if (colorCharacter == 'V') colors = colors.replace(ChatColor.UNDERLINE.toString(), "");
                else if (colorCharacter == 'Q') colors = colors.replace(ChatColor.ITALIC.toString(), "");
                else if (colorCharacter == 'M') colors = colors.replace(ChatColor.STRIKETHROUGH.toString(), "");
                else if (colorCharacter == 'W') colors = colors.replace(ChatColor.MAGIC.toString(), "");
                if (Character.isUpperCase(colorCharacter)) builder.append(ChatColor.RESET + colors);
            }
            if (part.length() > 1) {
                builder.append(part.substring(1));
            }
        }
        return builder;
    }

    private static String replaceWith(String message, String quot, String pre, String suf) {
        String part = message;
        for (String str : getMatches(message, quot + "(.+?)" + quot)) {
            part = part.replaceFirst(quot + Pattern.quote(str) + quot, pre + str + suf);
        }
        return part;
    }

    public static List<String> getMatches(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        List<String> matches = new ArrayList<String>();
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        return matches;
    }

    private static boolean isFormat(ChatColor color) {
        return color == ChatColor.MAGIC || color == ChatColor.BOLD || color == ChatColor.STRIKETHROUGH || color == ChatColor.ITALIC || color == ChatColor.UNDERLINE;
    }

    private static String getLastColors(String input) {
        String result = "";
        int length = input.length();

        // Search backwards from the end as it is faster
        for (int index = length - 1; index > -1; index--) {
            char section = input.charAt(index);
            if (section == ChatColor.COLOR_CHAR && index < length - 1) {
                char c = input.charAt(index + 1);
                ChatColor color = ChatColor.getByChar(c);

                if (color != null) {
                    result = color.toString() + result;

                    // Once we find a color or reset we can stop searching
                    if (!isFormat(color) || color.equals(ChatColor.RESET)) {
                        break;
                    }
                }
            }
        }

        return result;
    }

}
