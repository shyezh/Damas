package Damas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tablero extends Rectangle {

    private Pieza Pieza;

    public boolean hasPieza() {
        return Pieza != null;
    }

    public Pieza getPieza() {
        return Pieza;
    }

    public void setPieza(Pieza Pieza) {
        this.Pieza = Pieza;
    }

    public Tablero(boolean light, int x, int y) {
        setWidth(Damas.TAMANY);
        setHeight(Damas.TAMANY);

        relocate(x * Damas.TAMANY, y * Damas.TAMANY);

        setFill(light ? Color.valueOf("#ffffff") : Color.valueOf("#424242"));
    }
}
