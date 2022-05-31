
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Level {
    Canvas info;
    Canvas enemy;
    Canvas player;

    public Level(Canvas i, Canvas e, Canvas p){
        this.info = i;
        this.enemy = e;
        this.player = p;
    }

    public Scene level_to_scene(Level l){
        StackPane root1 = new StackPane();
        root1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        root1.getChildren().addAll(l.info, l.enemy, l.player);
        Scene lvl = new Scene(root1, 800, 600);

        return lvl;
    }

    public Canvas getEnemy() {
        return enemy;
    }

    public Canvas getInfo() {
        return info;
    }

    public Canvas getPlayer() {
        return player;
    }
    public Scene add_element(Canvas c, Level l){
        StackPane root1 = new StackPane();
        Canvas msg = new Canvas(490, 190);
        GraphicsContext gc = msg.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(0,0,490,190);
        msg.setTranslateY(-150);
        l.enemy = msg;
        root1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        System.out.println("testing3");
        root1.getChildren().addAll(l.info,l.enemy,l.player);
        System.out.println("testing4");

        Scene lvl = new Scene(root1, 800,600);

        return lvl;

    }
}
