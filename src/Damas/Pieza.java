package Damas;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static Damas.Damas.TAMANY;


public class Pieza extends StackPane {

    private TiposPieza type;

    private double RatonX, RatonY;
    private double oldX, oldY;

    public TiposPieza getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Pieza(TiposPieza type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(TAMANY * 0.3125, TAMANY * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TAMANY * 0.03);

        bg.setTranslateX((TAMANY - TAMANY * 0.3125 * 2) / 2);
        bg.setTranslateY((TAMANY - TAMANY * 0.26 * 2) / 2 + TAMANY * 0.07);

        Ellipse ellipse = new Ellipse(TAMANY * 0.3125, TAMANY * 0.26);
        ellipse.setFill(type == TiposPieza.NEGRO
                ? Color.valueOf("#000") : Color.valueOf("#e2e2e2"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TAMANY * 0.03);

        ellipse.setTranslateX((TAMANY - TAMANY * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TAMANY - TAMANY * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            RatonX = e.getSceneX();
            RatonY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - RatonX + oldX, e.getSceneY() - RatonY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * TAMANY;
        oldY = y * TAMANY;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
