package com.intellectualcrafters.plot.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.intellectualcrafters.plot.PlotSquared;
import com.intellectualcrafters.plot.config.Settings;
import com.intellectualcrafters.plot.database.MySQL;
import com.intellectualcrafters.plot.database.SQLManager;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.MainUtil;
import com.intellectualcrafters.plot.util.StringComparison;
import com.intellectualcrafters.plot.util.TaskManager;
import com.intellectualcrafters.plot.util.bukkit.UUIDHandler;

/**
 * Created 2014-11-15 for PlotSquared
 *
 * @author Citymonstret
 */
public class Database extends SubCommand {
    public Database() {
        super(Command.DATABASE, "转换/备份 存储方式", "database [类型] [...细节]", CommandCategory.DEBUG, false);
    }

    private static boolean sendMessageU(final UUID uuid, final String msg) {
        if (uuid == null) {
            PlotSquared.log(msg);
        } else {
            final PlotPlayer p = UUIDHandler.getPlayer(uuid);
            if ((p != null) && p.isOnline()) {
                return MainUtil.sendMessage(p, msg);
            } else {
                return sendMessageU(null, msg);
            }
        }
        return true;
    }

    public static void insertPlots(final SQLManager manager, final UUID requester, final Connection c) {
        final java.util.Set<Plot> plots = PlotSquared.getPlots();
        TaskManager.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<Plot> ps = new ArrayList<>();
                    for (final Plot p : plots) {
                        ps.add(p);
                    }
                    sendMessageU(requester, "&6已开始...");
                    manager.createPlotsAndData(ps, new Runnable() {
                        @Override
                        public void run() {
                            sendMessageU(requester, "&6数据库转换成功!");
                        }
                    });
                } catch (final Exception e) {
                    sendMessageU(requester, "无法插入地皮表单, 请查看报错信息");
                    e.printStackTrace();
                }
                try {
                    c.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean execute(final PlotPlayer plr, final String... args) {
        if (args.length < 1) {
            return sendMessage(plr, "/plot database [sqlite/mysql]");
        }
        final String type = new StringComparison(args[0], new String[] { "mysql", "sqlite" }).getBestMatch().toLowerCase();
        switch (type) {
            case "mysql":
                if (args.length < 6) {
                    return sendMessage(plr, "/plot database mysql [数据库地址] [数据库端口] [数据库用户名] [数据库密码] [数据库名] {表单前缀}");
                }
                final String host = args[1];
                final String port = args[2];
                final String username = args[3];
                final String password = args[4];
                final String database = args[5];
                String prefix = "";
                if (args.length > 6) {
                    prefix = args[6];
                }
                Connection n;
                try {
                    n = new MySQL(PlotSquared.THIS, host, port, database, username, password).openConnection();
                    // Connection
                    if (n.isClosed()) {
                        return sendMessage(plr, "无法打开连接");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return sendMessage(plr, "无法打开连接, 请查看报错信息");
                }
                final SQLManager manager = new SQLManager(n, prefix);
                try {
                    manager.createTables(Settings.DB.USE_MYSQL ? "mysql" : "sqlite");
                } catch (final SQLException e) {
                    e.printStackTrace();
                    return sendMessage(plr, "无法读取数据库或无法创建表单") && sendMessage(plr, "请查看报错信息");
                }
                UUID requester = null;
                if (plr != null) {
                    requester = UUIDHandler.getUUID(plr);
                }
                insertPlots(manager, requester, n);
                break;
            case "sqlite":
                if (args.length < 2) {
                    return sendMessage(plr, "/plot database sqlite [文件名称]");
                }
                sendMessage(plr, "这个暂时不支持");
                break;
            default:
                return sendMessage(plr, "未知数据库类型");
        }
        return false;
    }

    private boolean sendMessage(final PlotPlayer player, final String msg) {
        if (player == null) {
            PlotSquared.log(msg);
        } else {
            MainUtil.sendMessage(player, msg);
        }
        return true;
    }
}
