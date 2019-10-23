package controller.controllerFX;

import controller.proxy.ClientServerProxy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIStartWindow {
    private ClientServerProxy proxy;
    private Stage stage;

    public Label errorLabel;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Button loginButton;

//    public void setService(LoginService loginService){
//        this.loginService = loginService;
//        System.out.println("setting login service " + loginService);
//    }

    public void setProxy(ClientServerProxy proxy){
        this.proxy = proxy;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void loginHandle(ActionEvent actionEvent) throws IOException {
        System.out.print("You clicket me!\n");
        System.out.println(usernameTextField.getText() + " " + passwordTextField.getText());


        if (this.proxy.login(usernameTextField.getText(), passwordTextField.getText()))
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Controller.class.getResource("/sample/sample.fxml"));
                AnchorPane root = loader.load();
                root.getStylesheets().add(getClass().getResource("/sample/css/MainWindowStylesheet.css").toExternalForm());

                Stage dialogStage = new Stage();
                dialogStage.setTitle("");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                Controller controller = loader.getController();
                proxy.setClient(controller);
                controller.setProxy(proxy);
                controller.setStage(dialogStage);
                controller.setUsername(usernameTextField.getText());
                stage.close();
                dialogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            displayWrongMessage();
            errorLabel.setVisible(true);
        }
    }


//    private boolean login() {
//        String username = usernameTextField.getText();
//        String password = passwordTextField.getText();
//
//        Service service = new Service(new DatabaseRepository(new ArrayList<>(), DatabaseConnection.getPropreties()), new DatabaseLoginRepository(new ArrayList<>(), DatabaseConnection.getPropreties()), new Validator());
//        return service.existUser(username, password);
//    }
//
    private void displayWrongMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(" ¯\\_(ツ)_/¯  Oooops. Authentification went wrong");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid username or password!");
        alert.showAndWait();
    }
//
//    @FXML
//    private void enterMainWindow(ActionEvent e) throws IOException {
//        System.out.print("You clicket me!\n");
//        Parent homePageParent;
//        if (login()) {
//            homePageParent = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
//            Scene homePageScene = new Scene(homePageParent);
//            Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//            appStage.hide();
//            appStage.setScene(homePageScene);
//            appStage.show();
//        } else {
//            displayWrongMessage();
//        }
//    }
}
