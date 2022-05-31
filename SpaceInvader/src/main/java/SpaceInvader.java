
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class SpaceInvader extends Application{
    Scene main_page, level1, level2, level3;
    StackPane root1, root2, root3;
    ArrayList<Improved_Enemy> list_Enemy;

    Player p_struct;
    Canvas p_global;

    float flag2 = 0;
    float flag1 = 0;
    float flag3 = 0;
    float end_flag = 0;
    float flag_bullet = 0;

    AnimationTimer timer;
    float enemy_grid_x, enemy_grid_y;
    float dx, dy;
    float d_player = 0;

    Duration dur, durb;
    TranslateTransition trans;
    @Override

    public void start(Stage stage) throws Exception {
        stage.setTitle("Space Invader");
        stage.setWidth(800);
        stage.setHeight(600);

        Image image = new Image("images/logo.png", 400, 180, true, true);
        ImageView iv = new ImageView(image);
        VBox logo = new VBox(iv);
        VBox.setMargin(logo, new Insets(50, 200, 50, 200));

        Text ins = new Text("Instructions\n");
        ins.setFont(Font.font("News", FontWeight.BOLD, 40));

        Text info = new Text("Yixue Zhang - 20606015");
        info.setFont(Font.font("Courier", FontWeight.LIGHT, 14));

        Text start_game = new Text("ENTER - Start Game");
        start_game.setFont(Font.font("Courier",FontWeight.LIGHT, 20));

        Text movement = new Text("A or <, D or > - Move ship left or right");
        movement.setFont(Font.font("Courier",FontWeight.LIGHT, 20));

        Text fire = new Text("SPACE - Fire!");
        fire.setFont(Font.font("Courier",FontWeight.LIGHT, 20));

        Text quit = new Text("Q - Quit Game");
        quit.setFont(Font.font("Courier",FontWeight.LIGHT, 20));

        Text special_level = new Text("1 or 2 or 3 - Start Game at a specific level\n\n\n\n\n");
        special_level.setFont(Font.font("Courier",FontWeight.LIGHT, 20));

        VBox combo = new VBox(logo, ins, start_game, movement, fire, quit, special_level, info);
        VBox.setMargin(combo, new Insets(200,500,200,200));
        combo.setAlignment(Pos.TOP_CENTER);


        main_page = new Scene(combo, 800,600);
        stage.setScene(main_page);
        stage.show();


        main_page.setOnKeyPressed(event ->{
            if(event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.ENTER){
                flag1 = 1;
                root1 = new StackPane();
                root1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));


                Canvas info_l = info_level("level: 1");
                Canvas enemy_g = Enemy_grid_canvas();
                p_global = player_canvas(0, 270);

                moving_enemy(1, 30, enemy_g, p_struct.getPb(), root1);

                root1.getChildren().addAll(info_l, enemy_g, p_global);

                level1 = new Scene(root1, 800, 600);
                stage.setScene(level1);
                timer.start();


                level1.setOnKeyPressed(e -> {
                    if(e.getCode() == KeyCode.Q){
                        timer.stop();
                        flag1 = 0;
                        d_player = 0;
                        end_flag = 0;
                        stage.setScene(main_page);

                    }
                    if(e.getCode() == KeyCode.A){
                        moving_player(KeyCode.A, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.LEFT){
                        moving_player(KeyCode.LEFT, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.D){
                        moving_player(KeyCode.D, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.RIGHT){
                        moving_player(KeyCode.RIGHT,2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.SPACE){
                        flag_bullet = 1;
                        e.consume();
                    }
                });

            }
            else if(event.getCode() == KeyCode.DIGIT2){
                flag2 = 1;
                root2 = new StackPane();
                root2.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

                Canvas info_l = info_level("level: 2");
                Canvas enemy_g = Enemy_grid_canvas();
                p_global = player_canvas(0, 270);

                moving_enemy(2, 20, enemy_g, p_struct.getPb(), root2);

                root2.getChildren().addAll(info_l, enemy_g, p_global);

                level2 = new Scene(root2, 800, 600);
                stage.setScene(level2);
                timer.start();

                level2.setOnKeyPressed(e -> {
                    if(e.getCode() == KeyCode.Q){
                        timer.stop();
                        flag2 = 0;
                        d_player = 0;
                        stage.setScene(main_page);
                        end_flag = 0;

                    }
                    if(e.getCode() == KeyCode.A){
                        moving_player(KeyCode.A,  2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.LEFT){
                        moving_player(KeyCode.LEFT, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.D){
                        moving_player(KeyCode.D, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.RIGHT){
                        moving_player(KeyCode.RIGHT, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.SPACE){
                        flag_bullet = 1;
                        e.consume();
                    }
                });
            }

            else if(event.getCode() == KeyCode.DIGIT3){
                flag3 = 1;
                root3 = new StackPane();
                root3.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

                Canvas info_l = info_level("level: 3");
                Canvas enemy_g = Enemy_grid_canvas();
                p_global = player_canvas(0, 270);
                moving_enemy(3, 20, enemy_g, p_struct.getPb(), root3);

                root3.getChildren().addAll(info_l, enemy_g,p_global);
                level3 = new Scene(root3, 800, 600);
                stage.setScene(level3);
                timer.start();

                level3.setOnKeyPressed(e -> {
                    if(e.getCode() == KeyCode.Q){
                        timer.stop();
                        flag3 = 0;
                        d_player = 0;
                        end_flag = 0;
                        stage.setScene(main_page);

                    }
                    if(e.getCode() == KeyCode.A){
                        moving_player(KeyCode.A,2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.LEFT){
                        moving_player(KeyCode.LEFT, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.D){
                        moving_player(KeyCode.D, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.RIGHT){
                        moving_player(KeyCode.RIGHT, 2);
                        e.consume();
                    }
                    if(e.getCode() == KeyCode.SPACE){
                        flag_bullet = 1;
                        e.consume();
                    }
                });
            }
            else if (event.getCode() == KeyCode.Q){
                Platform.exit();
            }
        });

        stage.show();

    }

    public Canvas info_level(String l){
        Canvas c = new Canvas(800,600);
        GraphicsContext gc = c.getGraphicsContext2D();

        gc.setLineWidth(2);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Courier", 20));
        gc.fillText("Score: 0", 20, 40);
        gc.fillText("Lives: 3", 550, 40);
        gc.fillText(l, 700,40);

        return c;
    }

    public Canvas Enemy_grid_canvas(){
        Canvas enemy_grid = new Canvas(490, 190);
        enemy_grid.setTranslateY(-150);


        GraphicsContext gce = enemy_grid.getGraphicsContext2D();
        list_Enemy = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 11; j++){
                if (i == 0){
                    Image image1 = new Image("images/enemy3.png", 40, 40, true, true);
                    Image image2 = new Image("images/bullet3.png", 20, 20, true, true);
                    float x = 175 + j * 45;
                    float y = 165;
                    Improved_Enemy e1 = new Improved_Enemy(image1, image2, x, y);
                    list_Enemy.add(e1);
                } else if (i == 1 || i == 2){
                    Image image2 = new Image("images/enemy2.png", 40, 40, true, true);
                    Image image21 = new Image("images/bullet2.png", 20, 20, true, true);
                    float x = 175 + j * 45;
                    float y = 165 + 40 * i;
                    Improved_Enemy e2 = new Improved_Enemy(image2, image21, x, y);
                    list_Enemy.add(e2);
                } else {
                    float x = 175 + j * 45;
                    float y = 165 + 40 * i;
                    Image image3 = new Image("images/enemy1.png", 40, 40, true, true);
                    Image image31 = new Image("images/bullet1.png", 20, 20, true, true);
                    Improved_Enemy e3 = new Improved_Enemy(image3, image31, x, y);
                    list_Enemy.add(e3);
                }
            }
        }
        Image image1 = new Image("images/enemy3.png", 40, 40, true, true);
        Image image2 = new Image("images/enemy2.png", 40, 40, true, true);
        Image image3 = new Image("images/enemy1.png", 40, 40, true, true);

        for (int i = 0; i <= 190; i += 40){
            for(int j = 0; j < 490; j += 45){
                if (i == 0) {
                    gce.drawImage (image1, j, i,40,30);
                } else if (i == 40 || i == 80){
                    gce.drawImage(image2, j, i, 40,30);
                } else {
                    gce.drawImage(image3, j, i, 40, 30);
                }
            }
        }

        return enemy_grid;
    }

    public void moving_enemy(float a, float b, Canvas enemy_grid, PlayerBullet pb, StackPane r ){
        if (flag_bullet == 0){
            enemy_grid_x = 0;
            enemy_grid_y = -150;
            dx = a;
            dy = b;
        }
        dur = Duration.millis(1);
        trans = new TranslateTransition(dur, enemy_grid);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (flag_bullet == 0){
                    handle_animation(); //grid
                } else if (flag_bullet == 1){

                    ImageView ip = new ImageView(pb.getPb());
                    float x = pb.getX();

                    ip.setTranslateX(x - 400);
                    ip.setTranslateY(245);

                    r.getChildren().add(ip);
                    double y = ip.getTranslateY();
                    ip.setTranslateY(y - 5);


                    flag_bullet = 0;
                }
                if (enemy_grid_y > 180){
                    Canvas msg = end_message();
                    end_flag = 1;
                    trans.stop();
                    timer.stop();
                    if (flag2 == 1){
                        root2.getChildren().add(msg);
                    }
                    if (flag1 == 1){
                        root1.getChildren().add(msg);
                    }
                    if (flag3 == 1){
                        root3.getChildren().add(msg);
                    }

                }
            }
        };

    }

    public Canvas player_canvas(float x, float y){
        Canvas p = new Canvas(50,40);
        p.setTranslateY(y);

        Image i_ship = new Image("images/player.png", 50, 40, true, true);
        Image i_bullet = new Image("images/player_bullet.png", 20,20,true,true);
        p_struct = new Player(i_ship, i_bullet,400 , 600);
        GraphicsContext gce = p.getGraphicsContext2D();

        gce.drawImage(i_ship,0,0, 50,40);

        return p;
    }

    public void moving_player(KeyCode x, int speed){
        if (d_player < -380) {
            if (x == KeyCode.A || x == KeyCode.LEFT){
                p_global.setTranslateX(d_player);
            }
            if (x == KeyCode.D || x == KeyCode.RIGHT){
                d_player += (10 * speed);
                p_global.setTranslateX(d_player);
            }

        } else if (d_player > 380){
            if (x == KeyCode.D || x == KeyCode.RIGHT){
                p_global.setTranslateX(d_player);
            }
            if (x == KeyCode.A || x == KeyCode.LEFT){
                d_player -= (10 * speed);
                p_global.setTranslateX(d_player);
            }
        }
        else {
            float old_x = p_struct.getPb().getX(); // left most x = 20, right most x = 760
            if (x == KeyCode.A || x == KeyCode.LEFT){
                d_player -= (10 * speed);
                p_struct.getPb().setX(old_x - 20);
                p_global.setTranslateX(d_player);
            }
            if (x == KeyCode.D || x == KeyCode.RIGHT){
                d_player += (10 * speed);
                p_struct.getPb().setX(old_x + 20);
                p_global.setTranslateX(d_player);
            }
        }
    }

    public Canvas end_message(){
        Canvas msg = new Canvas(600, 400);
        GraphicsContext gc = msg.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,600,400);

        gc.setLineWidth(2);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Courier", FontWeight.BOLD, 60));
        gc.fillText("You Lose!", 150, 120);

        gc.setLineWidth(2);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Courier", 30));
        gc.fillText("Press Q to main page", 150, 200);

        return msg;

    }


    void handle_animation(){
        trans.play();
        if(enemy_grid_x > 155 || enemy_grid_x < -155){
            enemy_grid_y += dy;
            trans.setToY(enemy_grid_y);
            dx *= -1;

            if (dx < 0){
                dx -= 0.5;

            } else {
                dx += 0.5;
            }
        }
        enemy_grid_x += dx;
        trans.setToX(enemy_grid_x);
    }
/*
    void player_fire(PlayerBullet pb, StackPane r){
        transb.play();
        ImageView ip = new ImageView(pb.getPb());
        float x = pb.getX();
        transb.setToX(400);
        transb.setToY(-10);

        //ip.setTranslateX(x - 400);
        //ip.setTranslateY(245); // downwards 270

        r.getChildren().add(ip);


        double y = ip.getTranslateY();
        //ip.setTranslateY(y - 5);

    }

 */

}
