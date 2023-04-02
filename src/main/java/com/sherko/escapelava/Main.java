package com.sherko.escapelava;

import cn.nukkit.command.CommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import com.sherko.escapelava.commands.LavaPoolCommand;
import com.sherko.escapelava.commands.PermanentSpawnCommand;
import com.sherko.escapelava.listeners.CommandUseListener;
import com.sherko.escapelava.listeners.PlayerJoinTeleporter;
import com.sherko.escapelava.listeners.ScoreboardUpdater;
import com.sherko.escapelava.system.SherkoScoreboard;

public class Main extends PluginBase{
    public static Main INSTANCE;

    //tags: escaper

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        SherkoScoreboard.makeScoreboard();
        SherkoScoreboard.updatePlayersAliveScorer();

        registerCommands();
        registerEvents();
    }

    private void registerCommands(){
        CommandMap cm = getServer().getCommandMap();
        cm.register("setpermanentspawn",new PermanentSpawnCommand());
        cm.register("setpermanentspawn",new LavaPoolCommand());
    }

    private void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CommandUseListener(),this);
        pm.registerEvents(new PlayerJoinTeleporter(),this);
        pm.registerEvents(new ScoreboardUpdater(),this);
    }
}
