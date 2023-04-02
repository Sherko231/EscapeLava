package com.sherko.escapelava.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import com.sherko.escapelava.Main;


public class PlayerJoinTeleporter implements Listener {

    private static final Config config = Main.INSTANCE.getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        //teleport & setSpawn player to Permanent Spawn :
        double SpawnX = Double.parseDouble(config.get("SpawnPosX").toString());
        double SpawnY = Double.parseDouble(config.get("SpawnPosY").toString());
        double SpawnZ = Double.parseDouble(config.get("SpawnPosZ").toString());
        player.teleport(new Vector3(SpawnX,SpawnY,SpawnZ));
        player.setSpawn(new Vector3(SpawnX,SpawnY,SpawnZ));

    }
}
