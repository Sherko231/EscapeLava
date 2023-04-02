package com.sherko.escapelava.listeners;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Sound;
import cn.nukkit.utils.TextFormat;
import com.sherko.escapelava.Main;
import com.sherko.escapelava.system.LavaRaiser;
import com.sherko.escapelava.system.SherkoScoreboard;

public class ScoreboardUpdater implements Listener {

    private final Server server = Main.INSTANCE.getServer();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        SherkoScoreboard.updateOnlinePlayersScorer();

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        //remove tag:
        if (e.getPlayer().containTag("escaper")) {
            e.getPlayer().removeTag("escaper");
        }

        //update Scoreboard:
        server.getScheduler().scheduleDelayedTask(Main.INSTANCE, SherkoScoreboard::updateOnlinePlayersScorer, 10, true);
        server.getScheduler().scheduleDelayedTask(Main.INSTANCE, SherkoScoreboard::updatePlayersAliveScorer, 10, true);

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){

        //remove tag:
        if (e.getEntity().containTag("escaper")) {
            e.getEntity().removeTag("escaper");
        }

        //update scoreboard:
        SherkoScoreboard.updatePlayersAliveScorer();

        //if 1 Player is remaining:
        if (SherkoScoreboard.getPlayersAlive() == 1) {

            //reset Lava Pool:
            LavaRaiser.resetLavaPool();

            //get winner :
            for (Player player : server.getOnlinePlayers().values()){
                if (player.containTag("escaper")) {

                    for (Player p : server.getOnlinePlayers().values()) {
                        p.sendTitle(TextFormat.BOLD.toString() + TextFormat.GREEN + "The Winner is", player.getName());
                        p.sendMessage(TextFormat.BOLD.toString() + TextFormat.GREEN + "The Winner is: " + player.getName());
                        p.getLevel().addSound(p.getPosition(), Sound.RANDOM_LEVELUP, 1, 1, p);
                    }
                    break;
                }
            }

        }

    }





}
