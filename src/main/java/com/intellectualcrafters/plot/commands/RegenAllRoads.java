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

import java.util.List;

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.generator.HybridPlotManager;
import com.intellectualcrafters.plot.generator.HybridUtils;
import com.intellectualcrafters.plot.object.ChunkLoc;
import com.intellectualcrafters.plot.object.PlotManager;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.ChunkManager;

public class RegenAllRoads extends SubCommand {
    public RegenAllRoads() {
        super(Command.REGENALLROADS, "在当前地图重建全部的道路", "rgar", CommandCategory.DEBUG, false);
    }

    @Override
    public boolean execute(final PlotPlayer player, final String... args) {
        if (player != null) {
            sendMessage(player, C.NOT_CONSOLE);
            return false;
        }
        if (args.length < 1) {
            sendMessage(player, C.NEED_PLOT_WORLD);
            return false;
        }
        int height = 0;
        if (args.length == 2) {
            try {
                height = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                sendMessage(player, C.NOT_VALID_NUMBER);
                sendMessage(player, C.COMMAND_SYNTAX, "/plot regenallroads <世界名称> [高度]");
                return false;
            }
        }
        final String name = args[0];
        final PlotManager manager = PlotSquared.getPlotManager(name);
        if ((manager == null) || !(manager instanceof HybridPlotManager)) {
            sendMessage(player, C.NOT_VALID_PLOT_WORLD);
            return false;
        }
        final List<ChunkLoc> chunks = ChunkManager.manager.getChunkChunks(name);
        PlotSquared.log("&c如果没有设置建筑文件, 将不会有任何事情发生");
        PlotSquared.log("&7 - 设置一个建筑文件, 站在地皮上输入 &c/plot createroadschematic");
        PlotSquared.log("&6所有区块更新: &7" + (chunks.size() * 1024));
        PlotSquared.log("&6预估时间: &7" + (chunks.size()) + " 秒");
        final boolean result = HybridUtils.manager.scheduleRoadUpdate(name, height);
        if (!result) {
            PlotSquared.log("&c无法计划地图文件更新! (是否有进程未完成?)");
            return false;
        }
        return true;
    }
}
