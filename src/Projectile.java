import bagel.util.Rectangle;
import bagel.util.Point;

public abstract class Projectile extends Sprite{
    protected Slicer target;
    protected int damage;
    private final int speed = 10;

    public static final int DEFAULT_DAMAGE = 1;

    /**
     * Create a mobile projectile
     * @param start Where the projectile is starting
     * @param target Where the projectile is going to
     */
    public Projectile( Point start, Slicer target){
        this.target = target;
        image = ShadowDefend.getImageFile("tank_projectile");
        damage = DEFAULT_DAMAGE;
        spriteX = start.x;
        spriteY = start.y;
    }

    /**
     * Create a immobile projectile
     * @param start Where the projectile is placed
     */
    public Projectile(Point start) {
        spriteX = start.x;
        spriteY = start.y;
    }

    /**
     * Runs the projectile
     */
    @Override
    public void run(){

        for ( int i = 0 ; i < speed ; i++ ) {
            moveSpriteTo(target.getPoint());
            if (isHit()) {
                target.damaged(damage);
                ShadowDefend.projectiles.remove(this);
                return;
            }
            drawSprite();
        }
    }

    protected boolean isHit(){
        Rectangle boundBox = this.getBoundingBox();
        if ( boundBox.intersects(target.getPoint())){
            return true;
        }
        return false;
    }

}
