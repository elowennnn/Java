import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Improved_Enemy {
    Image s;
    EnemyBullet bullet;
    boolean dead;


    public Improved_Enemy(Image n, Image b, float x, float y){
        SetS(n);
        SetB(b, x, y);
        this.dead = false;
    }
    public boolean isDead() {
        return dead;
    }

    public void SetS(Image s) {
        this.s = s;
    }
    public void SetB(Image b, float x, float y){
        this.bullet = new EnemyBullet(b, x, y);
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public EnemyBullet getBullet() {
        return bullet;
    }

}

