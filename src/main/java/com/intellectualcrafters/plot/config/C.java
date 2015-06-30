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
package com.intellectualcrafters.plot.config;

import org.bukkit.ChatColor;

import com.intellectualsites.translation.TranslationFile;
import com.intellectualsites.translation.TranslationLanguage;
import com.intellectualsites.translation.TranslationManager;
import com.intellectualsites.translation.TranslationObject;
import com.intellectualsites.translation.YamlTranslationFile;
import com.intellectualsites.translation.bukkit.BukkitTranslation;

/**
 * Captions class.
 *
 * @author Citymonstret 
 */
public enum C {

    /*
     * Confirm
     */
    FAILED_CONFIRM("$2下一步时发生了错误!", "Confirm"),
    REQUIRES_CONFIRM("$2你是否执行: $1%s$2?&-$2该操作不可逆! 如果确定请输入: $1/plot confirm", "Confirm"),
    /*
     * Move
     */
    MOVE_SUCCESS("$4成功移除地皮.", "Move"),
    COPY_SUCCESS("$4你成功复制了地皮.", "Move"),
    REQUIRES_UNOWNED("$2该位置已被占用.", "Move"),
    /*
     * Compass
     */
    COMPASS_TARGET("$4成功传送到目标地皮", "Compass"),
    /*
     * Cluster
     */
    CLUSTER_AVAILABLE_ARGS("$1可用的组群指令: $4list$2, $4create$2, $4delete$2, $4resize$2, $4invite$2, $4kick$2, $4leave$2, $4helpers$2, $4info$2, $4tp$2, $4sethome .", "Cluster"),
    CLUSTER_LIST_HEADING("$2该世界中有 $1%s$2 个组群.", "Cluster"),
    CLUSTER_LIST_ELEMENT("$2 - $1%s\n", "Cluster"),
    CLUSTER_INTERSECTION("$2该地区有重叠的 $1%s$2 个组群.", "Cluster"),
    CLUSTER_ADDED("$4成功创建组群.", "Cluster"),
    CLUSTER_DELETED("$4成功删除组群.", "Cluster"),
    CLUSTER_RESIZED("$4成功调整了组群的大小.", "Cluster"),
    CLUSTER_ADDED_USER("$4成功加入玩家到组群中.", "Cluster"),
    CANNOT_KICK_PLAYER("$2你不能踢出该玩家", "Cluster"),
    CLUSTER_INVITED("$1你被邀请到组群: $2%s", "Cluster"),
    CLUSTER_REMOVED("$1你被组群 $2%s 踢出了", "Cluster"),
    CLUSTER_KICKED_USER("$4成功踢出玩家", "Cluster"),
    INVALID_CLUSTER("$1无效的组群名称: $2%s", "Cluster"),
    CLUSTER_NOT_ADDED("$2这个玩家未被加入地皮组群中", "Cluster"),
    CLUSTER_CANNOT_LEAVE("$1在退出之前先转移所有权", "Cluster"),
    CLUSTER_ADDED_HELPER("$4成功为组群加入帮手", "Cluster"),
    CLUSTER_REMOVED_HELPER("$4成功移除了组群的帮手", "Cluster"),
    CLUSTER_REGENERATED("$4成功开始群组重建", "Cluster"),
    CLUSTER_TELEPORTING("$4传送中...", "Cluster"),
    CLUSTER_INFO("$1当前组群: $2%id%&-$1名称: $2%name%&-$1拥有者: $2%owner%&-$1大小: $2%size%&-$1权限: $2%rights%", "Cluster"),
    CLUSTER_CURRENT_PLOTID("$1当前地皮: $2%s", "Cluster"),
    /*
     * Border
     */
    BORDER("$2你走到了地图边境", "Border"),
    /*
     * Unclaim
     */
    UNCLAIM_SUCCESS("$4你放弃了这块地皮.", "Unclaim"),
    /*
     * WorldEdit masks
     */
    REQUIRE_SELECTION_IN_MASK("$2你选择的地皮 %s 不是你的. 你只能在你的地皮中建筑.", "WorldEdit Masks"),
    WORLDEDIT_VOLUME("$2你无法选择 %current% 的空间. 最大空间可以设置为 %max%.", "WorldEdit Masks"),
    WORLDEDIT_ITERATIONS("$2你不能重复 %current% 次. 最大重复次数为 %max%.", "WorldEdit Masks"),
    WORLDEDIT_UNSAFE("$2该指令已经被禁止使用", "WorldEdit Masks"),
    WORLDEDIT_BYPASS("$2&o跳过权限请输入 $3/plot wea", "WorldEdit Masks"),
    WORLDEDIT_UNMASKED("$1你的WE功能没有限制.", "WorldEdit Masks"),
    WORLDEDIT_RESTRICTED("$1你的WE功能被限制.", "WorldEdit Masks"),
    /*
     * Records
     */
    RECORD_PLAY("$2%player $2开始播放CD $1%name", "Records"),
    NOTIFY_ENTER("$2%player $2进入了你的地皮 ($1%plot$2)", "Records"),
    NOTIFY_LEAVE("$2%player $2离开了你的地皮 ($1%plot$2)", "Records"),
    /*
     * Swap
     */
    SWAP_OVERLAP("$2该区域不允许覆盖", "Swap"),
    SWAP_DIMENSIONS("$2该区域需要同样大小的尺寸", "Swap"),
    SWAP_SYNTAX("$2/plots swap <地皮ID>", "Swap"),
    SWAP_SUCCESS("$4成功交换地皮", "Swap"),
    STARTED_SWAP("$2开始地皮交换. 交换就绪后会通知你", "Swap"),
    /*
     * Comment
     */
    INBOX_NOTIFICATION("%s 条未读的留言. 使用 /plot inbox 查看", "Comment"),
    NOT_VALID_INBOX_INDEX("$2页数 %s 没有留言", "Comment"),
    INBOX_ITEM("$2 - $4%s", "Comment"),
    COMMENT_SYNTAX("$2用法 /plots comment [X;Z] <%s> <留言>", "Comment"),
    INVALID_INBOX("$2这不是一个有效的页数.\n$1可用的参数: %s", "Comment"),
    NO_PERM_INBOX("$2你没有权限这样做", "Comment"),
    NO_PERM_INBOX_MODIFY("$2你没有权限修改", "Comment"),
    NO_PLOT_INBOX("$2这不是有效的页数", "Comment"),
    COMMENT_REMOVED("$4成功删除地皮留言/s:n$2 - '$3%s$2'", "Comment"),
    COMMENT_ADDED("$4添加了一条新的留言", "Comment"),
    COMMENT_HEADER("$2====== 留言板 ======", "Comment"),
    INBOX_EMPTY("$2还没有留言","Comment"),
    /*
     * Console
     */
    NOT_CONSOLE("$2该指令仅限控制台输入.", "Console"),
    IS_CONSOLE("$2该指令仅限游戏内输入.", "Console"),

