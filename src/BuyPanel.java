import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Arrays;
import java.util.List;

public class BuyPanel {

    private Image hoverImage;
    private boolean hover = false;
    private int selectedIndex ;

    private final Image buyPanel = ShadowDefend.getImageFile("buypanel");
    private final Image tank = ShadowDefend.getImageFile("tank");
    private final Image superTank = ShadowDefend.getImageFile("supertank");
    private final Image airplane = ShadowDefend.getImageFile("airsupport");

    private final Font keyBindFont = ShadowDefend.getFontFile("DejaVuSans-Bold", 12);
    private final Font moneyFont = ShadowDefend.getFontFile("DejaVuSans-Bold", 36);


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


    public BuyPanel(){
    }

    public void draw(){
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

    public void priceDraw( int item , double imageX, double imageY, Image image ){
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

    public void isClicked(Point mousePoint){
        // Check if pointer is on the bounding box of the image
        for ( int i = 0 ; i < imageList.size() ; i++){
            Image curr = imageList.get(i);
            double imageX = STARTING_POINT + (i*DISTANCE_BETWEEN);
            double imageY =  Y_CENTRE  - 10;
            Rectangle boundBox = curr.getBoundingBoxAt(new Point(imageX,imageY));

            if ( ( boundBox.intersects(mousePoint) ) && !hover){
                System.out.printf("Tower %d selected\n", i);
                hoverImage = curr;
                hover = true;
                selectedIndex = i;
                return;
            }
        }
    }

    public void towerSelected(Input input){
        boolean onBudget = ( ShadowDefend.money >= towerPrice.get(selectedIndex) );

        if ( input.wasPressed(MouseButtons.RIGHT)){
            hover = false;
        }

        if ( hover && onBudget){
            Rectangle boundBox = hoverImage.getBoundingBoxAt(input.getMousePosition());

            // Checks whether the selected tower is hovering over a legal spot
            boolean isOnRoute = ShadowDefend.map.getPropertyBoolean((int)input.getMouseX(),
                    (int)input.getMouseY(),"blocked", false);

            // Checks whether the point intersects the bounding boc of other towers
            boolean isOverlap = false;
            for ( int i = 0 ; i < ShadowDefend.towers.size(); i++){
                if ( ShadowDefend.towers.get(i).getBoundingBox().intersects(input.getMousePosition())){
                    isOverlap = true;
                    break;
                }
            }

            // Checks whether the selected object is outside the panel itself
            boolean isOnPanel = buyPanel.getBoundingBox().intersects(
                    hoverImage.getBoundingBoxAt(input.getMousePosition()));

            if (!isOnRoute && !isOverlap && !isOnPanel){
                hoverImage.draw(input.getMouseX(), input.getMouseY());

                // Place on point
                if (input.wasPressed(MouseButtons.LEFT)){
                    ShadowDefend.towers.add(new Tank(input.getMousePosition(), hoverImage));
                    hover = false;
                    ShadowDefend.money -= towerPrice.get(selectedIndex);
                }
            }
        }
    }


}
