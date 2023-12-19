package com.neteasemc.vkcraftsmp.EventListener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class WorldChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();

        event.getRecipients().removeIf(recipient -> !recipient.getWorld().getName().equals(worldName));
    }
}
