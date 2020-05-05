package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loadContent(ActionEvent actionEvent) throws IOException {
        ContentController content = new ContentController();
        content.openContentWindow();
    }
}
