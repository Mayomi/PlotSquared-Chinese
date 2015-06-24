////////////////////////////////////////////////////////////////////////////////////////////////////
// PlotSquared - A plot manager and world generator for the Bukkit API                             /
// Copyright (c) 2014 IntellectualSites/IntellectualCrafters                                       /
//                                                                                                 /
// This program is free software; you can redistribute it and/or modify                            /
// it under the terms of the GNU General Public License as published by                            /
// the Free Software Foundation; either version 3 of the License, or                               /
// (at your option) any later version.                                                             /
//                                                                                                 /
// This program is distributed in the hope that it will be useful,                                 /
// but WITHOUT ANY WARRANTY; without even the implied warranty of                                  /
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                   /
// GNU General Public License for more details.                                                    /
//                                                                                                 /
// You should have received a copy of the GNU General Public License                               /
// along with this program; if not, write to the Free Software Foundation,                         /
// Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA                               /
//                                                                                                 /
// You can contact us via: support@intellectualsites.com                                           /
////////////////////////////////////////////////////////////////////////////////////////////////////
package com.intellectualcrafters.plot.commands;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.config.Settings;
import com.intellectualcrafters.plot.database.AbstractDB;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.object.OfflinePlotPlayer;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.object.StringWrapper;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.PlayerManager;
import com.intellectualcrafters.plot.util.TaskManager;
import com.intellectualcrafters.plot.util.bukkit.UUIDHandler;
import com.intellectualcrafters.plot.uuid.DefaultUUIDWrapper;
import com.intellectualcrafters.plot.uuid.LowerOfflineUUIDWrapper;
import com.intellectualcrafters.plot.uuid.OfflineUUIDWrapper;
import com.intellectualcrafters.plot.uuid.UUIDWrapper;

public class DebugUUID extends SubCommand {
    public DebugUUID() {
        super("uuidconvert", "plots.admin", "UUID转换调试", "debuguuid", "debuguuid", CommandCategory.DEBUG, false);
    }

