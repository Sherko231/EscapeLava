package com.sherko.escapelava.system;

import cn.nukkit.Server;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockLava;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import com.sherko.escapelava.Main;
import org.jetbrains.annotations.NotNull;

public class LavaRaiser {

    //=======[Fields]=======
    private static Position startPos;
    private static Position endPos;
    private static double lavaPoolHeight;

    private static final Server server = Main.INSTANCE.getServer();

    //=======[Get-Set]=======

    //=======[Functions]=======
    public static void setLavaPool(@NotNull Position startPos, @NotNull Position endPos) {
        server.getScheduler().cancelAllTasks();

        setPositions(startPos,endPos);
        buildLavaPool();

    }

    public static void raiseLavaPool(double raiseAmount){
        if (lavaPoolHeight == endPos.y) return;
        lavaPoolHeight += raiseAmount;
        buildLavaPool();
    }

    public static void startAutoRaiseLavaPool(int delay){
        server.getScheduler().scheduleDelayedRepeatingTask(Main.INSTANCE, ()-> {

            if (lavaPoolHeight == endPos.y) {
                server.getScheduler().cancelAllTasks();
                return;
            }
            raiseLavaPool(1);

            },delay,delay,false);

    }

    public static void resetLavaPool(){
        Level level = startPos.getLevel();
        server.getScheduler().cancelAllTasks();

        //Remove Pool:
        for(double x = LavaRaiser.startPos.x; x <= LavaRaiser.endPos.x; x++){
            for(double y = LavaRaiser.startPos.y; y <= LavaRaiser.endPos.y; y++){
                for(double z = LavaRaiser.startPos.z; z <= LavaRaiser.endPos.z; z++) {

                    //Replace Lava with Air:
                    Position currentPos = new Position(x, y, z, level);
                    if (level.getBlock(currentPos) instanceof BlockLava) {
                        level.setBlock(currentPos, new BlockAir());
                    }
                }
            }
        }
    }

    private static void setPositions(@NotNull Position startPos, @NotNull Position endPos){

        //Set Start & End Positions:
        double minX = Math.min(startPos.x,endPos.x);
        double minY = Math.min(startPos.y,endPos.y);
        double minZ = Math.min(startPos.z,endPos.z);
        double maxX = Math.max(startPos.x,endPos.x);
        double maxY = Math.max(startPos.y,endPos.y);
        double maxZ = Math.max(startPos.z,endPos.z);
        LavaRaiser.startPos = new Position(minX,minY,minZ, startPos.getLevel());
        LavaRaiser.endPos = new Position(maxX,maxY,maxZ,endPos.getLevel());
        LavaRaiser.lavaPoolHeight = startPos.y;

    }

    private static void buildLavaPool(){
        Level level = startPos.getLevel();

        //Build Pool:
        for(double x = LavaRaiser.startPos.x; x <= LavaRaiser.endPos.x; x++){
            for(double z = LavaRaiser.startPos.z; z <= LavaRaiser.endPos.z; z++){

                //Replace Air with Lava:
                Position currentPos = new Position(x, LavaRaiser.lavaPoolHeight, z, level);
                if(level.getBlock(currentPos) instanceof BlockAir) {
                    level.setBlock(currentPos, new BlockLava());
                }
            }
        }
    }


}
