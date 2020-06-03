import java.util.List;

public class SlicerSpawn {

    private List<Slicer> waveSlicers;
    private double intervalFrame;
    private double updateCount;


    public SlicerSpawn(List<Slicer> slicers, double interval) {
        this.waveSlicers = slicers;
        intervalFrame = ShadowDefend.FPS * interval;
        updateCount = intervalFrame;
    }

    // Spawns a wave of the attacker
    public boolean runWave(){
        spawnSlicer();
        // Checks if the last attacker have reach the end
        if ( ( waveSlicers.size() == 0) && ( ShadowDefend.slicers.size() == 0) ){
            return false;
        }
        for ( int i = 0; i < ShadowDefend.slicers.size(); i++) {
            // Update only if not end on line
            ShadowDefend.slicers.get(i).spawn();
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
