package com.neteasemc.vkcraftsmp.CommandExecutor;
import com.neteasemc.vkcraftsmp.Manager.InvitationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class AcceptCommandExecutor implements CommandExecutor {

    private InvitationManager invitationManager;

    public AcceptCommandExecutor(InvitationManager invitationManager) {
        this.invitationManager = invitationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以接受邀请。");
            return true;
        }

        Player invitee = (Player) sender;
        Player inviter = invitationManager.getInviter(invitee.getUniqueId());

        if (inviter == null) {
            invitee.sendMessage("你没有待处理的邀请。");
            return true;
        }

        // 实现将 invitee 传送到 inviter 的世界
        invitee.teleport(inviter.getLocation());
        invitationManager.removeInvitation(invitee.getUniqueId());
        inviter.sendMessage(invitee.getName() + " 接受了你的邀请。");
        return true;
    }
}
