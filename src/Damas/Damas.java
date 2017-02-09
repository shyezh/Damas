package Damas;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Damas extends Application {

    public static final int TAMANY = 80;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tablero[][] Tabla = new Tablero[WIDTH][HEIGHT];

    private Group TableroGroup = new Group();
    private Group PiezaGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TAMANY, HEIGHT * TAMANY);
        root.getChildren().addAll(TableroGroup, PiezaGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tablero Tablero = new Tablero((x + y) % 2 == 0, x, y);
                Tabla[x][y] = Tablero;

                TableroGroup.getChildren().add(Tablero);

                Pieza Pieza = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    Pieza = makePieza(TiposPieza.NEGRO, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    Pieza = makePieza(TiposPieza.BLANCO, x, y);
                }

                if (Pieza != null) {
                    Tablero.setPieza(Pieza);
                    PiezaGroup.getChildren().add(Pieza);
                }
            }
        }

        return root;
    }

    private ResultadoMovimiento tryMove(Pieza Pieza, int newX, int newY) {
        if (Tabla[newX][newY].hasPieza() || (newX + newY) % 2 == 0) {
            return new ResultadoMovimiento(TiposMovimiento.NONE);
        }

        int x0 = toTabla(Pieza.getOldX());
        int y0 = toTabla(Pieza.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == Pieza.getType().moveDir) {
            return new ResultadoMovimiento(TiposMovimiento.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == Pieza.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (Tabla[x1][y1].hasPieza() && Tabla[x1][y1].getPieza().getType() != Pieza.getType()) {
                return new ResultadoMovimiento(TiposMovimiento.KILL, Tabla[x1][y1].getPieza());
            }
        }

        return new ResultadoMovimiento(TiposMovimiento.NONE);
    }

    private int toTabla(double pixel) {
        return (int)(pixel + TAMANY / 2) / TAMANY;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Damas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pieza makePieza(TiposPieza type, int x, int y) {
        Pieza Pieza = new Pieza(type, x, y);

        Pieza.setOnMouseReleased(e -> {
            int newX = toTabla(Pieza.getLayoutX());
            int newY = toTabla(Pieza.getLayoutY());

            ResultadoMovimiento result = tryMove(Pieza, newX, newY);

            int x0 = toTabla(Pieza.getOldX());
            int y0 = toTabla(Pieza.getOldY());

            switch (result.getType()) {
                case NONE:
                    Pieza.abortMove();
                    break;
                case NORMAL:
                    Pieza.move(newX, newY);
                    Tabla[x0][y0].setPieza(null);
                    Tabla[newX][newY].setPieza(Pieza);
                    break;
                case KILL:
                    Pieza.move(newX, newY);
                    Tabla[x0][y0].setPieza(null);
                    Tabla[newX][newY].setPieza(Pieza);

                    Pieza otherPieza = result.getPieza();
                    Tabla[toTabla(otherPieza.getOldX())][toTabla(otherPieza.getOldY())].setPieza(null);
                    PiezaGroup.getChildren().remove(otherPieza);
                    break;
            }
        });

        return Pieza;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
