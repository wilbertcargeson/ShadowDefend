public class DelayEvent extends Event{

    private int count = 0;
    private int delayCount;

    /**
     * Creates delay event on wave
     * @param duration The duration of the delay
     */
    public DelayEvent(int duration){
        delayCount = duration * ShadowDefend.FPS;
    }

    /**
     * Runs the delay
     * @return Returns true if the delay has been completed
     */
    @Override
    public boolean run() {
        if ( count >= delayCount ){
            return false;
        }
        count += ShadowDefend.timescale;
        return true;
    }
}
