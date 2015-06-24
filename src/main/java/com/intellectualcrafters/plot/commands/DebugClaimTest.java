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

import java.util.ArrayList;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.object.ChunkLoc;
import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotManager;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.object.PlotWorld;
import com.intellectualcrafters.plot.object.StringWrapper;
import com.intellectualcrafters.plot.util.BlockManager;
import com.intellectualcrafters.plot.util.ChunkManager;
import com.intellectualcrafters.plot.util.EventUtil;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.bukkit.UUIDHandler;

/**
 * @author Citymonstret
 */
public class DebugClaimTest extends SubCommand {
    public DebugClaimTest() {
        super(Command.DEBUGCLAIMTEST, "如果你不小心删除了数据库, 这个指令将可以识别地皮牌子来恢复数据库. 执行时间可能会漫长.", "debugclaimtest", CommandCategory.DEBUG, false);
    }

    public static boolean claimPlot(final PlotPlayer player, final Plot plot, final boolean teleport) {
        return claimPlot(player, plot, teleport, "");
    }

    public static boolean claimPlot(final PlotPlayer player, final Plot plot, final boolean teleport, final String schematic) {
        final boolean result = EventUtil.manager.callClaim(player, plot, false);
        if (result) {
            MainUtil.createPlot(player.getUUID(), plot);
            MainUtil.setSign(player.getName(), plot);
            MainUtil.sendMessage(player, C.CLAIMED);
            if (teleport) {
                MainUtil.teleportPlayer(player, player.getLocation(), plot);
            }
        }
        return !result;
    }

    @Override
    public boolean execute(final PlotPlayer plr, final String... args) {
        if (plr == null) {
            if (args.length < 3) {
                return !MainUtil.sendMessage(null, "如果你删除了你的数据库, 这个指令将可以识别地皮牌子来恢复数据库. \n\n&c缺失地皮世界参数 /plot debugclaimtest {世界名称} {最小地皮ID} {最大地皮ID}");
            }
            final String world = args[0];
            if (!BlockManager.manager.isWorld(world) || !PlotSquared.isPlotWorld(world)) {
                return !MainUtil.sendMessage(null, "&c无效的地皮世界!");
            }
            PlotId min, max;
            try {
                final String[] split1 = args[1].split(";");
                final String[] split2 = args[2].split(";");
                min = new PlotId(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));
                max = new PlotId(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
            } catch (final Exception e) {
                return !MainUtil.sendMessage(null, "&c无效的最大最小地皮ID参数. &7地皮ID参数格式 &cX;Y &7这个 X,Y 是地皮的坐标\n加入这个参数后, 数据恢复将在所选区域进行.");
            }
            MainUtil.sendMessage(null, "&3木牌恢复&8->&3PlotSquared&8: &7正在开始地皮数据恢复. 该过程需要一些时间...");
            MainUtil.sendMessage(null, "&3木牌恢复&8->&3PlotSquared&8: 发现超过 25000 区块. 请限制半径... (~3.8 分钟)");
            final PlotManager manager = PlotSquared.getPlotManager(world);
            final PlotWorld plotworld = PlotSquared.getPlotWorld(world);
            final ArrayList<Plot> plots = new ArrayList<>();
            for (final PlotId id : MainUtil.getPlotSelectionIds(min, max)) {
                final Plot plot = MainUtil.getPlot(world, id);
                final boolean contains = PlotSquared.getPlots(world).containsKey(plot.id);
                if (contains) {
                    MainUtil.sendMessage(null, " - &c数据库已存在: " + plot.id);
                    continue;
                }
                final Location loc = manager.getSignLoc(plotworld, plot);
                final ChunkLoc chunk = new ChunkLoc(loc.getX() >> 4, loc.getZ() >> 4);
                final boolean result = ChunkManager.manager.loadChunk(world, chunk);
                if (!result) {
                    continue;
                }
                final String[] lines = BlockManager.manager.getSign(loc);
                if (lines != null) {
                    String line = lines[2];
                    if ((line != null) && (line.length() > 2)) {
                        line = line.substring(2);
                        final BiMap<StringWrapper, UUID> map = UUIDHandler.getUuidMap();
                        UUID uuid = (map.get(new StringWrapper(line)));
                        if (uuid == null) {
                            for (final StringWrapper string : map.keySet()) {
                                if (string.value.toLowerCase().startsWith(line.toLowerCase())) {
                                    uuid = map.get(string);
                                    break;
                                }
                            }
                        }
                        if (uuid == null) {
                            uuid = UUIDHandler.getUUID(line);
                        }
                        if (uuid != null) {
                            MainUtil.sendMessage(null, " - &a发现了地皮: " + plot.id + " : " + line);
                            plot.owner = uuid;
                            plot.hasChanged = true;
                            plots.add(plot);
                        } else {
                            MainUtil.sendMessage(null, " - &c无效的玩家名称: " + plot.id + " : " + line);
                        }
                    }
                }
            }
            if (plots.size() > 0) {
                MainUtil.sendMessage(null, "&3木牌恢复&8->&3PlotSquared&8: &7更新了 '" + plots.size() + "' 块地皮!");
                DBFunc.createPlotsAndData(plots, new Runnable() {
                    @Override
                    public void run() {
                        MainUtil.sendMessage(null, "&6数据库更新成功!");
                    }
                });
                for (final Plot plot : plots) {
                    PlotSquared.updatePlot(plot);
                }
                MainUtil.sendMessage(null, "&3木牌恢复&8->&3PlotSquared&8: &7已完成!");
            } else {
                MainUtil.sendMessage(null, "给出的搜索条件已经没有未恢复地图了.");
            }
        } else {
            MainUtil.sendMessage(plr, "&6这个指令只能通过控制台使用.");
        }
        return true;
    }
}
