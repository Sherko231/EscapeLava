package com.sherko.escapelava.system;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scoreboard.data.DisplaySlot;
import cn.nukkit.scoreboard.data.SortOrder;
import cn.nukkit.scoreboard.scoreboard.Scoreboard;
import cn.nukkit.scoreboard.scoreboard.ScoreboardLine;
import cn.nukkit.scoreboard.scorer.FakeScorer;
import cn.nukkit.utils.TextFormat;
import com.sherko.escapelava.Main;

public class SherkoScoreboard {

    //============Fields============
    private static Scoreboard scoreboard;
    private static FakeScorer onlinePlayersScorer;
    private static FakeScorer playersAliveScorer;
    private static int playersAlive = 0;

    private static final Server server = Main.INSTANCE.getServer();
    //============Get-Set============

    public static int getPlayersAlive() {
        return playersAlive;
    }

    //============Functions============
    public static void makeScoreboard(){

        int playersOnline = server.getOnlinePlayers().size();
        //==========================================
        scoreboard = new Scoreboard(
                "main",
                TextFormat.BOLD.toString() + TextFormat.OBFUSCATED + "||" +TextFormat.RESET +
                        TextFormat.BOLD + TextFormat.GOLD + "SYRKING " + TextFormat.AQUA + "EVENT" +
                        TextFormat.BOLD + TextFormat.OBFUSCATED + TextFormat.WHITE + "||" +TextFormat.RESET,
                "dummy",
                SortOrder.ASCENDING);

        //==========================================
        onlinePlayersScorer = new FakeScorer(TextFormat.GREEN + "■ Players Online : " + TextFormat.YELLOW + playersOnline);
        playersAliveScorer = new FakeScorer(TextFormat.RED + "■ Players Alive : " + TextFormat.YELLOW + playersAlive);
        //==========================================
        ScoreboardLine line1 = new ScoreboardLine(scoreboard, new FakeScorer("-------------------  ") ,1);
        ScoreboardLine line2 = new ScoreboardLine(scoreboard, new FakeScorer("-------------------") ,5);
        ScoreboardLine onlineLine = new ScoreboardLine(scoreboard, onlinePlayersScorer, 6);
        ScoreboardLine line3 = new ScoreboardLine(scoreboard, new FakeScorer("------------------- ") ,7);
        ScoreboardLine discordLine = new ScoreboardLine(scoreboard, new FakeScorer(TextFormat.LIGHT_PURPLE + "discord.gg/syk") ,8);
        scoreboard.addLine(line1);
        scoreboard.addLine(line2);
        scoreboard.addLine(onlineLine);
        scoreboard.addLine(line3);
        scoreboard.addLine(discordLine);

        //==========================================
        server.getScoreboardManager().setDisplay(DisplaySlot.SIDEBAR,scoreboard);

    }

    public static void updateOnlinePlayersScorer(){

        int playersOnline = server.getOnlinePlayers().size();

        scoreboard.removeLine(onlinePlayersScorer);

        onlinePlayersScorer = new FakeScorer(TextFormat.GREEN + "■ Players Online : " + TextFormat.YELLOW + playersOnline);

        scoreboard.addLine(onlinePlayersScorer, 5);

        System.out.println("players online = " + playersOnline);
    }

    public static void updatePlayersAliveScorer(){
        updatePlayersAliveCount();

        if (playersAliveScorer != null) scoreboard.removeLine(playersAliveScorer);
        playersAliveScorer = new FakeScorer(TextFormat.RED + "■ Players Alive : " + TextFormat.YELLOW + playersAlive);
        scoreboard.addLine(playersAliveScorer,3);
    }

    private static void updatePlayersAliveCount(){
        playersAlive = 0;
        for (Player player : server.getOnlinePlayers().values()){
            if(player.containTag("escaper")){
                playersAlive++;
            }
        }
    }



}
