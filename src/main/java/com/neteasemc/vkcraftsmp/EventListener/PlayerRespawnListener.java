package com.neteasemc.vkcraftsmp.EventListener;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class PlayerRespawnListener implements Listener {

    private Plugin plugin;

    public PlayerRespawnListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        MultiverseCore multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore != null) {
            MVWorldManager worldManager = multiverseCore.getMVWorldManager();
            String playerWorldName = event.getPlayer().getName() + "_world";
            if (worldManager.isMVWorld(playerWorldName)) {
                World playerWorld = Bukkit.getServer().getWorld(playerWorldName);
                if (playerWorld != null) {
                    event.setRespawnLocation(playerWorld.getSpawnLocation());
                }
            }
        }
    }
}
