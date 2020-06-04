import bagel.util.Rectangle;
import bagel.util.Point;

public abstract class Projectile extends Sprite{
    protected Slicer target;
    protected int damage = 1;
    protected double rad;

    private final int speed = 10;

    public Projectile( Point start, Slicer target){
        this.target = target;

        image = ShadowDefend.getImageFile("tank_projectile");
        spriteX = start.x;
        spriteY = start.y;
    }

    public Projectile(Point start) {
        super();
    }

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

    boolean isHit(){
        Rectangle boundBox = this.getBoundingBox();
        if ( boundBox.intersects(target.getPoint())){
            return true;
        }
        return false;
    }

}
