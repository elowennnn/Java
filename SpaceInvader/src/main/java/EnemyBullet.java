import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyBullet {
    Image eb;
    float x;
    float y;

    public EnemyBullet(Image i, float x, float y){
        this.eb = i;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getEb() {
        return eb;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
}
