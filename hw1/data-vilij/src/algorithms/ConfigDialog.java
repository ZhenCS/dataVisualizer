package algorithms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import settings.AppPropertyTypes;
import vilij.propertymanager.PropertyManager;
import vilij.settings.PropertyTypes;

public class ConfigDialog extends Stage {

    public enum Option{
        YES,NO;
    }


    private static ConfigDialog dialog;
    private TextField maxIterationField;
    private TextField updateIntervalField;
    private TextField clusterNumberField;
    private CheckBox toContinueBox;
    private HBox clusterNumberHBox;
    private Option saveOption;

    private ConfigDialog() {
        maxIterationField = new TextField();
        updateIntervalField = new TextField();
        clusterNumberField = new TextField();
        clusterNumberHBox = new HBox();
        toContinueBox = new CheckBox("Continuous Run?");
    }

    public static ConfigDialog getDialog() {
        if (dialog == null)
            dialog = new ConfigDialog();
        return dialog;
    }

    public void init(Stage owner) {
        initModality(Modality.WINDOW_MODAL); // modal => messages are blocked from reaching other windows
        initOwner(owner);
        setWidth(400);

        PropertyManager manager     = PropertyManager.getManager();
        Button closeButton = new Button(manager.getPropertyValue(AppPropertyTypes.CONFIG_LABEL.name()));
        VBox messagePane = new VBox();

        closeButton.setOnAction(e -> {
            saveOption = Option.YES;
            this.close();
        });
        messagePane.setAlignment(Pos.CENTER);

        HBox hbox1 = new HBox();
        Text text1 = new Text(manager.getPropertyValue(AppPropertyTypes.MAX_ITERATIONS_LABEL.name()));
        StackPane space1 = new StackPane();
        space1.setMinWidth(10);
        hbox1.setHgrow(space1, Priority.ALWAYS);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.getChildren().addAll(text1, space1 ,maxIterationField);

        HBox hbox2 = new HBox();
        Text text2 = new Text(manager.getPropertyValue(AppPropertyTypes.UPDATE_INTERVAL_LABEL.name()));
        StackPane space2 = new StackPane();
        space2.setMinWidth(10);
        hbox2.setHgrow(space2, Priority.ALWAYS);
        hbox2.setAlignment(Pos.CENTER_LEFT);
        hbox2.getChildren().addAll(text2, space2, updateIntervalField);


        Text text3 = new Text("Number of Clusters:");
        StackPane space3 = new StackPane();
        space3.setMinWidth(10);
        clusterNumberHBox.setHgrow(space3, Priority.ALWAYS);
        clusterNumberHBox.setAlignment(Pos.CENTER_LEFT);
        clusterNumberHBox.getChildren().addAll(text3, space3, clusterNumberField);


        messagePane.getChildren().addAll(hbox1, hbox2, clusterNumberHBox, toContinueBox);
        messagePane.getChildren().add(closeButton);
        messagePane.setPadding(new Insets(60, 40, 60, 40));
        messagePane.setSpacing(20);

        Scene messageScene = new Scene(messagePane);
        this.setScene(messageScene);
    }


    public void show(String configDialogTitle, Algorithm alg) {
        setTitle(configDialogTitle);
        saveOption = Option.NO;
        maxIterationField.setText("" + alg.getMaxIterations());
        updateIntervalField.setText("" + alg.getUpdateInterval());
        if(alg.tocontinue())
            toContinueBox.setSelected(true);
        else
            toContinueBox.setSelected(false);

        if(alg instanceof Cluster){
            clusterNumberHBox.setVisible(true);
            clusterNumberField.setText("" + ((Cluster) alg).getClusterNumber());
        }
        else{
            clusterNumberHBox.setVisible(false);
        }

        showAndWait();
    }

    public Option getSave() {return saveOption;}

    public int getMaxIterations() {

        try{
            return Integer.parseInt(maxIterationField.getText());
        }catch (NumberFormatException e){
            return 0;
        }


    }

    public int getUpdateInterval() {
        try{
            return Integer.parseInt(updateIntervalField.getText());
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public int getClusterNumber() {
        try{
            return Integer.parseInt(clusterNumberField.getText());
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public boolean getToContinue() {
        return toContinueBox.isSelected();
    }


}
