package com.sherko.escapelava.listeners;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandUseListener implements Listener {

    @EventHandler
    public void onCommandSend(PlayerCommandPreprocessEvent e){
        if (!(e.getPlayer().hasPermission("HideAndSeek.use"))){
            e.setCancelled();
        }
    }

}
