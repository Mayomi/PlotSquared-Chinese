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

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.database.DBFunc;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MainUtil;

/**
 * @author Citymonstret
 */
public class DebugSaveTest extends SubCommand {
    public DebugSaveTest() {
        super(Command.DEBUGSAVETEST, "这个调试指令将强制重建所有数据库中的地皮", "debugsavetest", CommandCategory.DEBUG, false);
    }

    @Override
    public boolean execute(final PlotPlayer plr, final String... args) {
        if (plr == null) {
            final ArrayList<Plot> plots = new ArrayList<Plot>();
            plots.addAll(PlotSquared.getPlots());
            MainUtil.sendMessage(null, "&6正在开始 `DEBUGSAVETEST`");
            DBFunc.createPlotsAndData(plots, new Runnable() {
                @Override
                public void run() {
                    MainUtil.sendMessage(null, "&6数据库同步完成!");
                }
            });
        } else {
            MainUtil.sendMessage(plr, "这个指令只能通过控制台使用.");
        }
        return true;
    }
}
