package com.neteasemc.vkcraftsmp.CommandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHelper {

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "VKCraftSMP 帮助:");
        sender.sendMessage(ChatColor.YELLOW + "/smp create" + ChatColor.WHITE + " - 创建你的个人世界。");
        sender.sendMessage(ChatColor.YELLOW + "/smp back" + ChatColor.WHITE + " - 返回到你的个人世界。");
        sender.sendMessage(ChatColor.YELLOW + "/邀请 [玩家名称]" + ChatColor.WHITE + " - 邀请一个玩家到你的个人世界。");
        sender.sendMessage(ChatColor.YELLOW + "/接受" + ChatColor.WHITE + " - 接受一个邀请。");
        sender.sendMessage(ChatColor.YELLOW + "/拒绝" + ChatColor.WHITE + " - 拒绝一个邀请。");
    }

    public static void showDetailedHelp(CommandSender sender, String command) {
        switch (command.toLowerCase()) {
            case "create":
                sender.sendMessage(ChatColor.YELLOW + "/smp create" + ChatColor.WHITE + " - 创建你的个人世界。");
                break;
            case "邀请":
                sender.sendMessage(ChatColor.YELLOW + "/邀请 [玩家名称]" + ChatColor.WHITE + " - 邀请指定玩家到你的个人世界。");
                break;
            case "接受":
                sender.sendMessage(ChatColor.YELLOW + "/接受" + ChatColor.WHITE + " - 接受来自其他玩家的邀请。");
                break;
            case "拒绝":
                sender.sendMessage(ChatColor.YELLOW + "/拒绝" + ChatColor.WHITE + " - 拒绝来自其他玩家的邀请。");
                break;
            case "back":
                sender.sendMessage(ChatColor.YELLOW + "/smp back" + ChatColor.WHITE + " - 返回到你的个人世界。");
                break;
            // 可以为其他子命令添加更多的情况
            default:
                sender.sendMessage(ChatColor.RED + "未知命令。使用 /smp help 获取帮助。");
                break;
        }
    }
}
