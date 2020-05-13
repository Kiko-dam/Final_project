package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import sample.data.Unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ContentController implements Initializable
{
    public ListView listUnits;
    public ListView listSections;
    public TextArea txtArea;
    List<Unit> units = new ArrayList<>();
    public ObservableList<Unit> shownUnits;
    public ObservableList<String> shownSections;
    static int chosenUnit;
    static int chosenSection;
    Alert error = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        showUnits();
        listUnits.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<Unit>) (observable, oldValue, newValue) -> {
                    if (newValue != null)
                    {
                        showSections(newValue.getNumber());
                        chosenUnit = newValue.getNumber();
                    }
                }
        );
    }


    private void showUnits()
    {
        //This method loads a list of units working as an index at the left side listview.
        try {
            BufferedReader inputFile = new BufferedReader(
                    new FileReader(new File("src/texts/unit/units.txt")));
            String line;
            int numberOfUnit = 1;
            do
            {
                line = inputFile.readLine();
                if (line != null)
                {
                    units.add(new Unit(numberOfUnit, line));
                    numberOfUnit++;
                }
            }
            while (line != null);
            inputFile.close();

            shownUnits = FXCollections.observableArrayList(units);
            listUnits.setItems(shownUnits);
        }
        catch (IOException fileError)
        {
            error.setContentText("Ops.. it seems that something has happened while loading the unit content.");
            error.show();
        }
    }

    private void showSections(int numberOfUnit)
    {
        //This method loads a list of sections working as an index at the right side listview.
        String pathOfTitles = "src/texts/unit/" + numberOfUnit + "/sections.txt";
        List<String> sect = new ArrayList<>();
        try {
            BufferedReader inputFile = new BufferedReader(
                    new FileReader(new File(pathOfTitles)));
            String line;
            do
            {
                line = inputFile.readLine();
                if (line != null)
                    sect.add(line);
            }
            while (line != null);
            inputFile.close();

            shownSections = FXCollections.observableArrayList(sect);
            listSections.setItems(shownSections);
        }
        catch (IOException fileError)
        {
            error.setContentText("\n" +
                    "Ops.. it seems that something has happened while loading the section content.");
            error.show();
        }

        listSections.getSelectionModel().selectedItemProperty().addListener(
                (ChangeListener<String>) (observable, oldValue, newValue) -> {
                    if (newValue != null)
                    {
                        ShowContent(newValue);
                        chosenSection= listSections.getSelectionModel().getSelectedIndex()+1;
                    }
                }
        );
    }

    private void ShowContent(String title)
    {
        //Once the user has chosen the topic, the content is loaded according  to the unit  and the section.
        try
        {
            String path = "src/texts/unit/" + chosenUnit + "/" + title + ".txt";
            String content = "";

            List<String> lines = Files.readAllLines(Paths.get(path));
            for (int i = 0; i < lines.size(); i++)
            {
                content += lines.get(i);
                if (lines.get(i).equals(""))
                    content += "\n\n";
            }

            txtArea.setText(content);
        }
        catch (Exception e)
        {
            error.setContentText("Ops.. it seems that something has happened while loading the content.");
            error.show();
        }
    }

    public void loadTest(ActionEvent actionEvent) throws IOException
    {
        if(listSections.getSelectionModel().getSelectedIndex()>=0) {
            TestController newTest = new TestController();
            newTest.openTestWindow(listSections.getSelectionModel().getSelectedItems().toString());
        }
        else
        {

            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("Error");
            dialog.setHeaderText("");
            dialog.setContentText("You must choose the unit and the section to \n " +
                    "generate the appropriate test for your level.");
            dialog.showAndWait();
        }
    }

    public static int getUnit()
    {
        return chosenUnit;
    }


    public static int getSection()
    {
        return chosenSection;
    }
}
