package com.neteasemc.vkcraftsmp.Manager;

import com.neteasemc.vkcraftsmp.VKCraftSMP;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIManager implements Listener {

    private VKCraftSMP plugin;

    public GUIManager(VKCraftSMP plugin) {
        this.plugin = plugin;
    }

    public void openSettingsGUI(Player player) {

        if (!hasPersonalWorld(player)) {
            player.sendMessage(ChatColor.RED + "你没有个人世界！");
            return;
        }

        // 检查玩家是否在自己的个人世界中
        if (!isInPersonalWorld(player)) {
            player.sendMessage(ChatColor.RED + "你不在自己的个人世界中！");
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 9, "个人世界设置");

        // 创建各种设置选项
        inv.setItem(0, createItem(Material.TOTEM, "死亡掉落", "是否开启死亡掉落"));
        inv.setItem(1, createItem(Material.FLINT_AND_STEEL, "火焰蔓延", "是否允许火焰蔓延"));
        inv.setItem(2, createItem(Material.BED, "昼夜更替", "是否允许昼夜更替"));
        inv.setItem(3, createItem(Material.WATER_BUCKET, "天气变化", "是否允许天气变化"));
        inv.setItem(4, createItem(Material.TNT, "生物破坏", "是否允许苦力怕、恶魂等生物破坏方块"));
        inv.setItem(5, createItem(Material.IRON_SWORD, "玩家PVP", "是否允许玩家互相攻击"));

        player.openInventory(inv);
    }

    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("个人世界设置")) {
            event.setCancelled(true); // 防止物品被拖动

            Player player = (Player) event.getWhoClicked();
            int slot = event.getRawSlot();

            switch (slot) {
                case 0:
                    // 处理死亡掉落设置
                    break;
                case 1:
                    // 处理火焰蔓延设置
                    break;
                // 其他选项...
                default:
                    player.sendMessage("未知选项");
                    break;
            }
        }
    }


    private boolean hasPersonalWorld(Player player) {
        MultiverseCore multiverse = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverse != null) {
            MultiverseWorld mvWorld = multiverse.getMVWorldManager().getMVWorld(player.getName() + "_world");
            return mvWorld != null;
        }
        return false;
    }

    private boolean isInPersonalWorld(Player player) {
        MultiverseCore multiverse = (MultiverseCore) plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverse != null) {
            String worldName = player.getWorld().getName();
            return worldName.equals(player.getName() + "_world");
        }
        return false;
    }
}
