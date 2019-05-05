package me.fluxcapacitor.dragonprotect;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import static org.bukkit.Bukkit.getServer;

public class Utils {
    public static ConsoleCommandSender consoleSender = getServer().getConsoleSender();
    static String prefix = "&3&lDragonProtect&r >> &6";

    public static void log(String... msg) {
        String msgString = color(prefix);
        for (int i = 0; i < msg.length; i++) {
            msgString += color(msg[i]);
        }
        consoleSender.sendMessage(msgString);
    }

    public static String color(String original) {
        return original.replaceAll("&", ChatColor.COLOR_CHAR + "");
    }
}
