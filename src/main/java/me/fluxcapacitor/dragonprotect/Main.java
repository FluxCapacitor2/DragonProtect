package me.fluxcapacitor.dragonprotect;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class Main extends JavaPlugin implements Listener {
    private FileConfiguration config = this.getConfig();
    private WorldGuardPlugin wg;

    private StringFlag BlockBreakPermissionFlag = new StringFlag("block-break-permission");
    private StringFlag BlockPlacePermissionFlag = new StringFlag("block-place-permission");

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        handleEvent(event, BlockBreakPermissionFlag);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        handleEvent(event, BlockPlacePermissionFlag);
    }

    private void debugLog(String... msg) {
        if (config.getBoolean("debug")) Utils.log(msg);
    }

    @Override
    public void onEnable() {
        config.addDefault("debug", false);
        config.options().copyDefaults(true);
        this.saveConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        Utils.log("&aPlugin loaded!");
    }

    @Override
    public void onLoad() {
        wg = getWG();
        FlagRegistry registry = null;
        try {
            registry = wg.getFlagRegistry();
        } catch (NullPointerException e) {
            Utils.log("&4Could not get flag registry from WorldGuard. Make sure WorldGuard is installed and working.");
            Utils.log("&4You must have WorldGuard version 6.2 or higher for custom flags to be registered.");
        }
        if (registry != null) {
            registry.register(BlockBreakPermissionFlag);
            registry.register(BlockPlacePermissionFlag);
        }
    }

    private WorldGuardPlugin getWG() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
        if (!(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
    /*
    ==============================
    EVENT HANDLERS
    ==============================
     */

    //BLOCK BREAK
    public void handleEvent(BlockBreakEvent event, StringFlag flag) {
        debugLog(event.getEventName() + " handler method called");
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Iterator<ProtectedRegion> iterator = wg.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(block.getLocation()).getRegions().iterator();
        boolean cancelEvent = false;

        while (iterator.hasNext()) {
            ProtectedRegion value = iterator.next();
            if (value.getFlag(flag) != null && !player.hasPermission(value.getFlag(flag))) {
                debugLog(event.getEventName() + " will be cancelled because " + player.getName() + " lacks permission " + value.getFlag(flag));
                cancelEvent = true;
            } else {
                debugLog("User has permission required: " + value.getFlag(flag));
            }
        }
        if (cancelEvent) {
            event.setCancelled(true);
            debugLog(event.getEventName() + " Cancelled " + event.getEventName() + " at " + new Coordinate(block).toString() + " for player " + player.getName() + " because they lacked permissions.");
        }
        debugLog(event.getEventName() + " will be allowed because " + player.getName() + " has all required permission(s).");
    }

    //BLOCK PLACE
    public void handleEvent(BlockPlaceEvent event, StringFlag flag) {
        debugLog(event.getEventName() + " handler method called");
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Iterator<ProtectedRegion> iterator = wg.getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(block.getLocation()).getRegions().iterator();
        boolean cancelEvent = false;

        while (iterator.hasNext()) {
            ProtectedRegion value = iterator.next();
            if (value.getFlag(flag) != null && !player.hasPermission(value.getFlag(flag))) {
                debugLog(event.getEventName() + " will be cancelled because " + player.getName() + " lacks permission " + value.getFlag(flag));
                cancelEvent = true;
            } else {
                debugLog("User has permission required: " + value.getFlag(flag));
            }
        }
        if (cancelEvent) {
            event.setCancelled(true);
            debugLog(event.getEventName() + " Cancelled " + event.getEventName() + " at " + new Coordinate(block).toString() + " for player " + player.getName() + " because they lacked permissions.");
        }
        debugLog(event.getEventName() + " will be allowed because " + player.getName() + " has all required permission(s).");
    }
}