package controller;

import controller.controllerFX.GUIStartWindow;
import controller.proxy.ClientServerProxy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.utils.CommonUtils;

import java.io.IOException;

public class Client2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Client2.class.getResource("/sample/StartWindow.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            primaryStage = primaryStage;
            primaryStage.setTitle("Login");
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            GUIStartWindow loginController = loader.getController();
            //ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:client-config.xml");
            ClientServerProxy proxy = CommonUtils.getFactory().getBean(ClientServerProxy.class);
            loginController.setProxy(proxy);
            loginController.setStage(primaryStage);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
//        try{
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Client2.class.getResource("/sample/StartWindow.fxml"));
//            AnchorPane rootLayout = loader.load();
//            rootLayout.getStylesheets().add(getClass().getResource("/sample/css/StartWindowStylesheet.css").toExternalForm());
//            primaryStage = primaryStage;
//            primaryStage.setTitle("Login Window");
//            Scene scene = new Scene(rootLayout);
//            primaryStage.setScene(scene);
//
//            GUIStartWindow loginController = loader.getController();
//            ClientServerProxy proxy = CommonUtils.getFactory().getBean(ClientServerProxy.class);
//            loginController.setProxy(proxy);
//            loginController.setStage(primaryStage);
//
//            primaryStage.show();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }

//        Parent root = FXMLLoader.load(getClass().getResource("/sample/StartWindow.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
