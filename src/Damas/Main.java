package Damas;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.animation.TranslateTransition;
import javafx.application.Application;

import javafx.event.ActionEvent;

import javafx.geometry.*;
import javafx.scene.*;

import javafx.scene.control.Label;

import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static Font font;
    private MenuBox menu;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1000, 650);

        try (InputStream is = Files.newInputStream(Paths.get("src\\damas\\damasimg.jpg"));)/*C:\\Users\\Marc\\Desktop\\Java FX\\menu\\
      */  {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1066);
            img.setFitHeight(650);

            root.getChildren().add(img);
            
        }
        catch (IOException e) {
            System.out.println("No carga imagen");
        }

        MenuItem itemQuit = new MenuItem("SALIR");
        itemQuit.setOnMouseClicked(event -> System.exit(0));

        MenuItem itemCredits = new MenuItem("CREDITS");
        itemCredits.setOnMouseClicked(event1 -> {
            Label secondLabel = new Label("Hecho por Marc llobera, \n Victor Marchante y \n Sheng Ye " );
            
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(secondLabel);
            
            Scene secondScene = new Scene(secondaryLayout, 210, 110);
            
            Stage secondStage = new Stage();
            secondStage.setTitle("Creditos");
            secondStage.setScene(secondScene);
            secondStage.setMaxWidth(210);        
            secondStage.setMaxHeight(110);
            secondStage.setMinWidth(210);        
            secondStage.setMinHeight(110);
            secondStage.show();
            
        });
        
        MenuItem itemOptions = new MenuItem("MULTIJUGADOR");
        itemOptions.setOnMouseClicked(event2 -> System.exit(0));
        
        MenuItem itemNewGame = new MenuItem("MODO CAMPAÑA");
        itemNewGame.setOnMouseClicked(event3 -> System.exit(0));
        
        menu = new MenuBox("LAS DAMAS",
                itemNewGame,
                itemOptions,
                itemCredits,
                new MenuItem(""),
                itemQuit);

        root.getChildren().add(menu);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (menu.isVisible()) {
                    menu.hide();
                }
                else {
                    menu.show();
                }
            }
        });
        primaryStage.setTitle("Damas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private static class MenuBox extends StackPane {
        public MenuBox(String title, MenuItem... items) {
            Rectangle bg = new Rectangle(300, 650);
            bg.setOpacity(0.2);

            DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
            shadow.setSpread(0.8);

            bg.setEffect(shadow);

            Text text = new Text(title + "   ");
            text.setFont(font);
            text.setFill(Color.WHITE);

            Line hSep = new Line();
            hSep.setEndX(250);
            hSep.setStroke(Color.DARKGREEN);
            hSep.setOpacity(0.4);

            Line vSep = new Line();
            vSep.setStartX(300);
            vSep.setEndX(300);
            vSep.setEndY(600);
            vSep.setStroke(Color.DARKGREEN);
            vSep.setOpacity(0.4);

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.TOP_RIGHT);
            vbox.setPadding(new Insets(60, 0, 0, 0));
            vbox.getChildren().addAll(text, hSep);
            vbox.getChildren().addAll(items);

            setAlignment(Pos.TOP_RIGHT);
            getChildren().addAll(bg, vSep, vbox);
        }

        public void show() {
            setVisible(true);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(0);
            tt.play();
        }

        public void hide() {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
            tt.setToX(-300);
            tt.setOnFinished(event -> setVisible(false));
            tt.play();
        }
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            Rectangle bg = new Rectangle(300, 24);

            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.BLACK),
                    new Stop(0.2, Color.DARKGREY)
            });

            bg.setFill(gradient);
            bg.setVisible(false);
            bg.setEffect(new DropShadow(5, 0, 5, Color.BLACK));

            Text text = new Text(name + "      ");
            text.setFill(Color.LIGHTGREY);
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER_RIGHT);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setVisible(true);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setVisible(false);
                text.setFill(Color.LIGHTGREY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
