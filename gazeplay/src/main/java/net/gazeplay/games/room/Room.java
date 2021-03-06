package net.gazeplay.games.room;

import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.GameContext;
import net.gazeplay.GameLifeCycle;
import net.gazeplay.commons.gaze.devicemanager.GazeEvent;
import net.gazeplay.commons.utils.stats.Stats;

@Slf4j
public class Room implements GameLifeCycle {
    private final GameContext gameContext;
    private final Stats stats;

    private PerspectiveCamera camera;

    private final javafx.geometry.Dimension2D dimension2D;
    private final Rotate rotateX;
    private final Rotate rotateY;

    private static double xLength;
    private static double yLength;

    private final double positionCamera;

    Image arrowImNorth;
    Rectangle rectangleArrowNorth;

    Image arrowImWest;
    Rectangle rectangleArrowWest;

    Image arrowImEast;
    Rectangle rectangleArrowEast;

    Image arrowImSouth;
    Rectangle rectangleArrowSouth;

    double imageWidth;
    double imageHeight;

    public Room(GameContext gameContext, Stats stats) {
        super();
        this.gameContext = gameContext;
        this.stats = stats;
        dimension2D = gameContext.getGamePanelDimensionProvider().getDimension2D();
        imageWidth = dimension2D.getWidth() / 12;
        imageHeight = dimension2D.getHeight() / 12;

        arrowImNorth = new Image("data/room/arrowNorth.png", imageWidth, imageHeight, true, true);
        rectangleArrowNorth = new Rectangle(arrowImNorth.getWidth(), arrowImNorth.getHeight());
        rectangleArrowNorth.setFill(new ImagePattern(arrowImNorth));

        arrowImWest = new Image("data/room/arrowWest.png", imageWidth, imageHeight, true, true);
        rectangleArrowWest = new Rectangle(arrowImWest.getWidth(), arrowImWest.getHeight());
        rectangleArrowWest.setFill(new ImagePattern(arrowImWest));

        arrowImEast = new Image("data/room/arrowEast.png", imageWidth, imageHeight, true, true);
        rectangleArrowEast = new Rectangle(arrowImEast.getWidth(), arrowImEast.getHeight());
        rectangleArrowEast.setFill(new ImagePattern(arrowImEast));

        arrowImSouth = new Image("data/room/arrowSouth.png", imageWidth, imageHeight, true, true);
        rectangleArrowSouth = new Rectangle(arrowImSouth.getWidth(), arrowImSouth.getHeight());
        rectangleArrowSouth.setFill(new ImagePattern(arrowImSouth));

        rotateX = new Rotate();
        rotateX.setAxis(Rotate.X_AXIS);
        rotateY = new Rotate();
        rotateY.setAxis(Rotate.Y_AXIS);
        xLength = 1600;// dimension2D.getWidth();
        yLength = 500;// dimension2D.getHeight();
        positionCamera = -xLength / 2;
    }

    @Override
    public void launch() {
        Group objects = new Group();

        Wall top = new Wall("Y", -1, "top", xLength, yLength);
        objects.getChildren().add(top.getItem());

        Wall bottom = new Wall("Y", 1, "bottom", xLength, yLength);
        objects.getChildren().add(bottom.getItem());

        Wall back = new Wall("Z", -1, "back", xLength, yLength);
        objects.getChildren().add(back.getItem());

        Wall front = new Wall("Z", 1, "front", xLength, yLength);
        objects.getChildren().add(front.getItem());

        Wall left = new Wall("X", -1, "left", xLength, yLength);
        objects.getChildren().add(left.getItem());

        Wall right = new Wall("X", 1, "right", xLength, yLength);
        objects.getChildren().add(right.getItem());

        SubScene subScene = new SubScene(objects,
                dimension2D.getWidth() - arrowImWest.getWidth() - arrowImEast.getWidth(),
                dimension2D.getHeight() - arrowImNorth.getHeight() - arrowImSouth.getHeight());

        camera = new PerspectiveCamera(true);
        camera.setVerticalFieldOfView(false);

        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.getTransforms().addAll(rotateY, rotateX, new Translate(0, 0, positionCamera));

        PointLight light = new PointLight(Color.GAINSBORO);
        objects.getChildren().add(light);
        objects.getChildren().add(new AmbientLight(Color.WHITE));
        subScene.setCamera(camera);

        BorderPane root = new BorderPane(subScene);

        HBox topB = new HBox();
        topB.getChildren().add(rectangleArrowNorth);
        topB.setAlignment(Pos.CENTER);
        root.setTop(topB);

        HBox bottomB = new HBox();
        bottomB.getChildren().add(rectangleArrowSouth);
        bottomB.setAlignment(Pos.CENTER);
        root.setBottom(bottomB);

        root.setLeft(rectangleArrowWest);
        root.setRight(rectangleArrowEast);
        BorderPane.setAlignment(rectangleArrowWest, Pos.CENTER);
        BorderPane.setAlignment(rectangleArrowEast, Pos.CENTER);

        gameContext.getChildren().add(root);

        gameContext.getGazeDeviceManager().addEventFilter(rectangleArrowNorth);
        gameContext.getGazeDeviceManager().addEventFilter(rectangleArrowWest);
        gameContext.getGazeDeviceManager().addEventFilter(rectangleArrowEast);
        gameContext.getGazeDeviceManager().addEventFilter(rectangleArrowSouth);

        rectangleArrowNorth.setOnMouseMoved((event) -> {
            if (rotateX.getAngle() < 15) {
                rotateX.setAngle(rotateX.getAngle() + 0.25);
            }
        });
        rectangleArrowNorth.addEventHandler(GazeEvent.GAZE_MOVED, (GazeEvent ge) -> {
            if (rotateX.getAngle() < 15) {
                rotateX.setAngle(rotateX.getAngle() + 0.1);
            }
        });

        rectangleArrowSouth.setOnMouseMoved((event) -> {
            if (rotateX.getAngle() > -15) {
                rotateX.setAngle(rotateX.getAngle() - 0.25);
            }
        });
        rectangleArrowSouth.addEventHandler(GazeEvent.GAZE_MOVED, (GazeEvent ge) -> {
            if (rotateX.getAngle() > -15) {
                rotateX.setAngle(rotateX.getAngle() - 0.1);
            }
        });

        rectangleArrowWest.setOnMouseMoved((event) -> {
            rotateY.setAngle(rotateY.getAngle() % 360 - 0.25);
        });
        rectangleArrowWest.addEventHandler(GazeEvent.GAZE_MOVED, (GazeEvent ge) -> {
            rotateY.setAngle(rotateY.getAngle() % 360 - 0.1);
        });

        rectangleArrowEast.setOnMouseMoved((event) -> {
            rotateY.setAngle(rotateY.getAngle() % 360 + 0.25);
        });
        rectangleArrowEast.addEventHandler(GazeEvent.GAZE_MOVED, (GazeEvent ge) -> {
            rotateY.setAngle(rotateY.getAngle() % 360 + 0.1);
        });
    }

    @Override
    public void dispose() {

    }
}