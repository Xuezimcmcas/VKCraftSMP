package com.neteasemc.vkcraftsmp.EventListener;

import com.neteasemc.vkcraftsmp.Manager.InvitationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    private InvitationManager invitationManager;

    public PlayerJoinQuitListener(InvitationManager invitationManager) {
        this.invitationManager = invitationManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();

        // 取消默认的加入消息
        event.setJoinMessage(null);

        // 发送消息给被邀请玩家
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getWorld().getName().equals(joinedPlayer.getName() + "_world") &&
                    invitationManager.isPlayerInvitedTo(joinedPlayer.getUniqueId(), player.getUniqueId())) {
                player.sendMessage(ChatColor.YELLOW + joinedPlayer.getName() + " 加入了服务器");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player quittedPlayer = event.getPlayer();

        // 取消默认的退出消息
        event.setQuitMessage(null);

        // 发送消息给被邀请玩家
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getWorld().getName().equals(quittedPlayer.getName() + "_world") &&
                    invitationManager.isPlayerInvitedTo(quittedPlayer.getUniqueId(), player.getUniqueId())) {
                player.sendMessage(ChatColor.YELLOW + quittedPlayer.getName() + " 退出了服务器");
            }
        }
    }
}