    /*
    Inventory
     */
    INVENTORY_USAGE("用法: {usage}", "Inventory"),
    INVENTORY_DESC("描述: {desc}", "Inventory"),
    INVENTORY_CATEGORY("分类: {category}", "Inventory"),

    /*
     * Clipboard
     */
    CLIPBOARD_SET("$2当前地皮已经被复制到了剪切板, 使用 $1/plot paste$2 来粘贴它", "Clipboard"),
    PASTED("$4被选择的地皮成功粘贴.", "Clipboard"),
    PASTE_FAILED("$2粘贴时发生错误. 原因: $2%s", "Clipboard"),
    NO_CLIPBOARD("$2你没有选择剪切板", "Clipboard"),
    CLIPBOARD_INFO("$2当前选择 - 地皮 ID: $1%id$2, 宽度: $1%width$2, 方块数量: $1%total$2", "Clipboard"),
    /*
     * 
     */
    TOGGLE_ENABLED("$2启用设置: %s", "Toggle"),
    TOGGLE_DISABLED("$2禁用设置: %s", "Toggle"),
    /*
     * Ratings
     */
    RATE_THIS("$2为这个地皮评分!", "Ratings"),
    RATING_NOT_VALID("$2你需要指定一个1~10之间的数", "Ratings"),
    RATING_ALREADY_EXISTS("$2你已经为地皮 $2%s 评过分了", "Ratings"),
    RATING_APPLIED("$4你为地皮 $2%s $4评分了", "Ratings"),
    RATING_NOT_YOUR_OWN("$2你不能为自己地皮评分", "Ratings"),
    RATING_NOT_OWNED("$你不能为闲置地皮评分", "Ratings"),
    /*
     * Economy Stuff
     */
    ECON_DISABLED("$2经济功能未启用", "Economy"),
    CANNOT_AFFORD_PLOT("$2你没有足够的金钱购买地皮. 需要花费 $1%s", "Economy"),
    NOT_FOR_SALE("$2这块地皮并不出售", "Economy"),
    CANNOT_BUY_OWN("$2你不能买你自己的地皮", "Economy"),
    PLOT_SOLD("$4你的地皮; $1%s$4, 已卖给了 $1%s$4 价格为 $1$%s", "Economy"),
    CANNOT_AFFORD_MERGE("$2你没有足够的金钱合并地皮. 需要花费 $1%s", "Economy"),
    ADDED_BALANCE("$2向你的账户中加入了 $1%s", "Economy"),
    REMOVED_BALANCE("$2从你的账户中扣除了 $1%s", "Economy"),
    /*
     * Setup Stuff
     */
    SETUP_INIT("$1用法: $2/plot setup <参数>", "Setup"),
    SETUP_STEP("$3[$1步骤 - %s$3] $1%s $2- $1类型: $2%s $1建议值: $2%s", "Setup"),
    SETUP_INVALID_ARG("$2%s不是步骤 %s 的有效参数. 取消构建输入: $1/plot setup cancel", "Setup"),
    SETUP_VALID_ARG("$2参数 $1%s $2设置为 %s", "Setup"),
    SETUP_FINISHED("$3$3如果使用了 MULTIVERSE 或 MULTIWORLD 插件世界的配置会自动写入. 否则你需要手动写入文件 bukkit.yml", "Setup"),
    SETUP_WORLD_TAKEN("$2%s 已经是地皮世界了", "Setup"),
    SETUP_MISSING_WORLD("$2你需要指定一个世界名称 ($1/plot setup &l<世界名称>$1 <生成参数>$2)&-$1附加指令:&-$2 - $1/plot setup <参数>&-$2 - $1/plot setup back&-$2 - $1/plot setup cancel", "Setup"),
    SETUP_MISSING_GENERATOR("$2你需要指定一个生成参数 ($1/plot setup <世界名称> &l<生成参数>&r$2)&-$1附加指令:&-$2 - $1/plot setup <参数>&-$2 - $1/plot setup back&-$2 - $1/plot setup cancel", "Setup"),
    SETUP_INVALID_GENERATOR("$2无效的生成参数. 可选: %s", "Setup"),
    /*
     * Schematic Stuff
     */
    SCHEMATIC_MISSING_ARG("$2你需要指定一个参数. 可用的参数: $1test <名称>$2 , $1save$2 , $1paste $2, $1exportall", "Schematics"),
    SCHEMATIC_INVALID("$2这不是一个有效的建筑文件. 原因: $2%s", "Schematics"),
    SCHEMATIC_VALID("$2这是一个有效的建筑文件", "Schematics"),
    SCHEMATIC_PASTE_FAILED("$2粘贴建筑文件失败", "Schematics"),
    SCHEMATIC_PASTE_SUCCESS("$4建筑文件粘贴成功", "Schematics"),
    /*
     * Title Stuff
     */
    TITLE_ENTERED_PLOT("地皮位置: %world%;%x%;%z%", "Titles"),
    TITLE_ENTERED_PLOT_COLOR("GOLD", "Titles"),
    TITLE_ENTERED_PLOT_SUB("拥有者 %s", "Titles"),
    TITLE_ENTERED_PLOT_SUB_COLOR("RED", "Titles"),
    PREFIX_GREETING("$1%id%$2> ", "Titles"),
    PREFIX_FAREWELL("$1%id%$2> ", "Titles"),
    /*
     * Core Stuff
     */
    PREFIX("$3[$1地皮$3] $2", "Core"),
    ENABLED("$1PlotSquared 已启用", "Core"),
    EXAMPLE_MESSAGE("$2这是一条演示消息 &k!!!", "Core"),
    /*
     * Reload
     */
    RELOADED_CONFIGS("$1文件与设定被重新读取", "Reload"),
    RELOAD_FAILED("$2重新读取失败了", "Reload"),
    /*
     * BarAPI
     */
    BOSSBAR_CLEARING("$2正在清理地皮 $1%id%", "Bar API"),
    /*
     * Alias
     */
    ALIAS_SET_TO("$2地皮的别名设置为 $1%alias%", "Alias"),
    MISSING_ALIAS("$2你需要指定一个别名", "Alias"),
    ALIAS_TOO_LONG("$2别名的长度必须小于50个字符", "Alias"),
    ALIAS_IS_TAKEN("$2这个别名已被别人使用", "Alias"),
    /*
     * Position
     */
    MISSING_POSITION("$2你需要指定一个位置. 可用的参数: $1none", "Position"),
    POSITION_SET("$1在你当前的位置设置家", "Position"),
    HOME_ARGUMENT("$2用法 /plot set home [可不填]", "Position"),
    INVALID_POSITION("$2这不是一个有效的位置", "Position"),
    /*
     * Time
     */
    TIME_FORMAT("$1%hours%, %min%, %sec%", "Time"),
    /*
     * Permission
     */
    NO_SCHEMATIC_PERMISSION("$2你没有权限使用建筑文件 $1%s", "Permission"),
    NO_PERMISSION("$2你缺少了权限: $1%s", "Permission"),
    NO_PLOT_PERMS("$2你必须是地皮拥有者才能执行这个操作", "Permission"),
    CANT_CLAIM_MORE_PLOTS("$2你不能领取更多的地皮了.", "Permission"),
    CANT_TRANSFER_MORE_PLOTS("$2你不能发出更多的地皮邀请了", "Permission"),
    CANT_CLAIM_MORE_PLOTS_NUM("$2你不能一次领取 $1%s $2块地皮", "Permission"),
    YOU_BE_DENIED("$2你不能进入这块地皮", "Permission"),
    NO_PERM_MERGE("$2你不是这块地皮 $1%plot% $2的拥有者", "Permission"),
    UNLINK_REQUIRED("$2如果需要取消合并地皮.", "Permission"),
    UNLINK_IMPOSSIBLE("$2你只能取消合并超级地皮", "Permission"),
    UNLINK_SUCCESS("$2成功取消地皮合并.", "Permission"),
    NO_MERGE_TO_MEGA("$2超级地皮无法被合并.", "Permission"),
    MERGE_NOT_VALID("$2合并请求已失效.", "Permission"),
    MERGE_ACCEPTED("$2合并请求已被接受", "Permission"),
    SUCCESS_MERGE("$2地皮成功被合并!", "Permission"),
    MERGE_REQUESTED("$2成功发送合并请求", "Permission"),
    MERGE_REQUEST_CONFIRM("收到了 %s 的合并请求", "Permission"),
    /*
     * Commands
     */
    NOT_VALID_SUBCOMMAND("$2这不是一个有效的子命令", "Commands"),
    DID_YOU_MEAN("$2你的意思是 $1%s $2吗?", "Commands"),
    NAME_LITTLE("$2%s 名字太短了, $1%s$2<$1%s", "Commands"),
    NO_COMMANDS("$2你没有权限使用任何指令.", "Commands"),
    SUBCOMMAND_SET_OPTIONS_HEADER("$2可用的参数: ", "Commands"),
    COMMAND_SYNTAX("$1用法: $2%s", "Commands"),
    /*
     * Player not found
     */
    INVALID_PLAYER("$2未找到玩家 $1%s.", "Errors"),
    /*
     *
     */
    COMMAND_WENT_WRONG("$2执行命令时发生了错误...", "Errors"),
    /*
     * purge
     */
    PURGE_SYNTAX("用法 /plot purge <x;z|player|unowned|unknown|all> <世界名称>", "Purge"),
    PURGE_SUCCESS("$4成功清理了 %s 块地皮", "Purge"),
    /*
     * trim
     */
    TRIM_SYNTAX("用法 /plot trim <all|x;y> <世界名称>", "Trim"),
    TRIM_START("开始地皮整理...", "Trim"),
    TRIM_IN_PROGRESS("地皮清理任务正在进行!", "Trim"),
    NOT_VALID_HYBRID_PLOT_WORLD("The hybrid plot manager is required to perform this action", "Trim"),
    /*
     * No <plot>
     */
    NO_FREE_PLOTS("$2没有免费的地皮可用", "Errors"),
    NOT_IN_PLOT("$2你不在地皮上", "Errors"),
    NOT_IN_CLUSTER("$2你必须在一个地皮组群中才能进行此操作", "Errors"),
    NOT_IN_PLOT_WORLD("$2你不在地皮世界中", "Errors"),
    PLOTWORLD_INCOMPATIBLE("$2两个世界必须相互兼容", "Errors"),
    NOT_VALID_WORLD("$2这不是一个有效的世界 (注意大小写)", "Errors"),
    NOT_VALID_PLOT_WORLD("$2这不是一个有效的世界 (注意大小写)", "Errors"),
    NO_PLOTS("$2你没有额外的地皮了", "Errors"),
    /*
     * Block List
     */
    NOT_VALID_BLOCK_LIST_HEADER("$2这不是一个有效的方块. 有效的方块:\\n", "Block List"),
    BLOCK_LIST_ITEM(" $1%mat%$2,", "Block List"),
    BLOCK_LIST_SEPARATER("$1,$2 ", "Block List"),
    /*
     * Biome
     */
    NEED_BIOME("$2你需要指定一个生物群落.", "Biome"),
    BIOME_SET_TO("$22地皮的生物群落设置为 $2", "Biome"),
    /*
     * Teleport / Entry
     */
    TELEPORTED_TO_PLOT("$1你传送到了地皮中", "Teleport"),
    TELEPORTED_TO_ROAD("$2你传送到了路中", "Teleport"),
    TELEPORT_IN_SECONDS("$1将在 %s 秒内传送. 请勿移动...", "Teleport"),
    TELEPORT_FAILED("$2因为受到伤害而被取消传送", "Teleport"),
    /*
     * Set Block
     */
    SET_BLOCK_ACTION_FINISHED("$1最后一个方块设置已完成.", "Set Block"),
    /*
     * Debug
     */
    DEUBG_HEADER("$1调试信息\\n", "Debug"),
    DEBUG_SECTION("$2>> $1&l%val%", "Debug"),
    DEBUG_LINE("$2>> $1%var%$2:$1 %val%\\n", "Debug"),
    /*
     * Invalid
     */
    NOT_VALID_DATA("$2这不是一个有效的参数值.", "Invalid"),
    NOT_VALID_BLOCK("$2这不是一个有效的方块.", "Invalid"),
    NOT_VALID_NUMBER("$2这不是一个有效的数字", "Invalid"),
    NOT_VALID_PLOT_ID("$2这不是有效的地皮ID.", "Invalid"),
    PLOT_ID_FORM("$2地皮ID的格式必须为: $1X;Y $2例如: $1-5;7", "Invalid"),
    NOT_YOUR_PLOT("$2这不是你的地皮.", "Invalid"),
    NO_SUCH_PLOT("$2没有该类型的地皮", "Invalid"),
    PLAYER_HAS_NOT_BEEN_ON("$2这个玩家还没拥有地皮", "Invalid"),
    FOUND_NO_PLOTS("$2无法根据该要求查找地皮", "Invalid"),
    /*
     * Camera
     */
    CAMERA_STARTED("$2你进入了地皮 $1%s 的摄像机模式", "Camera"),
    CAMERA_STOPPED("$2你取消了摄像机模式", "Camera"),
    /*
     * Need
     */
    NEED_PLOT_NUMBER("$2你需要指定一个地皮ID或别名", "Need"),
    NEED_BLOCK("$2你需要指定一种方块", "Need"),
    NEED_PLOT_ID("$2你需要指定一个地皮ID.", "Need"),
    NEED_PLOT_WORLD("$2你需要指定一个地皮世界.", "Need"),
    NEED_USER("$2你需要指定一个玩家", "Need"),
    /*
     * Info
     */
    NONE("无", "Info"),
    PLOT_UNOWNED("$2你必须是地皮拥有者才能执行这个操作", "Info"),
    PLOT_INFO_UNCLAIMED("$2地皮 $1%s$2 还未被领取", "Info"),
    PLOT_INFO_HEADER("$3====== $1信息 $3======", false, "Info"),
    PLOT_INFO("$1ID: $2%id%$1\n" + "$1别名: $2%alias%$1\n" + "$1拥有者: $2%owner%$1\n" + "$1生物群落: $2%biome%$1\n" + "$1可否建筑: $2%build%$1\n" + "$1评分: $2%rating%$1/$210$1\n" + "$1可信玩家: $2%trusted%$1\n" + "$1成员: $2%members%$1\n" + "$1黑名单: $2%denied%$1\n" + "$1标识: $2%flags%", "Info"),
    PLOT_INFO_TRUSTED("$1可信玩家:$2 %trusted%", "Info"),
    PLOT_INFO_MEMBERS("$1成员:$2 %members%", "Info"),
    PLOT_INFO_DENIED("$1黑名单玩家:$2 %denied%", "Info"),
    PLOT_INFO_FLAGS("$1标识:$2 %flags%", "Info"),
    PLOT_INFO_BIOME("$1生物群落:$2 %biome%", "Info"),
    PLOT_INFO_RATING("$1评分:$2 %rating%", "Info"),
    PLOT_INFO_OWNER("$1拥有者:$2 %owner%", "Info"),
    PLOT_INFO_ID("$1ID:$2 %id%", "Info"),
    PLOT_INFO_ALIAS("$1别名:$2 %alias%", "Info"),
    PLOT_INFO_SIZE("$1大小:$2 %size%", "Info"),
    PLOT_USER_LIST(" $1%user%$2,", "Info"),
    INFO_SYNTAX_CONSOLE("$2/plot info X;Y", "Info"),
    /*
     * Generating
     */
    GENERATING_COMPONENT("$1开始根据你的设定生成", "Working"),
    /*
     * Clearing
     */
    CLEARING_PLOT("$2清理地皮中.", "Working"),
    CLEARING_DONE("$4清理完成! 耗时 %s毫秒.", "Working"),
    /*
     * Claiming
     */
    PLOT_NOT_CLAIMED("$2地皮未被领取", "Working"),
    PLOT_IS_CLAIMED("$2这块地皮已被领取", "Working"),
    CLAIMED("$4你成功领取了地皮", "Working"),
    /*
     * List
     */
    PLOT_LIST_HEADER_PAGED("$2(页数 $1%cur$2/$1%max$2) $1列出了 %amount% 块地皮", "List"),
    PLOT_LIST_HEADER("$1列出了 %word% 块地皮", "List"),
    PLOT_LIST_ITEM("$2>> $1%id$2:$1%world $2- $1%owner", "List"),
    PLOT_LIST_ITEM_ORDERED("$2[$1%in$2] >> $1%id$2:$1%world $2- $1%owner", "List"),
    PLOT_LIST_FOOTER("$2>> $1%word% 有 $2%num% $1被领取的 %plot%.", "List"),
    /*
     * Left
     */
    LEFT_PLOT("$2你离开了地皮", "Left"),
    /*
     * PlotMe
     */
    NOT_USING_PLOTME("$2这个服务器使用了 $1PlotSquared $2地皮管理系统. 请输入 $1/ps $2或 $1/p2 $2或 $1/plots $2来代替", "Errors"),
    /*
     * Wait
     */
    WAIT_FOR_TIMER("$2设置方块计时器已启用. 请稍后...", "Errors"),
    /*
     * Chat
     */
    PLOT_CHAT_FORMAT("$2[$1地皮聊天$2][$1%plot_id%$2] $1%sender%$2: $1%msg%", "Chat"),
    PLOT_CHAT_FORCED("$2该世界只能使用地皮聊天.", "Chat"),
    PLOT_CHAT_ON("$4地皮聊天已启用.", "Chat"),
    PLOT_CHAT_OFF("$4地皮聊天已禁用.", "Chat"),
    /*
     * Denied
     */
    DENIED_REMOVED("$4你解除了这个地皮对该玩家的黑名单", "Deny"),
    DENIED_ADDED("$4你将该玩家加入了这个地皮的黑名单", "Deny"),
    DENIED_NEED_ARGUMENT("$2缺少参数. $1/plot denied add <玩家名称> $2或 $1/plot denied remove <玩家名称>", "Deny"),
    WAS_NOT_DENIED("$2这玩家不在黑名单中", "Deny"),
    YOU_GOT_DENIED("$4你被加入了黑名单, 所以传送到出生点", "Deny"),
    /*
     * Rain
     */
    NEED_ON_OFF("$2你需要指定一个参数. 可用的参数: $1on$2, $1off", "Rain"),
    SETTING_UPDATED("$4你更新了你的设定", "Rain"),
    /*
     * Flag
     */
    FLAG_KEY("$2关键字: %s", "Flag"),
    FLAG_TYPE("$2类型: %s", "Flag"),
    FLAG_DESC("$2描述: %s", "Flag"),
    NEED_KEY("$2可用参数: $1%values%", "Flag"),
    NOT_VALID_FLAG("$2这不是一个有效的标识", "Flag"),
    NOT_VALID_VALUE("$2标识的参数必须为数字", "Flag"),
    FLAG_NOT_IN_PLOT("$2这块地皮还没有设置标识", "Flag"),
    FLAG_NOT_REMOVED("$2该标识无法被移除", "Flag"),
    FLAG_NOT_ADDED("$2该标识无法被添加", "Flag"),
    FLAG_REMOVED("$4成功移除标识", "Flag"),
    FLAG_ADDED("$4成功添加标识", "Flag"),
    /*
     * Trusted
     */
    TRUSTED_ADDED("$4你成功为地皮加入了可信玩家", "Trusted"),
    TRUSTED_REMOVED("$4你从地皮中移除了一名可信玩家", "Trusted"),
    WAS_NOT_ADDED("$2这个玩家还没有成为该地皮的可信玩家", "Trusted"),
    PLOT_REMOVED_USER("$1你加入的地皮 %s 因为拥有者不活跃而被删除了", "Trusted"),
    /*
     * Member
     */
    REMOVED_PLAYERS("$2从这个地皮中移除玩家 %s .", "Member"),
    ALREADY_OWNER("$2这个玩家已经拥有地皮了.", "Member"),
    ALREADY_ADDED("$2这个玩家已经加入了该分组.", "Member"),
    MEMBER_ADDED("$4当拥有者在线时该玩家可以在这个地皮建造了", "Member"),
    MEMBER_REMOVED("$1你移除了这个地皮的一个成员", "Member"),
    MEMBER_WAS_NOT_ADDED("$2该玩家还没有成为这个地皮的成员", "Member"),
    PLOT_MAX_MEMBERS("$2这个地皮不能再添加成员了", "Member"),
    /*
     * Set Owner
     */
    SET_OWNER("$4你成功为地皮设置了拥有者", "Owner"),
    NOW_OWNER("$4你现在成为地皮 %s 的拥有者了", "Owner"),
    /*
     * Signs
     */
    OWNER_SIGN_LINE_1("$1ID: $1%id%", "Signs"),
    OWNER_SIGN_LINE_2("$1拥有者:", "Signs"),
    OWNER_SIGN_LINE_3("$2%plr%", "Signs"),
    OWNER_SIGN_LINE_4("$3已领取", "Signs"),
    /*
     * Help
     */
    HELP_HEADER("$3====== $1地皮帮助菜单 $3======", "Help"),
    HELP_CATEGORY("$1分类: $2%category%$2,$1 页数: $2%current%$3/$2%max%$2,$1 已显示: $2%dis%$3/$2%total%", "Help"),
    HELP_INFO("$3====== $1选择一个分类 $3======", false, "Help"),
    HELP_INFO_ITEM("$1/plots help %category% $3- $2%category_desc%", "Help"),
    HELP_ITEM("$1%usage% [%alias%]\n $3- $2%desc%\n", "Help"),
    /*
     * Direction
     */
    DIRECTION("$1当前方向: %dir%", "Help"),
    /*
     * Custom
     */
    CUSTOM_STRING("-", "-");
    /**
     * Special Language
     *
     * @see com.intellectualsites.translation.TranslationLanguage
     */
    protected final static TranslationLanguage lang = new TranslationLanguage("PlotSquared", "this", "use");
    public static String COLOR_1 = "&6", COLOR_2 = "&7", COLOR_3 = "&8", COLOR_4 = "&3";
    /**
     * The TranslationManager
     *
     * @see com.intellectualsites.translation.TranslationManager
     */
    private static TranslationManager manager;
    /**
     * The default file
     *
     * @see com.intellectualsites.translation.TranslationFile
     */
    private static TranslationFile defaultFile;
    /**
     * Default
     */
    private String d;
    /**
     * Translated
     */
    private String s;
    /**
     * What locale category should this translation fall under
     */
    private String cat;
    /**
     * Should the string be prefixed?
     */
    private boolean prefix;

