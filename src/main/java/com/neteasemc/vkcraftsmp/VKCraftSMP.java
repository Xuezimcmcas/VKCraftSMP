package com.neteasemc.vkcraftsmp;

import com.neteasemc.vkcraftsmp.CommandExecutor.AcceptCommandExecutor;
import com.neteasemc.vkcraftsmp.CommandExecutor.InviteCommandExecutor;
import com.neteasemc.vkcraftsmp.CommandExecutor.RejectCommandExecutor;
import com.neteasemc.vkcraftsmp.CommandExecutor.SMPCommandExecutor;
import com.neteasemc.vkcraftsmp.EventListener.*;
import com.neteasemc.vkcraftsmp.Manager.GUIManager;
import com.neteasemc.vkcraftsmp.Manager.InvitationManager;
import com.neteasemc.vkcraftsmp.Manager.PermissionManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.entity.Player;

public class VKCraftSMP extends JavaPlugin implements Listener {

    private PermissionManager permissionManager;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        InvitationManager invitationManager = new InvitationManager();
        // 初始化 PermissionManager
        permissionManager = new PermissionManager(this);
        guiManager = new GUIManager(this);
        getServer().getPluginManager().registerEvents(guiManager, this);

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new WorldEventListener(this, permissionManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(invitationManager), this);
        getServer().getPluginManager().registerEvents(new WorldSpecificMessagesListener(), this);
        getServer().getPluginManager().registerEvents(new WorldChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
        // 注册命令
        this.getCommand("邀请").setExecutor(new InviteCommandExecutor(this, invitationManager));
        this.getCommand("接受").setExecutor(new AcceptCommandExecutor(invitationManager));
        this.getCommand("拒绝").setExecutor(new RejectCommandExecutor(invitationManager));
        this.getCommand("smp").setExecutor(new SMPCommandExecutor(this, permissionManager, guiManager));

        getLogger().info("VKCraftSMP has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("VKCraftSMP has been disabled.");
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        permissionManager.setPlayerWorldPermissions(player, player.getWorld().getName());
        permissionManager.handlePlayerLeaveWorld(player);
    }
}
