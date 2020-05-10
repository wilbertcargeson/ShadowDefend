public class Spawner {

    private Slicer slicers[];
    private double interval;
    private int attackerQty;

    private int updateCount;

    private final int FPS = 60;

    private double intervalFrame;
    private double timescale = ShadowDefend.BASE_TIMESCALE;

    public Spawner(Slicer slicers[],double interval, int attackerQty) {
        this.slicers = slicers;
        this.attackerQty = attackerQty;
        this.interval = interval;
        updateCount = 0;
        intervalFrame = FPS * interval;
    }

    // Spawns a wave of the attacker
    public boolean waveSpawn(){
        // Checks if the last attacker have reach the end
        if ( slicers[attackerQty-1].getIndex() >= slicers[attackerQty-1].getMaxIndex()) {
            return false;
        }
        for ( int i = 0; i < attackerQty; i++){
            if ( (i * intervalFrame) <= updateCount ){
                slicers[i].spawn();
            }
        }
        updateCount += timescale;
        return true;
    }

    public void setTimescale(double t){
        timescale = t;
        // Update timescale for all attackers
        for ( int i = 0 ; i < attackerQty ; i++){
            slicers[i].setTimescale(t);
        }
    }

}
