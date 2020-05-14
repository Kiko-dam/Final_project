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
    public  Stage stage;
    public  Scene scene;
    public Button btnStart;
    public Button btnCheckQuestion;
    public Label lblAnswers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Once the user selects a unit and a section, we generate the appropriate test.
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage = new Stage();
        stage.setTitle("-Test " + sectionName);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void loadTest()
    {
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
        //Here the test is loaded from the file. Once loaded the list is shuffled so
        //that the questions do not appear in the same order.
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
    }

    public void generateQuestion(ActionEvent actionEvent)
    {
        //This method is in charge of loading the next (question / answers) once the previous one has been answered.
        Alert dialog;
        if(actualQuestion < totalQuestions) {

                txtQuestion.setText(test.get(actualQuestion).getQuestion());
                lblAnswer1.setText(test.get(actualQuestion).getAnswer1());
                lblAnswer2.setText(test.get(actualQuestion).getAnswer2());
                lblAnswer3.setText(test.get(actualQuestion).getAnswer3());
        }
        else {
            dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("Test results");
            dialog.setHeaderText("");
            dialog.setContentText("Great job you did a total of "+
                    lblScore.getText().split("/")[0] +
                    " corrects answers");
            dialog.showAndWait();

            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        }
    }

    public void checkQuestion(ActionEvent actionEvent)
    {
        //When the user marks an answer and confirms his selection.
        //It is checked if it has been successful and the corresponding message is shown.
        Alert dialog;
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

        if(!success) {
            dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("Error");
            dialog.setHeaderText("");
            dialog.setContentText("It was the option " +test.get(actualQuestion).correctAnswer);
            dialog.showAndWait();
            actualQuestion++;
            generateQuestion(actionEvent);
        } else {
            dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("Good Job");
            dialog.setHeaderText("");
            dialog.setContentText("Perfect!");
            dialog.showAndWait();
            actualScore++;
            lblScore.setText(actualScore + "/" + lblScore.getText().split("/")[1]);
            actualQuestion++;
            generateQuestion(actionEvent);
        }

    }

    public void startTest(ActionEvent actionEvent)
    {
        generateQuestion(actionEvent);
        btnStart.visibleProperty().setValue(false);
        btnCheckQuestion.visibleProperty().setValue(true);
        lblAnswer1.visibleProperty().setValue(true);
        lblAnswer2.visibleProperty().setValue(true);
        lblAnswer3.visibleProperty().setValue(true);
        lblScore.visibleProperty().setValue(true);
        txtQuestion.visibleProperty().setValue(true);
        c1.visibleProperty().setValue(true);
        c2.visibleProperty().setValue(true);
        c3.visibleProperty().setValue(true);
        lblScore.visibleProperty().setValue(true);
        lblAnswers.visibleProperty().setValue(true);
    }
}

