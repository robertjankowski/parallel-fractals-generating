package pl.edmi.wdprir.ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ToggleParallelSwitch extends HBox {

    private final Label label = new Label();
    private final Button button = new Button();

    private final String textStyle = "-fx-text-fill:black; -fx-background-radius: 4; -fx-font-weight: bold;";

    private final SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);

    public SimpleBooleanProperty switchOnProperty() {
        return switchedOn;
    }

    private void init() {
        label.setText("SEQUENTIAL");
        getChildren().addAll(label, button);
        button.setOnAction(e -> switchedOn.set(!switchedOn.get()));
        label.setOnMouseClicked(e -> switchedOn.set(!switchedOn.get()));
        setStyle();
        bindProperties();
    }

    private void setStyle() {
        setWidth(80);
        label.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: grey;" + textStyle);
        setAlignment(Pos.CENTER_LEFT);
    }

    private void bindProperties() {
        label.prefWidthProperty().bind(widthProperty().divide(2));
        label.prefHeightProperty().bind(heightProperty());
        button.prefWidthProperty().bind(widthProperty().divide(2));
        button.prefHeightProperty().bind(heightProperty());
    }

    public ToggleParallelSwitch() {
        init();
        switchedOn.addListener((a, b, c) -> {
            if (c) {
                label.setText("PARALLEL");
                setStyle("-fx-background-color: indianred;" + textStyle);
                label.toFront();
            } else {
                label.setText("SEQUENTIAL");
                setStyle("-fx-background-color: grey;" + textStyle);
                button.toFront();
            }
        });
    }
}
