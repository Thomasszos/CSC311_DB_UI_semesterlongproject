package viewmodel;

import com.sun.jna.platform.win32.Netapi32Util;
import dao.DbConnectivityClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UserSession;

public class SignUpController {

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUsername;

    public void createNewAccount(ActionEvent actionEvent) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(username.equals("") || password.equals("")) {
            alert.setContentText("Username and Password are Required");
            alert.showAndWait();
            return;
        }

        DbConnectivityClass myDB = new DbConnectivityClass();

        if(myDB.createAccount(username, password)) {
            alert.setContentText("Account Created");
            alert.showAndWait();
            UserSession.getInstance(username, password, "USER");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username Taken");
            alert.showAndWait();
        }

        //myDB.createAccount(username, password);

    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
