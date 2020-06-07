public class DelayEvent extends Event{

    private int count = 0;
    private int delayCount;

    public DelayEvent(int duration){
        delayCount = duration * ShadowDefend.FPS;
    }

    public boolean run() {
        if ( count >= delayCount ){
            return false;
        }
        count += ShadowDefend.timescale;
        return true;
    }
}
