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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.flag.FlagManager;
import com.intellectualcrafters.plot.generator.BukkitHybridUtils;
import com.intellectualcrafters.plot.generator.HybridUtils;
import com.intellectualcrafters.plot.object.ChunkLoc;
import com.intellectualcrafters.plot.object.OfflinePlotPlayer;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.BlockManager;
import com.intellectualcrafters.plot.util.ChunkManager;
import com.intellectualcrafters.plot.util.ExpireManager;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.bukkit.UUIDHandler;

public class DebugExec extends SubCommand {
    public DebugExec() {
        super("debugexec", "plots.admin", "PlotSquared-Multipurpose™ 调试信息指令", "debugexec", "exec", CommandCategory.DEBUG, false);
    }

    @Override
    public boolean execute(final PlotPlayer player, final String... args) {
    	final List<String> allowed_params = Arrays.asList(new String[] { "reset-modified", "stop-expire", "start-expire", "show-expired", "update-expired", "seen", "trim-check" });
        if (args.length > 0) {
            final String arg = args[0].toLowerCase();
            switch (arg) {
                case "stop-expire": {
                    if (ExpireManager.task != -1) {
                        Bukkit.getScheduler().cancelTask(ExpireManager.task);
                    } else {
                        return MainUtil.sendMessage(player, "进程已经停止");
                    }
                    ExpireManager.task = -1;
                    return MainUtil.sendMessage(player, "进程被终止.");
                }
                case "reset-modified": {
                    for (Plot plot : PlotSquared.getPlots()) {
                        if (FlagManager.getPlotFlag(plot, "modified-blocks") != null) {
                            FlagManager.removePlotFlag(plot, "modified-blocks");
                        }
                    }
                    return MainUtil.sendMessage(player, "清理了被修改的标识!");    
                }
                case "start-rgar": {
                    if (args.length != 2) {
                        PlotSquared.log("&cInvalid syntax: /plot debugexec start-rgar <世界名称>");
                        return false;
                    }
                    boolean result;
                    if (!PlotSquared.isPlotWorld(args[1])) {
                        MainUtil.sendMessage(player, C.NOT_VALID_PLOT_WORLD, args[1]);
                        return false;
                    }
                    if (BukkitHybridUtils.regions != null) {
                        result = ((BukkitHybridUtils)(HybridUtils.manager)).scheduleRoadUpdate(args[1], BukkitHybridUtils.regions, 0);
                    }
                    else {
                        result = HybridUtils.manager.scheduleRoadUpdate(args[1], 0);
                    }
                    if (!result) {
                        PlotSquared.log("&c无法计划地图文件更新! (是否有进程未完成?)");
                        return false;
                    }
                    return true;
                }
                case "stop-rgar": {
                    if (((BukkitHybridUtils)(HybridUtils.manager)).task == 0) {
                        PlotSquared.log("&c没有运行进程!");
                        return false;
                    }
                    Bukkit.getScheduler().cancelTask(((BukkitHybridUtils)(HybridUtils.manager)).task);
                    PlotSquared.log("&c正在取消进程...");
                    while (BukkitHybridUtils.chunks.size() > 0) {
                        ChunkLoc chunk = BukkitHybridUtils.chunks.get(0);
                        BukkitHybridUtils.chunks.remove(0);
                        HybridUtils.manager.regenerateRoad(BukkitHybridUtils.world, chunk, 0);
                        ChunkManager.manager.unloadChunk(BukkitHybridUtils.world, chunk);
                    }
                    PlotSquared.log("&c已取消!");
                    return true;
                }
                case "start-expire": {
                    if (ExpireManager.task == -1) {
                        ExpireManager.runTask();
                    } else {
                        return MainUtil.sendMessage(player, "过期地皮进程已开始");
                    }
                    return MainUtil.sendMessage(player, "已经开始过期地皮进程了");
                }
                case "update-expired": {
                    if (args.length > 1) {
                        final String world = args[1];
                        if (!BlockManager.manager.isWorld(world)) {
                            return MainUtil.sendMessage(player, "无效的世界: " + args[1]);
                        }
                        MainUtil.sendMessage(player, "正在更新过期地皮列表");
                        ExpireManager.updateExpired(args[1]);
                        return true;
                    }
                    return MainUtil.sendMessage(player, "指令 /plot debugexec update-expired <世界名称>");
                }
                case "show-expired": {
                    if (args.length > 1) {
                        final String world = args[1];
                        if (!BlockManager.manager.isWorld(world)) {
                            return MainUtil.sendMessage(player, "无效的世界: " + args[1]);
                        }
                        if (!ExpireManager.expiredPlots.containsKey(args[1])) {
                            return MainUtil.sendMessage(player, "没有进程运行在世界: " + args[1]);
                        }
                        MainUtil.sendMessage(player, "过期的地皮 (" + ExpireManager.expiredPlots.get(args[1]).size() + "):");
                        for (final Plot plot : ExpireManager.expiredPlots.get(args[1])) {
                            MainUtil.sendMessage(player, " - " + plot.world + ";" + plot.id.x + ";" + plot.id.y + ";" + UUIDHandler.getName(plot.owner) + " : " + ExpireManager.dates.get(plot.owner));
                        }
                        return true;
                    }
                    return MainUtil.sendMessage(player, "指令 /plot debugexec show-expired <世界名称>");
                }
                case "seen": {
                    if (args.length != 2) {
                        return MainUtil.sendMessage(player, "指令 /plot debugexec seen <玩家名称>");
                    }
                    final UUID uuid = UUIDHandler.getUUID(args[1]);
                    if (uuid == null) {
                        return MainUtil.sendMessage(player, "玩家未找到: " + args[1]);
                    }
                    final OfflinePlotPlayer op = UUIDHandler.uuidWrapper.getOfflinePlayer(uuid);
                    if ((op == null) || (op.getLastPlayed() == 0)) {
                        return MainUtil.sendMessage(player, "玩家没有进过服务器: " + args[1]);
                    }
                    final Timestamp stamp = new Timestamp(op.getLastPlayed());
                    final Date date = new Date(stamp.getTime());
                    MainUtil.sendMessage(player, "PLAYER: " + args[1]);
                    MainUtil.sendMessage(player, "UUID: " + uuid);
                    MainUtil.sendMessage(player, "Object: " + date.toGMTString());
                    MainUtil.sendMessage(player, "GMT: " + date.toGMTString());
                    MainUtil.sendMessage(player, "Local: " + date.toLocaleString());
                    return true;
                }
                case "trim-check": {
                    if (args.length != 2) {
                        MainUtil.sendMessage(player, "指令 /plot debugexec trim-check <世界名称>");
                        MainUtil.sendMessage(player, "&7 - 生成该世界的清理列表");
                        return MainUtil.sendMessage(player, "&7 - 有到期地皮之后运行");
                    }
                    final String world = args[1];
                    if (!BlockManager.manager.isWorld(world) || !PlotSquared.isPlotWorld(args[1])) {
                        return MainUtil.sendMessage(player, "Invalid world: " + args[1]);
                    }
                    final ArrayList<ChunkLoc> empty = new ArrayList<>();
                    final boolean result = Trim.getTrimRegions(empty, world, new Runnable() {
                        @Override
                        public void run() {
                            Trim.sendMessage("当前进程已完成! 这些区块将被删除:");
                            Trim.sendMessage(" - 地图文件 #: " + empty.size());
                            Trim.sendMessage(" - 区块数目: " + (empty.size() * 1024) + " (max)");
                            Trim.sendMessage("正在输出记录日志...");
                            final File file = new File(PlotSquared.IMP.getDirectory() + File.separator + "trim.txt");
                            PrintWriter writer;
                            try {
                                writer = new PrintWriter(file);
                                for (final ChunkLoc loc : empty) {
                                    writer.println(world + "/region/r." + loc.x + "." + loc.z + ".mca");
                                }
                                writer.close();
                                Trim.sendMessage("文件保存到 'plugins/PlotSquared/trim.txt'");
                            } catch (final FileNotFoundException e) {
                                e.printStackTrace();
                                Trim.sendMessage("文件报错失败! :(");
                            }
                            Trim.sendMessage("如何从区域文件中获取区块坐标:");
                            Trim.sendMessage(" - 找到区域文件的 x,z.mca (例如r.0.1.mca)");
                            Trim.sendMessage(" - 每个数乘以 32 ; 将得到一个开始的坐标 (例如 0.32)");
                            Trim.sendMessage(" - 每个数加上 31 ; 将得到一个结束的坐标 (例如 31.63)");
                        }
                    });
                    if (!result) {
                        MainUtil.sendMessage(player, "清理进程已经开始!");
                    }
                    return result;
                }
            }
        }
        MainUtil.sendMessage(player, "可用的子指令: /plot debugexec <" + StringUtils.join(allowed_params, "|") + ">");
        return true;
    }
}
