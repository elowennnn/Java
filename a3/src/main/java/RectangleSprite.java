import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class RectangleSprite extends Sprite {
    // Creates a rectangle based at the origin with the specified width and height
    public RectangleSprite(int width, int height) {
        super(width, height);
    }

    // Creates a rectangle based at the origin with the specified parent, width, and height
    public RectangleSprite(Sprite parent, int width, int height) {
        super(parent, width, height);
    }

    public RectangleSprite(int width, int height, boolean translate, boolean scale, boolean rotate) {
        super(width, height);

        canRotate = rotate;
        canScale = scale;
        canTranslate = translate;
    }

    // Draw on the supplied canvas
    protected void draw(GraphicsContext gc) {
        // save the current graphics context so that we can restore later
        Affine oldMatrix = gc.getTransform();

        // make sure we have the correct transformations for this shape
        gc.setTransform(getFullMatrix());
        gc.setStroke(Color.BLUE);
        gc.strokeOval(x, y, width, height);

        // draw children
        gc.setStroke(Color.BLUE);
        for (Sprite child : children) {
            child.draw(gc);
        }

        // set back to original value since we're done with this branch of the scene graph
        gc.setTransform(oldMatrix);
    }

    // Check if the point is contained by this shape
    // This cannot be abstract, since it relies on knowledge of the
    // specific type of shape for the hit test.
    @Override
    protected boolean contains(Point2D p) {
        try {
            // Use inverted matrix to move the mouse click so that it's
            // relative to the shape model at the origin.
            Point2D pointAtOrigin = getFullMatrix().createInverse().transform(p);

            // Perform the hit test relative to the shape model's
            // untranslated coordinates at the origin
            return new Rectangle(x, y, width, height).contains(pointAtOrigin);

        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }
        return false;
    }
}

