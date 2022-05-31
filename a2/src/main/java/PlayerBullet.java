import javafx.scene.image.Image;

public class PlayerBullet {
    Image pb;
    float x;
    float y;

    public PlayerBullet(float x, float y, Image i){
        this.pb = i;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getPb() {
        return pb;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
