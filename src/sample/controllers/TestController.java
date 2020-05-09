package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.ContentController;
import sample.data.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class TestController  implements Initializable {
    public List<String> lines;
    public List<Test> test;
    public int unit;
    public int section;
    public Label lblAnswer1;
    public Label lblAnswer2;
    public Label lblAnswer3;
    public TextArea txtQuestion;
    public RadioButton c1;
    public RadioButton c2;
    public RadioButton c3;
    public int actualQuestion;
    public int totalQuestions;
    public Label lblScore;
    public boolean alreadyAnswered;
    public  Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Obtengo la unidad actual y la seccion actual
        unit =   ContentController.getUnit();
        section = ContentController.getSection();
        lines= new ArrayList<>();
        test = new ArrayList<>();
        actualQuestion = 0;
        this.loadTest();

    }

    @FXML
    public void openTestWindow( String sectionName) throws IOException
    {
        //La nueva ventana tendra como nombre la unidad y la seccion de la que se está haciendo el test nuevo.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("-Test " + sectionName);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        stage.close();
    }

    public void cerrarVentana() {
        stage = (Stage) stage.getScene().getWindow();
        stage.close();
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
                        lines.add(line);
                }
                while (line != null);
                inputFile.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        startTest();
    }

    public void startTest()
    {
        //Aqui tendremos que hacer el bloque que vaya sacando las preguntas de la lista con las preguntas cargadas anteriormente
        int position = 1;
        Test t ;
        String question="";
        String answer1="";
        String answer2="";
        String answer3="";
        for(int i = 0; i < lines.size();i++)
        {
            int correctAnswer;
            switch (position)
            {
                case 1: question = lines.get(i);
                    System.out.println(lines.get(i));
                    position++;
                break;
                case 2: answer1 = lines.get(i);
                    position++;
                break;
                case 3: answer2 = lines.get(i);
                    position++;
                break;
                case 4: answer3 = lines.get(i);
                    position++;
                break;
                case 5:
                    correctAnswer = Integer.parseInt(lines.get(i));
                    position = 1;
                    t = new Test(question,answer1,answer2,answer3,correctAnswer);
                    test.add(t);
                    break;
            }
        }

        totalQuestions =test.size();
        Collections.shuffle(test);
        lblScore.setText("0/"+totalQuestions);
        System.out.println("preguntas: "+totalQuestions);
        alreadyAnswered =true;
        generateQuestion();
    }

    public void generateQuestion()
    {
        Alert dialog;
        if(actualQuestion < totalQuestions) {
            if (alreadyAnswered) {
                txtQuestion.setText(test.get(actualQuestion).getQuestion());
                lblAnswer1.setText(test.get(actualQuestion).getAnswer1());
                lblAnswer2.setText(test.get(actualQuestion).getAnswer2());
                lblAnswer3.setText(test.get(actualQuestion).getAnswer3());
                alreadyAnswered = false;
            } else {
                dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error");
                dialog.setHeaderText("");
                dialog.setContentText(" First check this question! It's not answered!");
                dialog.showAndWait();

            }
        }
        else
            this.cerrarVentana();


    }

    public void checkQuestion(ActionEvent actionEvent)
    {
        //Creo que este podria ser un método por  ejemplo, deberiamos pasarle las respuestas y podemos avisar al usuario
        //A través de  ventanas si acierta o falla..
        Alert dialog;
        if(!alreadyAnswered) {
            int actualScore = Integer.parseInt(lblScore.getText().split("/")[0]);

            int correctAnswer = test.get(actualQuestion).correctAnswer;
            boolean success = false;
            if (c1.isSelected()) {
                if (correctAnswer == 1)
                    success = true;
            }
            if (c2.isSelected()) {
                if (correctAnswer == 2)
                    success = true;
            }
            if (c3.isSelected()) {
                if (correctAnswer == 3)
                    success = true;
            }

            if (!success) {
                dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setTitle("Error");
                dialog.setHeaderText("");
                dialog.setContentText("It was the option " +test.get(actualQuestion).correctAnswer);
                dialog.showAndWait();
                alreadyAnswered = true;
                actualQuestion++;
            } else {
                dialog = new Alert(Alert.AlertType.INFORMATION);
                dialog.setTitle("Good Job");
                dialog.setHeaderText("");
                dialog.setContentText("Perfect!");
                dialog.showAndWait();
                actualScore++;
                lblScore.setText(actualScore + "/" + lblScore.getText().split("/")[1]);
                alreadyAnswered = true;
                actualQuestion++;
            }
        }
        else
        {
            dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("");
            dialog.setContentText("Question already answered!");
            dialog.showAndWait();
        }

    }
}
