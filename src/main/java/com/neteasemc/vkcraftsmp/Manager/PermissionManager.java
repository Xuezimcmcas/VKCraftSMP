package com.neteasemc.vkcraftsmp.Manager;

import com.neteasemc.vkcraftsmp.VKCraftSMP;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;

public class PermissionManager {

    private VKCraftSMP plugin;
    private Map<Player, PermissionAttachment> playerPermissions;

    public PermissionManager(VKCraftSMP plugin) {
        this.plugin = plugin;
        this.playerPermissions = new HashMap<>();
    }

    public void setPlayerWorldPermissions(Player player, String worldName) {
        clearPlayerPermissions(player);
        if (player.getWorld().getName().equals(player.getName() + "_world")) {
            PermissionAttachment attachment = player.addAttachment(plugin);
            attachment.setPermission("minecraft.command.gamemode", true);
            attachment.setPermission("minecraft.command.tp", true);
            attachment.setPermission("minecraft.command.effect", true);
            attachment.setPermission("minecraft.command.fly", true);
            attachment.setPermission("minecraft.command.time", true);
            playerPermissions.put(player, attachment);
        }
    }

    public void clearPlayerPermissions(Player player) {
        if (playerPermissions.containsKey(player)) {
            player.removeAttachment(playerPermissions.get(player));
            playerPermissions.remove(player);
        }
    }

    public void handlePlayerLeaveWorld(Player player) {
        if (!player.getWorld().getName().equals(player.getName() + "_world")) {
            clearPlayerPermissions(player);
        }
    }
}
