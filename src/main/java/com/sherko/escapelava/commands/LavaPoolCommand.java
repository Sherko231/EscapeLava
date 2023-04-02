package com.sherko.escapelava.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import com.sherko.escapelava.Main;
import com.sherko.escapelava.system.LavaRaiser;
import com.sherko.escapelava.system.SherkoScoreboard;

public class LavaPoolCommand extends Command {
    public LavaPoolCommand() {
        super("lavapool","set lava pool position");
        commandParameters.clear();
        //----- /lavapool set pos1 pos2
        commandParameters.put("set", new CommandParameter[]{
                CommandParameter.newEnum("set", new CommandEnum("setPool","set")),
                CommandParameter.newType("start-pos", CommandParamType.BLOCK_POSITION),
                CommandParameter.newType("end-pos", CommandParamType.BLOCK_POSITION)
        });
        //----- /lavapool reset
        commandParameters.put("reset", new CommandParameter[]{
                CommandParameter.newEnum("reset", new CommandEnum("resetPool","reset"))
        });
        //----- /lavapool raise
        commandParameters.put("raise", new CommandParameter[]{
                CommandParameter.newEnum("raise", new CommandEnum("raisePool","raise"))
        });
        //----- /lavapool autoraise <duration>
        commandParameters.put("autoraise", new CommandParameter[]{
                CommandParameter.newEnum("autoraise", new CommandEnum("autoRaisePool","autoraise")),
                CommandParameter.newType("raise-duration",CommandParamType.INT)
        });
        //----- /lavapool tpplayers
        commandParameters.put("tpplayers", new CommandParameter[]{
                CommandParameter.newEnum("tpplayers", new CommandEnum("tpPlayers","tpplayers")),
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args){
        if(!(sender instanceof Player player)) return false;
        if(!(player.hasPermission("EscapeLava.use"))){
            player.sendMessage(TextFormat.RED + "You are not admin");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(TextFormat.RED + "Wrong Syntax");
            return false;
        }
        switch(args[0]){
            //---- /zone set <statPos> <endPos>
            case "set" -> {
                if (args.length != 7) {
                    player.sendMessage(TextFormat.RED + "Wrong Syntax : please follow the correct syntax : /zone set <statPos> <endPos>");
                    return false;
                }

                try {

                    //get-Input:
                    double startIPosX = Double.parseDouble(args[1]);
                    double startIPosY = Double.parseDouble(args[2]);
                    double startIPosZ = Double.parseDouble(args[3]);
                    double endIPosX = Double.parseDouble(args[4]);
                    double endIPosY = Double.parseDouble(args[5]);
                    double endIPosZ = Double.parseDouble(args[6]);
                    Level level = player.getLevel();
                    Position startPos = new Position(startIPosX, startIPosY, startIPosZ, level);
                    Position endPos = new Position(endIPosX, endIPosY, endIPosZ, level);

                    //build lava pool:
                    LavaRaiser.setLavaPool(startPos,endPos);

                    //send confirmation message :
                    player.sendMessage(TextFormat.GREEN + "Lava Pool has been set successfully");

                } catch (NumberFormatException e) {
                    player.sendMessage(TextFormat.RED + "Wrong Syntax : Position's XYZ should be numbers");
                    return false;
                }
            }
            //---- /lavapool reset
            case "reset" -> {

                //reset:
                LavaRaiser.resetLavaPool();

                //message:
                player.sendMessage(TextFormat.GREEN + "Lava Pool has been disabled successfully");

                return false;
            }
            //---- /lavapool raise
            case "raise" -> {

                //raise:
                LavaRaiser.raiseLavaPool(1);

                //message:
                player.sendMessage(TextFormat.GREEN + "Lava Pool has been raised successfully");

                return false;
            }
            //---- /lavapool autoraise <duration>
            case "autoraise" -> {
                if (args.length != 2) {
                    player.sendMessage(TextFormat.RED + "Wrong Syntax : please follow the correct syntax : /lavapool autoraise <duration>");
                    return false;
                }

                //get input:
                int raiseDuration = Integer.parseInt(args[1]);

                //start auto raise:
                LavaRaiser.startAutoRaiseLavaPool(raiseDuration);

                //message:
                player.sendMessage(TextFormat.GREEN + "Lava Pool is now raising every : " + raiseDuration);

                return false;
            }
            //---- /lavapool tpplayers
            case "tpplayers" -> {

                //tp:
                tpAllPlayers(player);

                //message:
                player.sendMessage(TextFormat.GREEN + "All Players Teleported and got \"escaper\" tag ");

                //update scoreboard:
                SherkoScoreboard.updatePlayersAliveScorer();

                return false;
            }

            default -> {
                player.sendMessage(TextFormat.RED + "Wrong Input");
                return false;
            }
        }

        return false;
    }

    private void tpAllPlayers(Player commandSender){
        for (Player escaper : Main.INSTANCE.getServer().getOnlinePlayers().values()){
            if (escaper == commandSender) continue;
            if (!escaper.containTag("escaper")) escaper.addTag("escaper");
            escaper.teleport(commandSender);
        }
    }
}
