import java.util.List;

public class SlicerSpawn extends Event {

    private List<Slicer> waveSlicers;
    private double intervalFrame;
    private double updateCount;

    /**
     * Creates slicer spawner that spawns slicers at an interval
     * @param slicers List of slicer that is needed to be spawned
     * @param interval The interval between slicers
     */
    public SlicerSpawn(List<Slicer> slicers, double interval) {
        this.waveSlicers = slicers;
        intervalFrame = ShadowDefend.FPS * interval;
        updateCount = intervalFrame;
    }

    /**
     * Spawns a wave of slicer
     * @return False if no more slicers to spawn
     */
    public boolean run(){
        spawnSlicer();
        if ( ( waveSlicers.size() == 0) ){
            return false;
        }
        updateCount += ShadowDefend.timescale;
        return true;
    }

    private void spawnSlicer(){
        // Add if spawning not on cooldown
        if ( ( updateCount >= intervalFrame) && (waveSlicers.size() > 0) ){
            updateCount = 0;
            Slicer slicer = waveSlicers.get(0);
            ShadowDefend.slicers.add(slicer);
            waveSlicers.remove(0);
        }
    }



}
