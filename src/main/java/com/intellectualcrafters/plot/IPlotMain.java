package com.intellectualcrafters.plot;

import java.io.File;
import java.util.UUID;

import org.bukkit.generator.ChunkGenerator;

import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.generator.HybridUtils;
import com.intellectualcrafters.plot.listeners.APlotListener;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.BlockManager;
import com.intellectualcrafters.plot.util.ChunkManager;
import com.intellectualcrafters.plot.util.EconHandler;
import com.intellectualcrafters.plot.util.EventUtil;
import com.intellectualcrafters.plot.util.PlayerManager;
import com.intellectualcrafters.plot.util.SetupUtils;
import com.intellectualcrafters.plot.util.TaskManager;
import com.intellectualcrafters.plot.uuid.UUIDWrapper;

public interface IPlotMain {
    void log(String message);

    File getDirectory();

    void disable();

    String getVersion();

    void handleKick(UUID uuid, C c);

    TaskManager getTaskManager();

    void runEntityTask();

    void registerCommands();

    void registerPlayerEvents();

    void registerInventoryEvents();

    void registerPlotPlusEvents();

    void registerForceFieldEvents();

    void registerWorldEditEvents();
    
    void registerTNTListener();

    EconHandler getEconomyHandler();

    BlockManager initBlockManager();
    
    EventUtil initEventUtil();

    ChunkManager initChunkManager();

    SetupUtils initSetupUtils();

    HybridUtils initHybridUtils();

    UUIDWrapper initUUIDHandler();

    boolean initPlotMeConverter();
    
    void unregister(PlotPlayer player);

    ChunkGenerator getGenerator(String world, String name);

    APlotListener initPlotListener();

    void registerChunkProcessor();

    void registerWorldEvents();

    PlayerManager initPlayerManager();
    
    public boolean checkVersion(int major, int minor, int minor2);
}
