package System;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Main extends Application {

    Stage window;
    Scene mainMenu;
    JButton closeApp = new JButton("Close App");

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;


        // Main Menu
        HBox mainMenuLayout = new HBox();
        Button cameraAppOption = new Button("Camera App");
        cameraAppOption.setOnAction(event -> cameraApp());
        mainMenuLayout.getChildren().addAll(cameraAppOption);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenu = new Scene(mainMenuLayout, 1280,720);

        //Start window
        window.setScene(mainMenu);
        window.setTitle("Alpha build 0.1");
        window.show();
        window.setMaximized(true);
    }

    public void goToMainMenu() {

    }

    public void cameraApp(){
        System.out.println("Camera app loaded.");
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.isMirrored();

        JFrame cameraWindow = new JFrame("Camera App");
        cameraWindow.add(panel);
        cameraWindow.setResizable(true);
        cameraWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cameraWindow.pack();
        cameraWindow.setLocationRelativeTo(null);
        cameraWindow.setVisible(true);
        cameraWindow.show();

        JFrame cameraOptions = new JFrame("Camera Tools");
        cameraOptions.add(closeApp);
        cameraOptions.setLocationRelativeTo(null);
        cameraOptions.pack();
        cameraOptions.setVisible(true);

        closeApp.addActionListener(e -> {
            cameraWindow.setVisible(false);
            cameraOptions.setVisible(false);
            webcam.close();
            goToMainMenu();
        });
    }
}
