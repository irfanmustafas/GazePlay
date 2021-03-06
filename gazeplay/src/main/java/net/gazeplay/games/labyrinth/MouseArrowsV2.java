package net.gazeplay.games.labyrinth;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import net.gazeplay.GameContext;
import net.gazeplay.commons.gaze.devicemanager.GazeEvent;
import net.gazeplay.commons.utils.stats.Stats;

public class MouseArrowsV2 extends MouseArrows {

    public MouseArrowsV2(double positionX, double positionY, double width, double height, GameContext gameContext,
            Stats stats, Labyrinth gameInstance) {
        super(positionX, positionY, width, height, gameContext, stats, gameInstance);

    }

    @Override
    protected void placementFleche() {

        buttonDimHeight = buttonDimHeight / 1.5;
        buttonDimWidth = buttonDimWidth / 2.5;

        double x = gameInstance.positionX(indiceX) + gameInstance.adjustmentCaseWidth;
        double y = gameInstance.positionY(indiceY) + gameInstance.adjustmentCaseHeight;

        double dx = gameInstance.caseWidth;
        double dy = gameInstance.caseHeight;
        double dx2 = dx * 0.8;
        double dy2 = dy * 0.8;

        this.buttonUp = new Rectangle(x, y - dy, buttonDimWidth, buttonDimHeight);
        this.buttonUp.setFill(new ImagePattern(new Image("data/labyrinth/images/upArrow.png"), 5, 5, 1, 1, true));
        this.indicatorUp = createProgressIndicator(x, y - dy, buttonDimWidth, buttonDimHeight);
        this.buttonUp.addEventHandler(MouseEvent.ANY, buttonUpEvent);
        this.buttonUp.addEventHandler(GazeEvent.ANY, buttonUpEvent);

        this.buttonDown = new Rectangle(x, y + dy2, buttonDimWidth, buttonDimHeight);
        this.buttonDown.setFill(new ImagePattern(new Image("data/labyrinth/images/downArrow.png"), 5, 5, 1, 1, true));
        this.indicatorDown = createProgressIndicator(x, y + dy2, buttonDimWidth, buttonDimHeight);
        this.buttonDown.addEventHandler(MouseEvent.ANY, buttonDownEvent);
        this.buttonDown.addEventHandler(GazeEvent.ANY, buttonDownEvent);

        this.buttonLeft = new Rectangle(x - dx, y, buttonDimHeight, buttonDimWidth);
        this.buttonLeft.setFill(new ImagePattern(new Image("data/labyrinth/images/leftArrow.png"), 5, 5, 1, 1, true));
        this.indicatorLeft = createProgressIndicator(x - dx, y, buttonDimHeight, buttonDimWidth);
        this.buttonLeft.addEventHandler(MouseEvent.ANY, buttonLeftEvent);
        this.buttonLeft.addEventHandler(GazeEvent.ANY, buttonLeftEvent);

        this.buttonRight = new Rectangle(x + dx2, y, buttonDimHeight, buttonDimWidth);
        this.buttonRight.setFill(new ImagePattern(new Image("data/labyrinth/images/rightArrow.png"), 5, 5, 1, 1, true));
        this.indicatorRight = createProgressIndicator(x + dx2, y, buttonDimHeight, buttonDimWidth);
        this.buttonRight.addEventHandler(MouseEvent.ANY, buttonRightEvent);
        this.buttonRight.addEventHandler(GazeEvent.ANY, buttonRightEvent);

        this.getChildren().addAll(buttonUp, buttonDown, buttonLeft, buttonRight);
        this.getChildren().addAll(indicatorUp, indicatorDown, indicatorLeft, indicatorRight);
    }

    @Override
    protected void recomputeArrowsPositions() {

        double x = gameInstance.positionX(indiceX) + gameInstance.adjustmentCaseWidth;
        double y = gameInstance.positionY(indiceY) + gameInstance.adjustmentCaseHeight;
        double dx = gameInstance.caseWidth;
        double dy = gameInstance.caseHeight;
        double dx2 = dx * 0.8;
        double dy2 = dy * 0.8;

        this.buttonUp.setX(x);
        this.buttonUp.setY(y - dy);
        this.indicatorUp.setTranslateX(x);
        this.indicatorUp.setTranslateY(y - dy);

        this.buttonDown.setX(x);
        this.buttonDown.setY(y + dy2);
        this.indicatorDown.setTranslateX(x);
        this.indicatorDown.setTranslateY(y + dy2);

        this.buttonLeft.setX(x - dx);
        this.buttonLeft.setY(y);
        this.indicatorLeft.setTranslateX(x - dx);
        this.indicatorLeft.setTranslateY(y);

        this.buttonRight.setX(x + dx2);
        this.buttonRight.setY(y);
        this.indicatorRight.setTranslateX(x + dx2);
        this.indicatorRight.setTranslateY(y);

    }

}
