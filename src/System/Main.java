package System;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    Stage window;
    Scene mainMenu;
    JButton closeApp = new JButton("Close App");
    Webcam webcam = Webcam.getDefault();

    public static void main(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.UNDECORATED);
        window = primaryStage;


        // Main Menu
        VBox mainMenuLayout = new VBox();
        Button cameraAppOption = new Button("Camera App");
        Button closeWindow = new Button("Close Window");
        cameraAppOption.setOnAction(event -> {
            try {
                cameraApp();
            } catch (IOException e) {
                System.out.print("error launching camera app"); //replace with alert box soon
            }
        });
        closeWindow.setOnAction(event -> closeProgram());
        mainMenuLayout.getChildren().addAll(cameraAppOption, closeWindow);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenu = new Scene(mainMenuLayout, 1280,720);

        //Start window
        window.setScene(mainMenu);
        window.setTitle("Alpha build 0.12");
        window.show();
        window.setMaximized(true);
    }


    //Temporary Close Button
    private void closeProgram(){
        Boolean answer = ConfirmationBox.display("Temporary Close","Do you want to exit?");
        if(answer == true){
            window.close();
        }

    }



    //Camera App
    public void cameraApp() throws IOException{

        //Turning on Camera
        System.out.println("Camera app loaded.");
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        //Camera Settings
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.isMirrored();

        // Window
        JFrame cameraWindow = new JFrame("Camera App");
        cameraWindow.add(panel);
        cameraWindow.setResizable(true);
        cameraWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cameraWindow.pack();
        cameraWindow.setLocationRelativeTo(null);
        cameraWindow.setVisible(true);
        cameraWindow.show();

        JButton cameraPicture = new JButton("Take photo");
        cameraPicture.addActionListener(e -> {
            try {
                takePhoto();
            } catch (IOException e1) {
                System.out.print("error taking photo"); //add alert box here soon
            }
        });

        JFrame cameraOptions = new JFrame("Camera Tools");
        cameraOptions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cameraOptions.setBounds(0,0,200,200);
        JPanel cameraButtonLayout = new JPanel();
        cameraButtonLayout.add(cameraPicture);
        cameraButtonLayout.add(closeApp);
        cameraOptions.add(cameraButtonLayout);
        cameraOptions.setLocationRelativeTo(null);
        cameraOptions.pack();
        cameraOptions.setVisible(true);


        // Return to main menu
        closeApp.addActionListener(e -> {
            cameraWindow.setVisible(false);
            cameraOptions.setVisible(false);
            webcam.close();
        });
    }

    // Take photo
    private void takePhoto() throws IOException{
        ImageIO.write(webcam.getImage(), "JPG", new File("test.jpg"));
    }

}
