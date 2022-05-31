Yixue Zhang

20606015 Y998ZHAN

openjdk version "16.0.1" 2021-04-20

macOS 10.15.7 (MacBook Pro 2015)

How to run it: Gradle Build and Gradle Run

Instructions to play:
Press 1, 2, or 3 to enter Level1, Level2, Level3
Press Enter to enter Level1
Press Q on main page to quit the game
Press Q on any Levels will go back to main page
Press A or <-, D or -> to move your ship
Press SPACE to fire



Reference:

1. Sound and Images: cs349 sound and image files provided in A2 description

2. Use sample code from cs349-public repository:

02.JavaFX -> 03.switch_scenes
03.Graphics -> 01.canvas, 07a.simple_animation, 07c.transition_animation
04.Events -> 02.key_events

3. Imported java classes:
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