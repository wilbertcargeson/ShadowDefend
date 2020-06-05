import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;


public abstract class Slicer extends Sprite{

    protected final List<Point> trail;

    private int index;
    private final int maxIndex;

    // Default slicer stats
    public final double REGULAR_SPEED = 2.0;

    public final int REGULAR_HEALTH = 1;
    public final int REGULAR_REWARD = 2;
    public final int REGULAR_PENALTY = 1;

    // Slicer.Slicer stats
    protected double speed = REGULAR_SPEED;

    protected int health = REGULAR_HEALTH;
    protected int reward = REGULAR_REWARD;
    protected int penalty = REGULAR_PENALTY;

    private final double UPDATE_LENGTH = 1;


    protected List<Slicer> children = new ArrayList<>();

    public Slicer(List<Point> trail) {
        this.trail = trail;
        spriteX = this.trail.get(0).x;
        spriteY = this.trail.get(0).y;
        index = 1;
        maxIndex = this.trail.size();

        // Default slicer image
        image = new Image("res/images/slicer.png");
    }

    @Override
    public void run(){
        // Move slicer
        double count = speed;
        for ( int i = 0 ; i<ShadowDefend.timescale ; i++) {
            if ( index < maxIndex) {
                updateSlicer();
            }
        }
        drawSprite();

        // If reach end of polyline
        if ( index >= maxIndex ){
            ShadowDefend.inflictDamage(penalty);
            ShadowDefend.slicers.remove(this);
        }
    }

    // Update slicer utilizing vectors
    public void updateSlicer(){
        Point target = trail.get(index);
        // If the slicer has arrived at the point, go to next
        if (moveSpriteTo(target)== 0) {
            if ( index < maxIndex){
                index++;
            }
        }
    }

    // Decrease health if damaged
    public void damaged ( int damage ){
        health -= damage;
    }

    // Spawn child slicers on destroy
    public void spawnChildren(){
        for ( int i = 0 ; i < children.size(); i++ ){
            Slicer child = children.get(i);
            child.setXY(this.getPoint());
            child.setIndex(this.index);
            ShadowDefend.slicers.add( child );

        }
    }

    public boolean dead(){return health <= 0;}

    public void aftermath(){
        spawnChildren();
        ShadowDefend.earnReward(reward);
    }

    public abstract void createChild();
    // Setters and Getters
    public int getPenalty(){ return penalty;}
    public void setIndex( int index ){this.index = index;}

}