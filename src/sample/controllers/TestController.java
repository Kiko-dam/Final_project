package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.ContentController;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestController  implements Initializable {

    public  List<String> test;
    public int unit;
    public int section;
    public Label lblAnswer1;
    public Label lblAnswer2;
    public Label lblAnswer3;
    public TextArea txtQuestion;
    public CheckBox c1;
    public CheckBox c2;
    public CheckBox c3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Obtengo la unidad actual y la seccion actual
        unit =   ContentController.getUnit();
        section = ContentController.getSection();
        test= new ArrayList<>();

        this.loadTest();
    }

    @FXML
    public void openTestWindow( String sectionName) throws IOException
    {
        //La nueva ventana tendra como nombre la unidad y la seccion de la que se está haciendo el test nuevo.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("-Test " + sectionName);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void loadTest()
    {
        //Este método se encarga de cargar las preguntas de la unidad / seccion seleccionada.
        String pathOfTitles = "src/texts/unit/"+unit+"/test/"+section+".txt";
            try {
                BufferedReader inputFile = new BufferedReader(
                        new FileReader(new File(pathOfTitles)));
                String line;
                do {
                    line = inputFile.readLine();
                    if (line != null)
                        test.add(line);
                }
                while (line != null);
                inputFile.close();
                startTest();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void startTest()
    {
        //Aqui tendremos que hacer el bloque que vaya sacando las preguntas de la lista con las preguntas cargadas anteriormente
        //Creo que sería interesante hacer otro método que fuese el encargado de comprobar si las respuestas son correctas

        txtQuestion.setText(test.get(0));
        lblAnswer1.setText(test.get(1));
        lblAnswer2.setText(test.get(2));
        lblAnswer3.setText(test.get(3));

    }

    public void checkQuestion(ActionEvent actionEvent)
    {
        //Creo que este podria ser un método por  ejemplo, deberiamos pasarle las respuestas y podemos avisar al usuario
        //A través de  ventanas si acierta o falla..
        Alert dialog;

        boolean success = false;

        if(c1.isSelected())
            success = true;
        if(c2.isSelected())
            success = true;
        if(c3.isSelected())
            success = true;

        if(!success)
        {
            dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setHeaderText("");
            dialog.setContentText("It was the option X");
            dialog.showAndWait();
        }
        else
        {

            dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("Good Job");
            dialog.setHeaderText("");
            dialog.setContentText("Perfect!");
            dialog.showAndWait();
        }

    }
}

