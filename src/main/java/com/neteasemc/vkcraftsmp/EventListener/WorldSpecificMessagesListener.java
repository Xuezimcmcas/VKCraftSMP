package com.neteasemc.vkcraftsmp.EventListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class WorldSpecificMessagesListener implements Listener {

    @EventHandler
    public void onPlayerAchievement(PlayerAchievementAwardedEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String worldName = player.getWorld().getName();
        String achievementName = event.getAchievement().name();

        // 取消成就解锁的默认广播
        event.setCancelled(true);

        // 向同一世界中的所有玩家发送成就解锁消息
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld().getName().equals(worldName)) {
                p.sendMessage(ChatColor.GREEN + playerName + "解锁了成就" + achievementName);
            }
        }
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {

    }
}