    @Override
    public boolean execute(final PlotPlayer player, final String... args) {
        if (player != null) {
            MainUtil.sendMessage(player, C.NOT_CONSOLE);
            return false;
        }
        if (args.length == 0) {
            MainUtil.sendMessage(player, C.COMMAND_SYNTAX, "/plot uuidconvert <lower|offline|online>");
            return false;
        }
        
        UUIDWrapper currentUUIDWrapper = UUIDHandler.uuidWrapper;
        UUIDWrapper newWrapper = null;
        
        switch (args[0].toLowerCase()) {
            case "lower": {
                newWrapper = new LowerOfflineUUIDWrapper();
                break;
            }
            case "offline": {
                newWrapper = new OfflineUUIDWrapper();
                break;
            }
            case "online": {
                newWrapper = new DefaultUUIDWrapper();
                break;
            }
            default: {
                try {
                    Class<?> clazz = Class.forName(args[0]);
                    newWrapper = (UUIDWrapper) clazz.newInstance();
                }
                catch (Exception e) {
                    MainUtil.sendMessage(player, C.COMMAND_SYNTAX, "/plot uuidconvert <lower|offline|online>");
                    return false;
                }
            }
        }
        
        if (args.length != 2 || !args[1].equals("-o")) {
            MainUtil.sendMessage(player, C.COMMAND_SYNTAX, "/plot uuidconvert " + args[0] + " - o");
            MainUtil.sendMessage(player, "&c请注意以下问题!");
            MainUtil.sendMessage(player, "&8 - &c如果中断进程, 所有的地皮将会被删除");
            MainUtil.sendMessage(player, "&8 - &c如果发生错误, 所有的地皮将会被删除");
            MainUtil.sendMessage(player, "&8 - &转换的时候地皮设置将会被删除");
            MainUtil.sendMessage(player, "&c在使用之前请务必备份数据!!!");
            MainUtil.sendMessage(player, "&7准备就绪后重新输入指令和参数");
            return false;
        }
        
        if (currentUUIDWrapper.getClass().getCanonicalName().equals(newWrapper.getClass().getCanonicalName())) {
            MainUtil.sendMessage(player, "&c正在使用 UUID 模式!");
            return false;
        }
        MainUtil.sendConsoleMessage("&6正在开始 UUID 模式转换");
        MainUtil.sendConsoleMessage("&7 - 正在断开玩家的服务器连接");
        for (PlotPlayer user : UUIDHandler.players.values()) {
            PlayerManager.manager.kickPlayer(user, "PlotSquared UUID 转换已经开始. 当转换完成后你可以重新连入服务器.");
        }
        
        MainUtil.sendConsoleMessage("&7 - 正在初始化地图");
        
        HashMap<UUID, UUID> uCMap = new HashMap<UUID, UUID>();
        HashMap<UUID, UUID> uCReverse = new HashMap<UUID, UUID>();
        
        MainUtil.sendConsoleMessage("&7 - 正在获取玩家数据");

        final HashSet<String> worlds = new HashSet<>();
        worlds.add(Bukkit.getWorlds().get(0).getName());
        worlds.add("world");
        final HashSet<UUID> uuids = new HashSet<>();
        final HashSet<String> names = new HashSet<>();
        for (final String worldname : worlds) {
            final File playerdataFolder = new File(worldname + File.separator + "playerdata");
            String[] dat = playerdataFolder.list(new FilenameFilter() {
                @Override
                public boolean accept(final File f, final String s) {
                    return s.endsWith(".dat");
                }
            });
            if (dat != null) {
                for (final String current : dat) {
                    final String s = current.replaceAll(".dat$", "");
                    try {
                        final UUID uuid = UUID.fromString(s);
                        uuids.add(uuid);
                    } catch (final Exception e) {
                        PlotSquared.log(C.PREFIX.s() + "无效的玩家数据: " + current);
                    }
                }
            }
            final File playersFolder = new File(worldname + File.separator + "players");
            dat = playersFolder.list(new FilenameFilter() {
                @Override
                public boolean accept(final File f, final String s) {
                    return s.endsWith(".dat");
                }
            });
            if (dat != null) {
                for (final String current : dat) {
                    names.add(current.replaceAll(".dat$", ""));
                }
            }
        }
        
        MainUtil.sendConsoleMessage("&7 - 正在构建地图");
        UUID uuid2;
        final UUIDWrapper wrapper = new DefaultUUIDWrapper();
        for (UUID uuid : uuids) {
            try {
                final OfflinePlotPlayer op = wrapper.getOfflinePlayer(uuid);
                uuid = currentUUIDWrapper.getUUID(op);
                uuid2 = newWrapper.getUUID(op);
                if (!uuid.equals(uuid2)) {
                    uCMap.put(uuid, uuid2);
                    uCReverse.put(uuid2, uuid);
                }
            } catch (final Throwable e) {
                PlotSquared.log(C.PREFIX.s() + "&6无效的玩家数据: " + uuid.toString() + ".dat");
            }
        }
        for (final String name : names) {
            final UUID uuid = currentUUIDWrapper.getUUID(name);
            uuid2 = newWrapper.getUUID(name);
            if (!uuid.equals(uuid2)) {
                uCMap.put(uuid, uuid2);
                uCReverse.put(uuid2, uuid);
            }
        }
        if (uCMap.size() == 0) {
            MainUtil.sendConsoleMessage("&c - 错误! 尝试重新构建");
            for (OfflinePlotPlayer op : currentUUIDWrapper.getOfflinePlayers()) {
                if (op.getLastPlayed() != 0) {
                    String name = op.getName();
                    StringWrapper wrap = new StringWrapper(name);
                    UUID uuid = currentUUIDWrapper.getUUID(op);
                    uuid2 = newWrapper.getUUID(op);
                    if (!uuid.equals(uuid2)) {
                        uCMap.put(uuid, uuid2);
                        uCReverse.put(uuid2, uuid);
                    }
                }
            }
            if (uCMap.size() == 0) {
                MainUtil.sendConsoleMessage("&c错误. 无法获取 UUID!");
                return false;
            }
            else {
                MainUtil.sendConsoleMessage("&a - 重新构建成功");
            }
        }
        
        MainUtil.sendConsoleMessage("&7 - 正在更换 Cache");
        for (Entry<UUID, UUID> entry : uCMap.entrySet()) {
            String name = UUIDHandler.getName(entry.getKey());
            UUIDHandler.add(new StringWrapper(name), entry.getValue());
        }
        
        MainUtil.sendConsoleMessage("&7 - 正在更换 Wrapper");
        UUIDHandler.uuidWrapper = newWrapper;
        
        MainUtil.sendConsoleMessage("&7 - 正在更新地皮");
        
        for (Plot plot : PlotSquared.getPlotsRaw()) {
            UUID value = uCMap.get(plot.owner);
            if (value != null) {
                plot.owner = value;
            }
            plot.trusted = new ArrayList<>();
            plot.members = new ArrayList<>();
            plot.denied = new ArrayList<>();
        }
        
        MainUtil.sendConsoleMessage("&7 - 正在删除数据库");
        final AbstractDB database = DBFunc.dbManager;
        boolean result = database.deleteTables();

        MainUtil.sendConsoleMessage("&7 - 正在创建表单");
        
        try {
            database.createTables(Settings.DB.USE_MYSQL ? "mysql" : "sqlite");
            if (!result) {
                MainUtil.sendConsoleMessage("&c转换失败! 正在尝试恢复");
                for (Plot plot : PlotSquared.getPlots()) {
                    UUID value = uCReverse.get(plot.owner);
                    if (value != null) {
                        plot.owner = value;
                    }
                }
                database.createPlotsAndData(new ArrayList<>(PlotSquared.getPlots()), new Runnable() {
                    @Override
                    public void run() {
                        MainUtil.sendMessage(null, "&6恢复成功!");
                    }
                });
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        if (newWrapper instanceof OfflineUUIDWrapper) {
            PlotSquared.config.set("UUID.force-lowercase", false);
            PlotSquared.config.set("UUID.offline", true);
        }
        else if (newWrapper instanceof LowerOfflineUUIDWrapper) {
            PlotSquared.config.set("UUID.force-lowercase", true);
            PlotSquared.config.set("UUID.offline", true);
        }
        else if (newWrapper instanceof DefaultUUIDWrapper) {
            PlotSquared.config.set("UUID.force-lowercase", false);
            PlotSquared.config.set("UUID.offline", false);
        }
        try {
            PlotSquared.config.save(PlotSquared.configFile);
        }
        catch (Exception e) {
            MainUtil.sendConsoleMessage("C无法保存配置文件. 这将需要手动设置!");
        }
        
        MainUtil.sendConsoleMessage("&7 - 正在填充表格");
        
        TaskManager.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                ArrayList<Plot> plots = new ArrayList<>(PlotSquared.getPlots());
                database.createPlotsAndData(plots, new Runnable() {
                    @Override
                    public void run() {
                        MainUtil.sendConsoleMessage("&a转换完成!");
                    }
                });
            }
        });
        
        MainUtil.sendConsoleMessage("&a现在玩家将可以进入服务器了");
        MainUtil.sendConsoleMessage("&c但是转换仍在进行, 转换就绪后将会收到通知");
        return true;
    }
}
