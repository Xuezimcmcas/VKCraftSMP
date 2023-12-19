package com.neteasemc.vkcraftsmp.CommandExecutor;

import com.neteasemc.vkcraftsmp.Manager.InvitationManager;
import com.neteasemc.vkcraftsmp.VKCraftSMP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InviteCommandExecutor implements CommandExecutor {

    private final VKCraftSMP plugin;
    private InvitationManager invitationManager;

    public InviteCommandExecutor(VKCraftSMP plugin, InvitationManager invitationManager) {
        this.plugin = plugin;
        this.invitationManager = invitationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以发送邀请。");
            return true;
        }

        Player inviter = (Player) sender;
        if (args.length != 1) {
            sender.sendMessage("用法: /邀请 [玩家名称]");
            return true;
        }

        Player invitee = plugin.getServer().getPlayer(args[0]);
        if (invitee == null) {
            inviter.sendMessage("玩家未找到。");
            return true;
        }

        if (inviter.equals(invitee)) {
            inviter.sendTitle(ChatColor.RED + "你不能邀请自己", "", 10, 70, 20);
            return true;
        }

        if (!isInPersonalWorld(inviter)) {
            inviter.sendMessage(ChatColor.RED + "你必须在自己的个人世界中才能发送邀请。");
            return true;
        }

        invitationManager.createInvitation(inviter.getUniqueId(), invitee.getUniqueId());
        inviter.sendMessage("已向 " + invitee.getName() + " 发送邀请，等待回应...");
        invitee.sendMessage(ChatColor.GREEN + inviter.getName() + " 邀请你加入他们的个人世界。使用 /接受 或 /拒绝 来响应。");

        inviter.sendTitle("等待受理中", "", 10, 70, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (invitationManager.getInvitation(inviter.getUniqueId()) != null) {
                    inviter.sendMessage(ChatColor.RED + "邀请已超时。");
                    invitationManager.removeInvitation(inviter.getUniqueId());
                }
            }
        }.runTaskLater(plugin, 20 * 10);

        return true;
    }

    private boolean isInPersonalWorld(Player player) {
        String worldName = player.getWorld().getName();
        return worldName.equals(player.getName() + "_world");
    }
}
