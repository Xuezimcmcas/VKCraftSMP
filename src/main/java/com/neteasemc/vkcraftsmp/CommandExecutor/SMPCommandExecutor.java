package com.neteasemc.vkcraftsmp.CommandExecutor;

import com.neteasemc.vkcraftsmp.Manager.GUIManager;
import com.neteasemc.vkcraftsmp.Manager.PermissionManager;
import com.neteasemc.vkcraftsmp.VKCraftSMP;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

public class SMPCommandExecutor implements CommandExecutor {

    private VKCraftSMP plugin;
    private PermissionManager permissionManager;
    private GUIManager guiManager;

    // 构造函数中添加 PermissionManager 参数
    public SMPCommandExecutor(VKCraftSMP plugin, PermissionManager permissionManager, GUIManager guiManager) {
        this.plugin = plugin;
        this.permissionManager = permissionManager;
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以使用这个命令。");
            return true;
        }
            Player player = (Player) sender;
        if (args.length > 0) {
            if ("create".equalsIgnoreCase(args[0])) {
                // 检查玩家是否有创建世界的权限
                if (player.hasPermission("vkcraftsmp.create")) {
                    handleWorldCreation(player);
                } else {
                    player.sendMessage(ChatColor.RED + "你没有权限使用这个命令。");
                }
                return true;
            }else if ("back".equalsIgnoreCase(args[0])) {
                return handleWorldBack(player);
            } else if ("settings".equalsIgnoreCase(args[0])) {
                guiManager.openSettingsGUI(player);
                return true;
            }
        }
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                // 显示帮助信息
                CommandHelper.showHelp(sender);
                return true;
            } else if (args.length > 1 && args[0].equalsIgnoreCase("help")) {
                // 显示特定子命令的详细帮助
                CommandHelper.showDetailedHelp(sender, args[1]);
                return true;
            }
        return false;
    }

    private void handleWorldCreation(Player player) {
        // 显示审核中的标题
        player.sendTitle(ChatColor.GOLD + "审核中", ChatColor.GRAY + "请稍等...", 10, 70, 20);

        // 延迟10秒后执行
        new BukkitRunnable() {
            @Override
            public void run() {
                // 显示审核成功的标题
                player.sendTitle(ChatColor.GREEN + "审核成功", "", 10, 70, 20);

                // 延迟3秒后执行
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        // 显示创建中的标题
                        player.sendTitle(ChatColor.AQUA + "创建中", "", 10, 70, 20);

                        // 创建世界并设置权限
                        createWorld(player, player.getName() + "_world");
                    }
                }.runTaskLater(plugin, 20 * 3); // 20 ticks * 3 seconds
            }
        }.runTaskLater(plugin, 20 * 10); // 20 ticks * 10 seconds
    }

    private void createWorld(Player player, String worldName) {
        MultiverseCore multiverseCore = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore != null) {
            MVWorldManager worldManager = multiverseCore.getMVWorldManager();
            if (worldManager.isMVWorld(worldName)) {
                player.sendMessage(ChatColor.RED + "You already have a world.");
                return;
            }

            WorldCreator worldCreator = new WorldCreator(worldName);
            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.type(WorldType.NORMAL);

            boolean worldCreated = worldManager.addWorld(
                    worldName,
                    worldCreator.environment(),
                    "",
                    worldCreator.type(),
                    false,
                    "VoidGenerator",
                    false
            );

            if (worldCreated) {
                World world = plugin.getServer().getWorld(worldName);
                if (world != null) {
                    player.sendMessage(ChatColor.GREEN + "创建完毕!");
                    player.teleport(world.getSpawnLocation());

                    // 在世界创建后设置玩家权限
                    permissionManager.setPlayerWorldPermissions(player, worldName);
                } else {
                    player.sendMessage(ChatColor.RED + "There was an error finding your world after creation.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "There was an error creating your world.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Multiverse-Core not found!");
        }
    }

    private boolean handleWorldBack(Player player) {
        MultiverseCore multiverseCore = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore != null) {
            MVWorldManager worldManager = multiverseCore.getMVWorldManager();
            String playerWorld = player.getName() + "_world";
            if (worldManager.isMVWorld(playerWorld)) {
                World world = plugin.getServer().getWorld(playerWorld);
                if (world != null) {
                    player.teleport(world.getSpawnLocation());
                    return true;
                }
            }
        }

        player.sendMessage(ChatColor.RED + "你还没有个人世界，输入:/smp create来创建。");
        player.sendTitle(ChatColor.RED + "错误", ChatColor.GRAY + "你没有个人世界", 10, 70, 20);
        return true;
    }
}
