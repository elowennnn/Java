
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.image.Image;



public class Ragdoll extends Application{
    final int screen_width = 1024;
    final int screen_height = 768;

    long eventCount = 0L;
    double eventSumDx = 0.0;
    double eventSumDy = 0.0;
    double eventSumDist = 0.0;
    int processingInterval = 3; // process every n mouse events
    double head_restrictions = 0.872664626;
    double arm_restrictions = 2.35619449;
    double hand_restrictions = 0.610865238;
    double leg_restrictions = 1.57079633;
    double feet_restrictions = 0.610865238;
    double restrictions = 6.28318531;
    boolean head_flag = false;
    boolean arm_flag = false;
    boolean hand_flag = false;
    boolean leg_flag = false;
    boolean feet_flag = false;

    double previous_x, previous_y;
    Sprite selectedSprite;
    Sprite root;

    private boolean isAltDown = false;
    private boolean isShiftDown = false;
    private boolean isCtrlDown = false;

    @Override
    public void start(Stage stage){
        // setup a canvas to use as a drawing surface
        Canvas canvas = new Canvas(screen_width, screen_height);
        MenuBar menu = get_menu();
        Scene scene = new Scene(new VBox(menu,canvas), screen_width, screen_height);

        // create hierarchy of sprites
        Sprite root = createSprites();

        // add listeners

        // canvas is not traversable by default
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(event -> {
            isAltDown = event.isAltDown();
            isShiftDown = event.isShiftDown();
            isCtrlDown = event.isControlDown();
            if (isCtrlDown && event.getCode() == KeyCode.R) {
                reset();
            }
            if (isCtrlDown && event.getCode() == KeyCode.Q) {
                System.exit(0);
            }
            draw(canvas, root);
        });

        canvas.setOnKeyReleased(event -> {
            isAltDown = event.isAltDown();
            isShiftDown = event.isShiftDown();
            draw(canvas, root);
        });

        // click selects the shape under the cursor
        // we have sprites do it since they track their own locations
        canvas.setOnMousePressed(mouseEvent -> {
            Sprite hit = root.getSpriteHit(mouseEvent.getX(), mouseEvent.getY());
            if (hit != null) {
                selectedSprite = hit;
                where();
                previous_x = mouseEvent.getX();
                previous_y = mouseEvent.getY();
            }
            draw(canvas, root);
        });

        // un-selects any selected shape
        canvas.setOnMouseReleased( mouseEvent -> {
            selectedSprite = null;
            //System.out.println("Unselected");
        });

        // dragged translates the shape based on change in mouse position
        // since shapes are defined relative to one another, they will follow their parent
        canvas.setOnMouseDragged(mouseEvent -> {
            if (selectedSprite != null) {
                double dx = mouseEvent.getX() - previous_x;
                double dy = mouseEvent.getY() - previous_y;

                eventCount++;
                eventSumDx += dx;
                eventSumDy += dy;

                if (selectedSprite.canTranslate && eventCount >= processingInterval) {
                    selectedSprite.translate(eventSumDx, eventSumDy);
                }

                if (selectedSprite.canScale && isShiftDown && eventCount >= processingInterval) {
                    try {
                        if (eventSumDx < 0.5) { //moving right
                            selectedSprite.scale(1.05, 1.05, selectedSprite.startingX, selectedSprite.startingY);
                        } else if (eventSumDx > 0.05) { // moving left
                            selectedSprite.scale(0.95, 0.95,selectedSprite.startingX, selectedSprite.startingY);
                        }
                    } catch (NonInvertibleTransformException e) {
                        e.printStackTrace();
                    }
                }

                if (selectedSprite.canRotate && eventCount >= processingInterval) {
                    eventSumDist += Math.sqrt(Math.pow(mouseEvent.getX() - previous_x, 2) + Math.pow(mouseEvent.getY() - previous_y, 2));
                   // System.out.println(eventSumDist);
                    double theta = Math.atan(eventSumDist);
                    try {
                        restriction();
                        if (theta < restrictions && theta > -restrictions){
                            if (eventSumDx > 0.0) {
                                selectedSprite.rotate(-theta, selectedSprite.startingX, selectedSprite.startingY);
                            } else {
                                selectedSprite.rotate(theta, selectedSprite.startingX, selectedSprite.startingY);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // save coordinates for next event
                previous_x = mouseEvent.getX();
                previous_y = mouseEvent.getY();
            }

            // draw tree in new position
            draw(canvas, root);

            if (eventCount%processingInterval == 0) {
                eventCount = 0;
                eventSumDx = 0.0;
                eventSumDy = 0.0;
                eventSumDist = 0.0;
            }
        });

        // draw the sprites on the canvas
        draw(canvas, root);

        // show the scene including the canvas
        stage.setScene(scene);
        stage.show();
    }
    private void draw(Canvas canvas, Sprite root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // instructions
        gc.setStroke(Color.BLACK);
        gc.setFont(new Font("Arial", 14));


        // root of scene graph (and recurse through it)
        root.draw(gc);
    }

    private void reset() {
        root.reset();
    }

    private Sprite createSprites() {
        // create a bunch of different sprites at the origin
        Sprite torso = new RectangleSprite(80, 150, true, false, false);
        Sprite head = new RectangleSprite(40, 40, false, true, true);

        Sprite left_shoudler = new RectangleSprite(30, 15, false, false, false);
        Sprite right_shoudler = new RectangleSprite(30, 15, false, false, false);


        Sprite left_arm = new RectangleSprite(15, 80, false, true, true);
        Sprite right_arm = new RectangleSprite(15, 80, false, true, true);

        Sprite left_down_arm = new RectangleSprite(15,80,false,true,true);
        Sprite right_down_arm = new RectangleSprite(15,80,false,true,true);

        Sprite right_hand = new RectangleSprite(15,15,false,true,true);
        Sprite left_hand = new RectangleSprite(15,15,false,true,true);

        Sprite left_leg = new RectangleSprite(15,80,false,true,true);
        Sprite right_leg = new RectangleSprite(15,80,false,true,true);

        Sprite left_down_leg = new RectangleSprite(15,80,false,true,true);
        Sprite right_down_leg = new RectangleSprite(15,80,false,true,true);

        Sprite left_down_foot = new RectangleSprite(30,15,false,true,true);
        Sprite right_down_foot = new RectangleSprite(30,15,false,true,true);


        // build scene graph aka tree from them
        torso.addChild(head);
        torso.addChild(left_shoudler);
        torso.addChild(right_shoudler);
        torso.addChild(left_leg);
        torso.addChild(right_leg);

        left_shoudler.addChild(left_arm);
        left_arm.addChild(left_down_arm);
        left_down_arm.addChild(left_hand);

        right_shoudler.addChild(right_arm);
        right_arm.addChild(right_down_arm);
        right_down_arm.addChild(right_hand);

        left_leg.addChild(left_down_leg);
        left_down_leg.addChild(left_down_foot);

        right_leg.addChild(right_down_leg);
        right_down_leg.addChild(right_down_foot);


        // translate them to a starting position
        torso.startingPosition(screen_width/2.0, screen_height/2.0);
        head.startingPosition(0, -(torso.height/2.0 + head.height/2.0));

        left_shoudler.startingPosition(torso.startingX+1,torso.startingY+10);
        right_shoudler.startingPosition(torso.startingX+78, torso.startingY+10);

        left_arm.startingPosition(left_shoudler.startingX-3, left_shoudler.startingY+50);
        right_arm.startingPosition(right_shoudler.startingX+32, right_shoudler.startingY+50);

        left_down_arm.startingPosition(left_arm.startingX+7, left_arm.startingY+120);
        left_hand.startingPosition(left_down_arm.x+7, left_down_arm.startingY+87);

        right_down_arm.startingPosition(right_arm.startingX+10, right_arm.startingY+120);
        right_hand.startingPosition(right_down_arm.startingX+10, right_down_arm.startingY+87);

        left_leg.startingPosition(torso.startingX+20, torso.startingY+180);
        right_leg.startingPosition(torso.startingX+60, torso.startingY+180);

        left_down_leg.startingPosition(-1.5, left_leg.startingY+120);
        left_down_foot.startingPosition(-2.5, left_down_leg.startingY+87);

        right_down_leg.startingPosition(right_leg.startingX+8, right_leg.startingY+120);
        right_down_foot.startingPosition(right_down_leg.startingX+10, right_down_leg.startingY+87);

        // return root
        root = torso;
        return torso;
    }
    public MenuBar get_menu(){
        MenuBar menubar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem reset = new MenuItem("Reset (Ctrl+R)");
        MenuItem di = new MenuItem("---- (divider)");
        MenuItem quit =  new MenuItem("Quit (Ctrl+Q)");
        fileMenu.getItems().addAll(reset, di, quit);

        Menu ragdolls = new Menu("Other Ragdolls");
        MenuItem doll = new MenuItem("Doll");
        MenuItem cat = new MenuItem("Cat");
        MenuItem tree = new MenuItem("Treeman");
        ragdolls.getItems().addAll(doll, cat, tree);
        menubar.getMenus().addAll(fileMenu,ragdolls);

        reset.setOnAction(action-> {
            reset();
        });
        quit.setOnAction(action-> {
            System.exit(0);
        });
        return menubar;
    }
    public void where(){
        if (selectedSprite.localID == "2"){
            head_flag = true;
            arm_flag = false;
            hand_flag =false;
            leg_flag = false;
            feet_flag = false;
        } else if (selectedSprite.localID == "5" || selectedSprite.localID == "6"){
            head_flag = false;
            arm_flag = true;
            hand_flag =false;
            leg_flag = false;
            feet_flag = false;
        } else if (selectedSprite.localID == "7" || selectedSprite.localID == "8"){
            head_flag = false;
            arm_flag = false;
            hand_flag = true;
            leg_flag = false;
            feet_flag = false;
        } else if (selectedSprite.localID == "9" ||selectedSprite.localID == "10"
                ||selectedSprite.localID == "11" || selectedSprite.localID == "12" ){
            head_flag = false;
            arm_flag = false;
            hand_flag =false;
            leg_flag = true;
            feet_flag = false;
        } else if (selectedSprite.localID == "13" ||selectedSprite.localID == "14"){
            head_flag = false;
            arm_flag = false;
            hand_flag =false;
            leg_flag = false;
            feet_flag = true;
        } else {

        }
    }
    public void restriction(){
        if (head_flag == true){
            restrictions = head_restrictions;
        }else if (arm_flag == true){
            restrictions = arm_restrictions;
        }else if (hand_flag == true){
            restrictions = hand_restrictions;
        }else if (leg_flag == true){
            restrictions = leg_restrictions;
        } else if (feet_flag == true){
            restrictions = feet_restrictions;
        } else {
            restrictions = 6.28318531;
        }
    }


}
