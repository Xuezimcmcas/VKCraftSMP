package com.neteasemc.vkcraftsmp.CommandExecutor;

import com.neteasemc.vkcraftsmp.Manager.InvitationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RejectCommandExecutor implements CommandExecutor {

    private InvitationManager invitationManager;

    public RejectCommandExecutor(InvitationManager invitationManager) {
        this.invitationManager = invitationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以拒绝邀请。");
            return true;
        }

        Player invitee = (Player) sender;
        Player inviter = invitationManager.getInviter(invitee.getUniqueId());

        if (inviter == null) {
            invitee.sendMessage("你没有待处理的邀请。");
            return true;
        }

        invitationManager.removeInvitation(invitee.getUniqueId());
        inviter.sendMessage(invitee.getName() + " 拒绝了你的邀请。");
        return true;
    }
}
