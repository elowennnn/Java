import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.image.Image;
import java.util.Vector;

/**
 * A building block for creating your own shapes
 * These explicitly support parent-child relationships between nodes
 */

public abstract class Sprite {
    static int spriteID = 0;
    final String localID;

    Image image;

    public double x, y, startingX, startingY, width, height;
    public boolean canRotate = true;
    public boolean canTranslate = true;
    public boolean canScale = true;

    protected Sprite parent = null;
    public Affine matrix = new Affine();
    public Affine startingMatrix = new Affine();
    protected Vector<Sprite> children = new Vector<Sprite>();

    public Sprite(double width, double height) {
        localID = String.valueOf(++spriteID);
        this.image = new Image("head.png", 50,50,true,true);
        this.startingX = -width/2.0;
        this.x = startingX;
        this.startingY = -height/2.0;
        this.y = startingY;
        this.width = width;
        this.height = height;
    }

    public Sprite(Sprite parent, double width, double height) {
        this(width, height);
        if (parent != null) {
            parent.addChild(this);
        }
    }

    // reset to starting values
    void reset() {
        for (Sprite child : children) {
            child.reset();
        }
        matrix = startingMatrix.clone();
        x = startingX;
        y = startingY;
    }

    // maintain hierarchy
    public void addChild(Sprite s) {
        children.add(s);
        s.setParent(this);
    }

    public Sprite getParent() {
        return parent;
    }

    private void setParent(Sprite s) {
        this.parent = s;
    }

    // matrix accessors
    Affine getLocalMatrix() { return matrix; }
    Affine getFullMatrix() {
        Affine fullMatrix = getLocalMatrix().clone();
        if (parent != null) {
            fullMatrix.prepend(parent.getFullMatrix());
        }
        return fullMatrix;
    }

    void startingPosition(double x, double y) {
        startingMatrix = new Affine();
        startingMatrix.appendTranslation(x, y);
        matrix = startingMatrix.clone();
    }

    // transformations
    void translate(double dx, double dy) {
        matrix.appendTranslation(dx, dy);
    }

    void rotate(double theta, double x, double y) {
        Affine fullMatrix = getFullMatrix();
        Point2D pivot = new Point2D(x,y);
        matrix.appendRotation(theta, pivot.getX(), pivot.getY());
    }

    void scale(double sx, double sy, double x, double y) throws NonInvertibleTransformException {
        Affine fullMatrix = getFullMatrix();
        Point2D pivot = new Point2D(x,y);
        matrix.appendScale(sx, sy, pivot.getX(), pivot.getY());
/*
        //Affine fullMatrix = getFullMatrix();
        Affine inverse = fullMatrix.createInverse();

        // move to the origin, rotate and move back
        matrix.append(fullMatrix);
        matrix.appendScale(sx, sy);
        matrix.append(inverse);

 */


    }

    // hit tests
    // these cannot be handled in the base class, since the actual hit tests are dependend on the type of shape
    protected abstract boolean contains(Point2D p);
    protected boolean contains(double x, double y) {
        return contains(new Point2D(x, y));
    }

    // we can walk the tree from the base class, since we rely on the specific sprites to check containment
    protected Sprite getSpriteHit(double x, double y) {
        // check me first...
        if (this.contains(x, y)) {
            return this;
        }

        // if no match above, recurse through children and return the first hit
        // assumes no overlapping shapes
        for (Sprite sprite : children) {
            Sprite hit = sprite.getSpriteHit(x, y);
            if (hit != null) return hit;
        }

        return null;
    }

    // drawing method
    protected abstract void draw(GraphicsContext gc);

    // debugging
    public String toString() { return "Sprite " + localID; }
}
