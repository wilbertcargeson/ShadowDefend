import java.util.List;

public class SlicerSpawn extends Event {

    private List<Slicer> waveSlicers;
    private double intervalFrame;
    private double updateCount;


    public SlicerSpawn(List<Slicer> slicers, double interval) {
        this.waveSlicers = slicers;
        intervalFrame = ShadowDefend.FPS * interval;
        updateCount = intervalFrame;
    }

    // Spawns a wave of the attacker, returns false if no more to spawn
    public boolean run(){
        spawnSlicer();
        if ( ( waveSlicers.size() == 0) ){
            return false;
        }
        updateCount += ShadowDefend.timescale;
        return true;
    }

    public void spawnSlicer(){
        // Add if spawning not on cooldown
        if ( ( updateCount >= intervalFrame) && (waveSlicers.size() > 0) ){
            updateCount = 0;
            Slicer slicer = waveSlicers.get(0);
            ShadowDefend.slicers.add(slicer);
            waveSlicers.remove(0);
        }
    }



}
