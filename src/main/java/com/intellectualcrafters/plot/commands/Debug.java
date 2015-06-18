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

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.Lag;
import com.intellectualcrafters.plot.util.MainUtil;

public class Debug extends SubCommand {
    public Debug() {
        super(Command.DEBUG, "显示调试信息", "debug [msg]", CommandCategory.DEBUG, false);
    }

    @Override
    public boolean execute(final PlotPlayer plr, final String... args) {
        if ((args.length > 0) && args[0].equalsIgnoreCase("msg")) {
            final StringBuilder msg = new StringBuilder();
            for (final C c : C.values()) {
                msg.append(c.s()).append("");
            }
            MainUtil.sendMessage(plr, msg.toString());
            return true;
        }
        StringBuilder information;
        String header, line, section;
        {
            information = new StringBuilder();
            header = C.DEUBG_HEADER.s();
            line = C.DEBUG_LINE.s();
            section = C.DEBUG_SECTION.s();
        }
        {
            final StringBuilder worlds = new StringBuilder("");
            for (final String world : PlotSquared.getPlotWorlds()) {
                worlds.append(world).append(" ");
            }
            information.append(header+ "\n");
            information.append(getSection(section, "插件调试信息显示")+ "\n");
            information.append(getLine(line, "当前TPS", Lag.getTPS())+ "\n");
            information.append(getLine(line, "滞后百分比", (int) Lag.getPercentage() + "%")+ "\n");
            information.append(getLine(line, "TPS百分比", (int) Lag.getFullPercentage() + "%")+ "\n");
            information.append(getSection(section, "地皮世界信息")+ "\n");
            information.append(getLine(line, "地皮世界", worlds)+ "\n");
            information.append(getLine(line, "拥有地皮", PlotSquared.getPlots().size())+ "\n");
            information.append(getSection(section, "所有发送信息")+ "\n");
            information.append(getLine(line, "信息总计", C.values().length)+ "\n");
            information.append(getLine(line, "查看所有", "/plot debug msg")+ "\n");
        }
        {
            MainUtil.sendMessage(plr, information.toString());
        }
        return true;
    }

    private String getSection(final String line, final String val) {
        return line.replaceAll("%val%", val) ;
    }

    private String getLine(final String line, final String var, final Object val) {
        return line.replaceAll("%var%", var).replaceAll("%val%", "" + val) ;
    }
}
