import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Arrays;
import java.util.List;

public class BuyPanel {

    private Image hoverImage;
    private boolean hover = false;
    private int selectedIndex;

    private static final Image buyPanel = ShadowDefend.getImageFile("buypanel");

    private final Image tank = ShadowDefend.getImageFile("tank");
    private final Image superTank = ShadowDefend.getImageFile("supertank");
    private final Image airplane = ShadowDefend.getImageFile("airsupport");

    private final Font keyBindFont = ShadowDefend.getFontFile("DejaVuSans-Bold", 12);
    private final Font moneyFont = ShadowDefend.getFontFile("DejaVuSans-Bold", 36);

    private PlaneDirection direction = PlaneDirection.HORIZONTAL;

    private final double STARTING_POINT = 64;
    private final double  DISTANCE_BETWEEN = 120;
    private final double X_CENTRE = buyPanel.getWidth()/2;
    private final double Y_CENTRE = buyPanel.getHeight()/2;

    private final List<Image> imageList = Arrays.asList(tank, superTank, airplane);
    private final List<Integer> towerPrice = Arrays.asList(250,600,500);

    private final String KEY_BIND_TEXT = "Key binds:\n" +
            "\n" +
            "S - Start Wave\n" +
            "L - Increase Timescale\n" +
            "K - Decrease Timescale";

    /**
     * Creates the BuyPanel object
     */
    public BuyPanel(){
    }

    /**
     * Runs the buy panel
     * @param input The user input
     */
    public void run(Input input){
        draw();
        placingTower(input);
        if (input.wasPressed(MouseButtons.LEFT)){
            selectTower(input.getMousePosition());
        }
    }

    private void draw(){
        buyPanel.drawFromTopLeft(0,0);
        keyBindFont.drawString(KEY_BIND_TEXT, X_CENTRE-20, 20,
                new DrawOptions().setBlendColour(Colour.WHITE));


        for ( int i = 0 ; i < imageList.size(); i++ ){
            Image curr = imageList.get(i);
            double imageX = STARTING_POINT + (i*DISTANCE_BETWEEN) - curr.getWidth()/2;
            double imageY =  Y_CENTRE - curr.getHeight()/2 - 10;
            curr.draw(STARTING_POINT + (i*DISTANCE_BETWEEN), Y_CENTRE - 10);
            priceDraw(towerPrice.get(i), imageX,imageY, curr );
        }

        moneyFont.drawString(String.format("$%d",ShadowDefend.money), Window.getWidth() - 200, 65);
    }


    private void selectTower(Point mousePoint){
        // Check if pointer is on the bounding box of the image
        for ( int i = 0 ; i < imageList.size() ; i++){
            Image curr = imageList.get(i);
            double imageX = STARTING_POINT + (i*DISTANCE_BETWEEN);
            double imageY =  Y_CENTRE  - 10;
            Rectangle boundBox = curr.getBoundingBoxAt(new Point(imageX,imageY));

            if ( ( boundBox.intersects(mousePoint) ) && !hover){
                hoverImage = curr;
                hover = true;
                selectedIndex = i;
                return;
            }
        }
    }

    private void placingTower(Input input){
        boolean onBudget = ( ShadowDefend.money >= towerPrice.get(selectedIndex) );
        if ( input.wasPressed(MouseButtons.RIGHT)){
            hover = false;
            ShadowDefend.status.setWaiting();
        }

        if ( hover && onBudget){
            Point mousePoint = getLimitedMousePoint(input.getMousePosition());
            ShadowDefend.status.setPlacing();

            // Checks whether the selected object is outside the panel itself
            boolean isOnPanel = buyPanel.getBoundingBox().intersects(
                    hoverImage.getBoundingBoxAt(mousePoint));

            boolean isOnRoute = false;
            // Checks whether the selected tower is hovering over a legal spot
            if ( !isOnPanel ) {
                isOnRoute = ShadowDefend.map.getPropertyBoolean((int)mousePoint.x,
                        (int) mousePoint.y, "blocked", false);
            }

            // Checks whether the point intersects the bounding box of other towers
            boolean isOverlap = false;
            for ( int i = 0 ; i < ShadowDefend.towers.size(); i++){
                if ( ShadowDefend.towers.get(i).getBoundingBox().intersects(mousePoint)){
                    isOverlap = true;
                    break;
                }
            }

            if (!isOnRoute && !isOverlap && !isOnPanel){
                hoverImage.draw(mousePoint.x, mousePoint.y);

                // Place on point
                if (input.wasPressed(MouseButtons.LEFT)){
                    ShadowDefend.towers.add(generateTower(selectedIndex, mousePoint));
                    hover = false;
                    ShadowDefend.money -= towerPrice.get(selectedIndex);
                    ShadowDefend.status.setWaiting();
                }
            }
        }
    }

    private void priceDraw( int item , double imageX, double imageY, Image image ){
        Font font = ShadowDefend.getFontFile("DejaVuSans-Bold", 20);
        Colour colour;
        // Not enough money to buy, will be Red
        if ( item > ShadowDefend.money ){
            colour = Colour.RED;
        }
        else {
            colour = Colour.GREEN;
        }

        font.drawString(String.format("$%s",Integer.toString(item)), imageX, Y_CENTRE + 40 ,
                new DrawOptions().setBlendColour(colour));
    }

    // Generate tower based on the index selected
    private Tower generateTower(int i, Point point){
        Tower tower = new Tank(point);

        if ( i == 1 ){
            tower = new SuperTank(point);
        }

        else if ( i == 2 ){
            tower = new Airplane(point, direction);
            alternate();
        }

        return tower;

    }

    // Alternate direction of airplane
    private void alternate(){
        if ( direction == PlaneDirection.VERTICAL) {
            direction = PlaneDirection.HORIZONTAL;
        }
        else if ( direction == PlaneDirection.HORIZONTAL){
            direction = PlaneDirection.VERTICAL;
        }
    }

    // Limits the mouse access from outside the window
    private Point getLimitedMousePoint( Point mousePoint ){
        double x = mousePoint.x;
        double y = mousePoint.y;

        // Limiting X coordinate
        if ( x > bagel.Window.getWidth() - hoverImage.getWidth()/4){
            x = bagel.Window.getWidth() - hoverImage.getWidth()/4;
        }
        else if ( x < 0 ){
            x = 0;
        }

        // Limiting Y coordinate
        if ( y > bagel.Window.getHeight() - hoverImage.getHeight()/4 - StatusPanel.getHeight()){
            y = bagel.Window.getHeight() - hoverImage.getHeight()/4 - StatusPanel.getHeight();
        }
        else if ( y < 0 ){
            y = 0;
        }
        return new Point(x,y);

    }

    /**
     * Gets the image of the buy panel
     * @return Image of the buy panel
     */
    public static Image getImage(){
        return buyPanel;
    }
}
