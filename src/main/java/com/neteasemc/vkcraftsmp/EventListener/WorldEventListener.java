package com.neteasemc.vkcraftsmp.EventListener;

import com.neteasemc.vkcraftsmp.Manager.PermissionManager;
import com.neteasemc.vkcraftsmp.VKCraftSMP;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.entity.Player;

public class WorldEventListener implements Listener {

    public VKCraftSMP plugin;
    private PermissionManager permissionManager;

    public WorldEventListener(VKCraftSMP plugin, PermissionManager permissionManager) {
        this.plugin = plugin;
        this.permissionManager = permissionManager;
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        String playerWorldName = player.getName() + "_world";

        if (worldName.equals(playerWorldName)) {
            permissionManager.setPlayerWorldPermissions(player, worldName);
        } else {
            permissionManager.clearPlayerPermissions(player);
        }
    }
}