    /**
     * Constructor for custom strings.
     */
    C() {
        /*
         * use setCustomString();
         */
    }

    /**
     * Constructor
     *
     * @param d default
     * @param prefix use prefix
     */
    C(final String d, final boolean prefix, String cat) {
        this.d = d;
        if (this.s == null) {
            this.s = "";
        }
        this.prefix = prefix;
        this.cat = cat.toLowerCase();
    }

    /**
     * Constructor
     *
     * @param d default
     */
    C(final String d, String cat) {
        this(d, true, cat.toLowerCase());
    }

    public static void setupTranslations() {
        manager = new TranslationManager();
        defaultFile = new YamlTranslationFile(BukkitTranslation.getParent(), lang, "PlotSquared", manager, true).read();
        // register everything in this class
        for (final C c : values()) {
            manager.addTranslationObject(new TranslationObject(c.toString(), c.d, "", ""));
        }
    }

    public static void saveTranslations() {
        try {
            manager.saveAll(defaultFile).saveFile(defaultFile);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the default string
     *
     * @return default
     */
    public String d() {
        return this.d;
    }

    /**
     * Get translated if exists
     *
     * @return translated if exists else default
     */
    public String s() {
        final String s = manager.getTranslated(toString(), lang).getTranslated().replaceAll("&-", "\n").replaceAll("\\n", "\n");
        return s.replace("$1", COLOR_1.toString()).replace("$2", COLOR_2.toString()).replace("$3", COLOR_3.toString()).replace("$4", COLOR_4.toString());
    }

    public boolean usePrefix() {
        return this.prefix;
    }

    /**
     * @return translated and color decoded
     *
     * @see org.bukkit.ChatColor#translateAlternateColorCodes(char, String)
     */
    public String translated() {
        return ChatColor.translateAlternateColorCodes('&', this.s());
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
