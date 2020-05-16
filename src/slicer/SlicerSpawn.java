package slicer;

import main.*;

import java.util.List;

public class SlicerSpawn {

    private List<Slicer> slicers;
    private double interval;
    private int attackerQty;

    private int updateCount;

    private final int FPS = 60;

    private double intervalFrame;
    private double timescale = ShadowDefend.BASE_TIMESCALE;

    public SlicerSpawn(List<Slicer> slicers, double interval) {
        this.slicers = slicers;
        this.interval = interval;
        attackerQty = slicers.size();
        updateCount = 0;
        intervalFrame = FPS * interval;
    }

    // Spawns a wave of the attacker
    public boolean waveSpawn(){
        // Checks if the last attacker have reach the end
        if ( slicers.get(attackerQty-1).getIndex() >= slicers.get(attackerQty-1).getMaxIndex()) {
            System.out.println("Done");
            return false;
        }
        for ( int i = 0; i < attackerQty; i++){
            if ( (i * intervalFrame) <= updateCount ){
                slicers.get(i).spawn();
            }
        }
        updateCount += timescale;
        return true;
    }

    public void setTimescale(double t){
        timescale = t;
        // Update timescale for all attackers
        for ( int i = 0 ; i < attackerQty ; i++){
            slicers.get(i).setTimescale(t);
        }
    }

}
