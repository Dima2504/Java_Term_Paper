package sample;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import objects.micro.Shopper;
import objects.secondMacro.Building;
import objects.secondMacro.Underpass;
import sample.windows.aboutWindow.AboutWindow;
import sample.windows.createShopperWindow.CreateShopperWindow;
import sample.windows.preferencesWindow.PreferencesWindow;
import java.io.*;
import java.util.Optional;


public class Controller {

    @FXML
    private MenuItem menuFileOpen;

    @FXML
    private MenuItem menuFileSave;

    @FXML
    private MenuItem menuFileExit;

    @FXML
    private MenuItem menuEditInteract;

    @FXML
    private MenuItem menuEditPlay;

    @FXML
    private MenuItem menuEditSell;

    @FXML
    private MenuItem menuEditCreate;

    @FXML
    private MenuItem menuEditDelete;

    @FXML
    private MenuItem menuEditCancel;

    @FXML
    private MenuItem menuEditProperties;

    @FXML
    private MenuItem menuWindowsCreate;

    @FXML
    private MenuItem menuWindowsProperties;

    @FXML
    private MenuItem menuMoreAbout;

    @FXML
    void initialize() {
        EventHandler<ActionEvent> menuHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String buttonName = ((MenuItem)event.getTarget()).getText();
                if (buttonName.equals("Зберегти")){
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Виберіть місце для збереження");
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML-збереження", "*.xml"),
                            new FileChooser.ExtensionFilter("TXT-збереження", "*.txt"),
                            new FileChooser.ExtensionFilter("Усі файли","*.*"));
                    File file = fileChooser.showSaveDialog(Main.getScene().getWindow());
                    if (file != null)
                        Serialization.serializeNow(file);
                } else if (buttonName.equals("Відкрити")){

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Ви дійсно хочете це зробити?");
                    alert.setContentText("Переконайтеся, що поточна гра збережена");
                    Optional<ButtonType> option = alert.showAndWait();
                    if (option.get() == ButtonType.OK) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Виберіть файл");
                        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML-збереження", "*.xml"),
                                new FileChooser.ExtensionFilter("TXT-збереження", "*.txt"),
                                new FileChooser.ExtensionFilter("Усі файли", "*.*"));
                        File file = fileChooser.showOpenDialog(Main.getScene().getWindow());
                        if (file != null)
                            Serialization.deserializeNow(file);
                    }
                }

                else if (buttonName.equals("Вийти")){
                    Platform.exit();
                } else if (buttonName.equals("Взаємодіяти з будівлею")){
                    for (Shopper shopper : Main.shoppers) {
                        if (shopper.isActive()) {
                            for (Building b : Main.buildings) {
                                b.interact(shopper, false);
                            }
                        }
                    }
                } else if (buttonName.equals("Продати (тільки магазин)")) {
                    for (Shopper shopper : Main.shoppers) {
                        if (shopper.isActive()) {
                            for (Building b : Main.buildings) {
                                b.interact(shopper, true);
                            }
                        }
                    }
                } else if (buttonName.equals("Грати на інструменті")){
                    for (Shopper shopper : Main.shoppers) {
                        if (shopper.isActive() && shopper.getInstrument() != null) {
                            shopper.playAnInstrument();
                            break;
                        }
                    }
                }
                else if (buttonName.equals("Створити персонажа")) {
                    try {
                        new CreateShopperWindow().display("Let's create new Shopper!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Видалити персонажей")){
                    for (int i = 0; i < Main.shoppers.size(); i++) {
                        Shopper shopper = Main.shoppers.get(i);
                        if (shopper.isActive()) {
                            Main.deleteAShopper(shopper);
                            i--;
                        }

                    }
                    System.out.println(Shopper.getNumberOfShoppers()+"----");
                    System.out.println(Main.shoppers.size());
                } else if (buttonName.equals("Відмінити вибір")){
                    for(Shopper s: Main.shoppers){
                        if (s.isActive()) {
                            s.setActive(false);
                        }
                    }
                } else if (buttonName.equals("Налаштування")){
                    try {
                        new PreferencesWindow().display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Створення нового персонажа")){
                    try {
                        new CreateShopperWindow().display("Let's create new Shopper!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Вікно налаштувань")){
                    try {
                        new PreferencesWindow().display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (buttonName.equals("Про програму")){
                    try {
                        new AboutWindow().display();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        menuFileOpen.setOnAction(menuHandler);
        menuFileOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        menuFileSave.setOnAction(menuHandler);
        menuFileSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        menuFileExit.setOnAction(menuHandler);
        menuFileExit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        menuEditInteract.setOnAction(menuHandler);
        menuEditInteract.setAccelerator(KeyCombination.keyCombination("J"));
        menuEditSell.setOnAction(menuHandler);
        menuEditSell.setAccelerator(KeyCombination.keyCombination("J+shift"));
        menuEditPlay.setOnAction(menuHandler);
        menuEditPlay.setAccelerator(KeyCombination.keyCombination("K"));
        menuEditCreate.setOnAction(menuHandler);
        menuEditCreate.setAccelerator(KeyCombination.keyCombination("M"));
        menuEditDelete.setOnAction(menuHandler);
        menuEditDelete.setAccelerator(KeyCombination.keyCombination("Delete"));
        menuEditCancel.setOnAction(menuHandler);
        menuEditCancel.setAccelerator(KeyCombination.keyCombination("Esc"));
        menuEditProperties.setOnAction(menuHandler);
        menuEditProperties.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));
        menuWindowsCreate.setOnAction(menuHandler);
        menuWindowsCreate.setAccelerator(KeyCombination.keyCombination("M"));
        menuWindowsProperties.setOnAction(menuHandler);
        menuWindowsProperties.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));
        menuMoreAbout.setOnAction(menuHandler);
        menuMoreAbout.setAccelerator(KeyCombination.keyCombination("f1"));
    }
}
