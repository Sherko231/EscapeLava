package com.sherko.escapelava.commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import com.sherko.escapelava.Main;

public class PermanentSpawnCommand extends Command {
    public PermanentSpawnCommand() {
        super("setpermanentspawn","sets Permanent Spawn Point");
    }

    private final Server server = Main.INSTANCE.getServer();
    private static final Config config = Main.INSTANCE.getConfig();

    public boolean execute(CommandSender sender, String commandLabel, String[] args){
        if(!(sender instanceof Player player)) return false;
        if(!player.hasPermission("HideAndSeek.use")) {
            player.sendMessage(TextFormat.RED + "You are not admin");
            return false;
        }

        //set permanent spawn point in config :
        config.set("SpawnPosX",player.getPosition().x);
        config.set("SpawnPosY",player.getPosition().y);
        config.set("SpawnPosZ",player.getPosition().z);
        config.save();

        //set world spawn :
        server.getDefaultLevel().setSpawnLocation(
                new Vector3(player.getPosition().x,player.getPosition().y,player.getPosition().z)
        );

        player.sendMessage(TextFormat.YELLOW + "Permanent spawn point has been set");

        return false;
    }

}
