import bagel.util.Point;
import bagel.util.Vector2;

public class Airplane extends Tower{

    private final double MIN = 0;
    private final double MAX = 3;
    private final int speed = 5;

    // Horizontal
    private Point startPoint;
    private Point endPoint;


    protected Airplane(Point point, int direction) {
        super(point);

        // Horizontal
        if ( direction == 0 ) {
            startPoint = new Point(0, point.y);
            endPoint = new Point(bagel.Window.getWidth() + image.getHeight(), point.y);
            rad = Math.PI/2;
        }
        //Vertical
        else {
            startPoint = new Point(point.x, 0);
            endPoint = new Point(point.x, bagel.Window.getHeight() + image.getHeight());
            rad = Math.PI;
        }

        image = ShadowDefend.getImageFile("airsupport");
        this.setXY(startPoint);
        cooldown = randomInt() * ShadowDefend.FPS;
        cooldownFrameCount = 0;
    }

    @Override
    public void run(){

        // Checks whether the selected object is outside the panel itself
        boolean isOffPanel = !BuyPanel.buyPanel.getBoundingBox().intersects(this.getBoundingBox());

        // Drop explosive
        if ( ready() && isOffPanel ){
            dropExplosives();
        }

        // Fly into the point and beyond
        for ( int i = 0 ; i < speed; i++) {
            double distance = moveSpriteTo(endPoint);
            if (distance == 0) {
                ShadowDefend.towers.remove(this);
            }
        }

        drawSprite();
    }

    // Generate random int between MIN n MAX
    public double randomInt(){
        double x = (int)(Math.random()*((MAX-MIN)+1))+MIN;
        return x;
    }

    // Drop explosive
    public void dropExplosives(){
        ShadowDefend.projectiles.add(new Explosive(this.getPoint()));
        cooldown = randomInt() * ShadowDefend.FPS;
        cooldownFrameCount = 0;
    }

    @Override
    // Update sprite coordinates to move towards the target, returns the distance moved
    public double moveSpriteTo( Point target ){
        Vector2 vector2 = new Vector2(target.x - spriteX, target.y - spriteY);
        Point update = vector2.normalised().asPoint();
        spriteX += update.x;
        spriteY += update.y;
        return roundHundredth(vector2.length());
    }

}
