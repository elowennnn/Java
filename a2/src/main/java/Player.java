
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Player {
    ArrayList ships;
    int number_of_ships;
    PlayerBullet pb;
    String instructions;
    int score;
    int lives;
    float x;
    float y;
    //ship
    public class ship{
        Image i_ship;
        boolean dead;

        public boolean isDead() {
            return dead;
        }
        public ship(Image s){
            this.i_ship = s;
            this.dead = false;
        }

        public void setDead(boolean dead) {
            this.dead = dead;
        }
    }


    public Player(Image i, Image b, float x, float y){
        this.ships = new ArrayList();
        ship s1 = new ship(i);
        ship s2 = new ship(i);
        ship s3 = new ship(i);
        this.ships.add(s1);
        this.ships.add(s2);
        this.ships.add(s3);
        this.pb = new PlayerBullet(x, y, b);
        this.x = this.pb.getX();
        this.y = this.pb.getY();
        this.number_of_ships = 3;
        this.instructions = "";
        this.score = 0;
        this.lives = 3;
    }
    public void minus_lives() {
        this.lives -= 1;
    }

    public void minus_ships(int n) {
        this.number_of_ships -= n;
    }

    public void add_Score(int s) {
        this.score += s;
    }

    public int getLives() {
        return lives;
    }

    public int getNumber_of_ships() {
        return number_of_ships;
    }

    public int getScore() {
        return score;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public PlayerBullet getPb() {
        return pb;
    }
}
