package controller.controllerFX;

import com.google.gson.Gson;
import controller.proxy.ClientServerProxy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.participant.AuditionDTO;
import model.participant.Distance;
import model.participant.ParticipantDTO;
import model.participant.Types;
import model.server.Request;
import model.server.RequestType;
import repository.server.ServerConnection;

import javax.xml.bind.ValidationException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private class ObserverThread implements Runnable {
        Socket s;
        DataInputStream inputStream;
        Gson gson;

        public ObserverThread(Properties properties) {
            try {
                s = ServerConnection.getSocketConnection(properties);
                gson = new Gson();
                sendObserverRequest(new DataOutputStream(s.getOutputStream()));
                inputStream = new DataInputStream(s.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String input = inputStream.readUTF();
                    Request request = gson.fromJson(input, Request.class);
                    if (request.getRequestType() == RequestType.Update) {
                        update();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendObserverRequest(DataOutputStream outputStream) throws IOException {
            Request request = new Request(RequestType.ObserverRequest, "");
            outputStream.writeUTF(gson.toJson(request));
        }
    }


    private final static String connectionSQLiteURL = "jdbc:sqlite:E:\\CS UBB\\Semestrul 4\\Medii de proiectare si programare\\DB\\lab2DB.db";

    public TableView<ParticipantDTO> mainTableView;
    public TableColumn<ParticipantDTO, Integer> idParticipantColumn;
    public TableColumn<ParticipantDTO, String> nameColumn;
    public TableColumn<ParticipantDTO, Integer> ageColumn;
    //public TableColumn<ParticipantDTO, List<AuditionDTO>> auditionsColumn;

    public TableView<AuditionDTO> auditionTableView;
    public TableColumn<AuditionDTO, Integer> idAuditionColumn;
    public TableColumn<AuditionDTO, Types> typeColumn;
    public TableColumn<AuditionDTO, Distance> distanceColumn;


    public TextArea l50TextArea;
    public TextArea l200TextArea;
    public TextArea l800TextArea;
    public TextArea l2000TextArea;
    public TextArea s50TextArea;
    public TextArea s200TextArea;
    public TextArea s800TextArea;
    public TextArea s2000TextArea;
    public TextArea f50TextArea;
    public TextArea f200TextArea;
    public TextArea f800TextArea;
    public TextArea f2000TextArea;
    public TextArea m50TextArea;
    public TextArea m200TextArea;
    public TextArea m800TextArea;
    public TextArea m2000TextArea;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField ageTextField;

    public CheckBox l50CheckBox;
    public CheckBox l200CheckBox;
    public CheckBox l800CheckBox;
    public CheckBox l2000CheckBox;
    public CheckBox s50CheckBox;
    public CheckBox s200CheckBox;
    public CheckBox s800CheckBox;
    public CheckBox s2000CheckBox;
    public CheckBox f50CheckBox;
    public CheckBox f200CheckBox;
    public CheckBox f800CheckBox;
    public CheckBox f2000CheckBox;
    public CheckBox m50CheckBox;
    public CheckBox m200CheckBox;
    public CheckBox m800CheckBox;
    public CheckBox m2000CheckBox;

    public Button addButton;
    public Button removeButton;
    public Button updateButton;
    public Button refreshTableViewButton;
    public Button uncheckAllCheckboxButton;
    public Button checkAllCheckboxButton;
    public Button logoutButton;
    public Button showAllParticipantsButton;

    private ClientServerProxy proxy;
    private Stage stage;
    private String username;
    private ObservableList<ParticipantDTO> participantDTOObservableList;


    private AuditionDTO auditionDTO1 = new AuditionDTO(1, Types.LIBER, Distance.m50);
    private AuditionDTO auditionDTO2 = new AuditionDTO(2, Types.LIBER, Distance.m200);
    private AuditionDTO auditionDTO3 = new AuditionDTO(3, Types.LIBER, Distance.m800);
    private AuditionDTO auditionDTO4 = new AuditionDTO(4, Types.LIBER, Distance.m2000);
    private AuditionDTO auditionDTO5 = new AuditionDTO(5, Types.SPATE, Distance.m50);
    private AuditionDTO auditionDTO6 = new AuditionDTO(6, Types.SPATE, Distance.m200);
    private AuditionDTO auditionDTO7 = new AuditionDTO(7, Types.SPATE, Distance.m800);
    private AuditionDTO auditionDTO8 = new AuditionDTO(8, Types.SPATE, Distance.m2000);
    private AuditionDTO auditionDTO9 = new AuditionDTO(9, Types.FLUTURE, Distance.m50);
    private AuditionDTO auditionDTO10 = new AuditionDTO(10, Types.FLUTURE, Distance.m200);
    private AuditionDTO auditionDTO11 = new AuditionDTO(11, Types.FLUTURE, Distance.m800);
    private AuditionDTO auditionDTO12 = new AuditionDTO(12, Types.FLUTURE, Distance.m2000);
    private AuditionDTO auditionDTO13 = new AuditionDTO(13, Types.MIXT, Distance.m50);
    private AuditionDTO auditionDTO14 = new AuditionDTO(14, Types.MIXT, Distance.m200);
    private AuditionDTO auditionDTO15 = new AuditionDTO(15, Types.MIXT, Distance.m800);
    private AuditionDTO auditionDTO16 = new AuditionDTO(16, Types.MIXT, Distance.m2000);

//    public void setService(ClientService service) throws IOException {
////        this.service = service;
////        showParticipantObservableList(service.findAllParticipants());
////        showNumberOfParticipantsForOneAudition();
////        ClassLoader loader = Thread.currentThread().getContextClassLoader();
////        Properties properties = new Properties();
////        properties.load(loader.getResourceAsStream("server.properties"));
////        ObserverThread observarThread = new ObserverThread(properties);
////        Thread tw = new Thread(observarThread);
////        tw.start();
////    }

    public void setProxy(ClientServerProxy proxy) throws IOException{
        this.proxy = proxy;
        showParticipantObservableList(proxy.getAllParticipants());
        showNumberOfParticipantsForOneAudition();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        props.load(loader.getResourceAsStream("server.properties"));

        ObserverThread observerThread = new ObserverThread(props);
        Thread tw = new Thread(observerThread);
        tw.start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private List<AuditionDTO> returnSelectedAuditionForParticipant() {
        List<AuditionDTO> auditionDTOList = new ArrayList<>();
        if (l50CheckBox.isSelected()) auditionDTOList.add(auditionDTO1);
        if (l200CheckBox.isSelected()) auditionDTOList.add(auditionDTO2);
        if (l800CheckBox.isSelected()) auditionDTOList.add(auditionDTO3);
        if (l2000CheckBox.isSelected()) auditionDTOList.add(auditionDTO4);

        if (s50CheckBox.isSelected()) auditionDTOList.add(auditionDTO5);
        if (s200CheckBox.isSelected()) auditionDTOList.add(auditionDTO6);
        if (s800CheckBox.isSelected()) auditionDTOList.add(auditionDTO7);
        if (s2000CheckBox.isSelected()) auditionDTOList.add(auditionDTO8);

        if (f50CheckBox.isSelected()) auditionDTOList.add(auditionDTO9);
        if (f200CheckBox.isSelected()) auditionDTOList.add(auditionDTO10);
        if (f800CheckBox.isSelected()) auditionDTOList.add(auditionDTO11);
        if (f2000CheckBox.isSelected()) auditionDTOList.add(auditionDTO12);

        if (m50CheckBox.isSelected()) auditionDTOList.add(auditionDTO13);
        if (m200CheckBox.isSelected()) auditionDTOList.add(auditionDTO14);
        if (m800CheckBox.isSelected()) auditionDTOList.add(auditionDTO15);
        if (m2000CheckBox.isSelected()) auditionDTOList.add(auditionDTO16);

        return auditionDTOList;
    }

    public void uncheckAllCheckbox(ActionEvent actionEvent) {
        l50CheckBox.setSelected(false);
        l200CheckBox.setSelected(false);
        l800CheckBox.setSelected(false);
        l2000CheckBox.setSelected(false);
        s50CheckBox.setSelected(false);
        s200CheckBox.setSelected(false);
        s800CheckBox.setSelected(false);
        s2000CheckBox.setSelected(false);
        l50CheckBox.setSelected(false);
        f50CheckBox.setSelected(false);
        f200CheckBox.setSelected(false);
        f800CheckBox.setSelected(false);
        f2000CheckBox.setSelected(false);
        m50CheckBox.setSelected(false);
        m200CheckBox.setSelected(false);
        m800CheckBox.setSelected(false);
        m2000CheckBox.setSelected(false);
    }

    public void checkAllCheckbox(ActionEvent actionEvent) {
        l50CheckBox.setSelected(true);
        l200CheckBox.setSelected(true);
        l800CheckBox.setSelected(true);
        l2000CheckBox.setSelected(true);
        s50CheckBox.setSelected(true);
        s200CheckBox.setSelected(true);
        s800CheckBox.setSelected(true);
        s2000CheckBox.setSelected(true);
        l50CheckBox.setSelected(true);
        f50CheckBox.setSelected(true);
        f200CheckBox.setSelected(true);
        f800CheckBox.setSelected(true);
        f2000CheckBox.setSelected(true);
        m50CheckBox.setSelected(true);
        m200CheckBox.setSelected(true);
        m800CheckBox.setSelected(true);
        m2000CheckBox.setSelected(true);
    }

    public void add(ActionEvent actionEvent) {
        if(validateFields() &&
                validate("[1-9][0-9]*", idTextField.getText(), "Please enter a valid id ( > 0 )!") &&
                validate("[1-9][0-9]*", ageTextField.getText(), "Please enter a valid age ( > 0 )!")){
            ParticipantDTO readedParticipantDTO = new ParticipantDTO(Integer.valueOf(idTextField.getText()), nameTextField.getText(), Integer.valueOf(ageTextField.getText()), returnSelectedAuditionForParticipant());
            try{
                proxy.save(readedParticipantDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("The participant was sucessfully added!");
                alert.show();
            } catch (Exception e){
                System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error 404");
                alert.show();
            }
            finally {
                mainTableView.refresh();
            }
        }
    }

    public void remove(ActionEvent actionEvent) {
        if(validate("[1-9][0-9]*", idTextField.getText(), "Please enter a valid id ( > 0 )!")) {
            int id = Integer.valueOf(idTextField.getText());
            try{
                proxy.delete(id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("The participant was sucessfully deleted!");
                alert.show();
            } catch (Exception e){
                System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error 404!");
                alert.show();
            }finally {
                mainTableView.refresh();
            }
        }

    }

    public void update(ActionEvent actionEvent) throws ValidationException {
        if(validateFields() &&
                validate("[1-9][0-9]*", idTextField.getText(), "Please enter a valid id ( > 0 )!") &&
                validate("[1-9][0-9]*", ageTextField.getText(), "Please enter a valid age ( > 0 )!")){
            ParticipantDTO readedParticipantDTO = new ParticipantDTO(Integer.valueOf(idTextField.getText()), nameTextField.getText(), Integer.valueOf(ageTextField.getText()), returnSelectedAuditionForParticipant());
            try{
                proxy.update(readedParticipantDTO);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("The participant was sucessfully updated!");
                alert.show();
            } catch (Exception e){
                System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Error 404!");
                alert.show();
            }
            finally {
                mainTableView.refresh();
            }
        }
    }

    public void showAuditionForThisParticipant(MouseEvent mouseEvent) {
        auditionTableView.refresh();
        ParticipantDTO participant = mainTableView.getSelectionModel().getSelectedItem();

        final ObservableList<AuditionDTO> auditionObservableList = FXCollections.observableArrayList(proxy.getAuditionListForOneParticipant(participant));
        idAuditionColumn.setCellValueFactory(new PropertyValueFactory<AuditionDTO, Integer>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<AuditionDTO, Types>("typ"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<AuditionDTO, Distance>("distance"));
        auditionTableView.setItems(auditionObservableList);

        idTextField.clear();
        nameTextField.clear();
        ageTextField.clear();
        idTextField.setText(participant.getId().toString());
        nameTextField.setText(participant.getName());
        ageTextField.setText(participant.getAge().toString());
    }

    public void showParticipantObservableList(List<ParticipantDTO> participantDTOList) {
        final ObservableList<ParticipantDTO> participantDTOObservableList = FXCollections.observableArrayList(participantDTOList);
        idParticipantColumn.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, Integer>("age"));
        //auditionsColumn.setCellValueFactory(new PropertyValueFactory<ParticipantDTO, List<AuditionDTO>>("auditionList"));
        mainTableView.setItems(participantDTOObservableList);
    }

    public void showNumberOfParticipantsForOneAudition() {
        l50TextArea.clear();
        l50TextArea.setDisable(true);
        l200TextArea.clear();
        l200TextArea.setDisable(true);
        l800TextArea.clear();
        l800TextArea.setDisable(true);
        l2000TextArea.clear();
        l2000TextArea.setDisable(true);

        s50TextArea.clear();
        s50TextArea.setDisable(true);
        s200TextArea.clear();
        s200TextArea.setDisable(true);
        s800TextArea.clear();
        s800TextArea.setDisable(true);
        s2000TextArea.clear();
        s2000TextArea.setDisable(true);

        f50TextArea.clear();
        f50TextArea.setDisable(true);
        f200TextArea.clear();
        f200TextArea.setDisable(true);
        f800TextArea.clear();
        f800TextArea.setDisable(true);
        f2000TextArea.clear();
        f2000TextArea.setDisable(true);

        m50TextArea.clear();
        m50TextArea.setDisable(true);
        m200TextArea.clear();
        m200TextArea.setDisable(true);
        m800TextArea.clear();
        m800TextArea.setDisable(true);
        m2000TextArea.clear();
        m2000TextArea.setDisable(true);


        l50TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO1)));
        l200TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO2)));
        l800TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO3)));
        l2000TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO4)));

        s50TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO5)));
        s200TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO6)));
        s800TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO7)));
        s2000TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO8)));

        f50TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO9)));
        f200TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO10)));
        f800TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO11)));
        f2000TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO12)));

        m50TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO13)));
        m200TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO14)));
        m800TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO15)));
        m2000TextArea.appendText(String.valueOf(proxy.countNumberOfParticipantForOneAudition(auditionDTO16)));
    }

    public void refreshTableView(ActionEvent actionEvent) {
        mainTableView.refresh();
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GUIStartWindow.class.getResource("/sample/StartWindow.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            GUIStartWindow loginController = loader.getController();

            loginController.setProxy(proxy);
            loginController.setStage(dialogStage);

            stage.close();
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllParticipants(ActionEvent actionEvent) {
        showParticipantObservableList(proxy.getAllParticipants());
    }

    public void update() {
        showParticipantObservableList(proxy.getAllParticipants());
        showNumberOfParticipantsForOneAudition();
        mainTableView.refresh();
    }

    private boolean validateFields() {
        if (idTextField.getText().isEmpty() || nameTextField.getText().isEmpty() ||
                ageTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" ¯\\_(ツ)_/¯  Oooops. Something went wrong");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Data Into The Fields");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validate(String regex, String matcher, String exitMessage) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(matcher);
        if (m.find() && m.group().equals(matcher)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" ¯\\_(ツ)_/¯  Oooops. Something went wrong");
            alert.setHeaderText(null);
            alert.setContentText(exitMessage);
            alert.showAndWait();
            return false;
        }
    }
}
