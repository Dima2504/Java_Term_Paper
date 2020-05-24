package objects.micro;


import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objects.firstMacro.Instrument;
import sample.Main;
import sample.Sprite;
import sample.windows.preferencesWindow.Preferences;

import java.nio.file.Paths;
import java.util.Objects;


public class Shopper implements Cloneable {

    protected String type;
    protected double xChord, yChord;
    protected byte startDirection;

    protected Ellipse shadow;

    protected boolean isActive;
    protected boolean stay;
    protected Polygon triangleAct;


    protected ImageView shopperImage;
    protected boolean isMan;
    protected Label shopperText;

    protected Instrument instrument = null;
    protected Group shopperPicture;

    protected double money;
    protected String name;
    protected static int numberOfShoppers = 0;



    public Shopper(Instrument instrument, boolean isActive, String name, double money, boolean isMan) {
        Shopper.numberOfShoppers++;
        this.type = "Shopper";
        this.name = name;
        this.money = money;
        this.isMan = isMan;
        this.startDirection = (byte) Main.random.nextInt(8);

        if (isMan) {
            this.shopperImage = new ImageView(new Image("assets/shopper.png"));
        } else this.shopperImage = new ImageView(new Image("assets/girlShopper.png"));

        this.isActive = isActive;
        this.stay = false;
        this.shopperImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.shopperImage.setEffect(new DropShadow(30, 0, 0, Color.GRAY));

        this.instrument = instrument;

        this.shopperText = new Label(this.name + ", money: " + this.money);
        this.shopperText.setFont(new Font("Segoe UI Black Italic", 13));

        this.shadow = new Ellipse(75, 20);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.8);
        this.shadow.getTransforms().add(new Rotate(15));
        this.shadow.setEffect(new GaussianBlur(40));

        this.triangleAct = new Polygon();
        triangleAct.getPoints().addAll(0.0, 0.0,
                30.0, 0.0,
                15.0, 25.0);
        this.triangleAct.setFill(Color.BLACK);

        if (this.instrument != null) {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct, this.instrument.getInstrumentImage());
        } else {
            this.shopperPicture = new Group(shopperImage, shopperText, shadow, triangleAct);
        }
    }

    public Shopper(boolean isActive, String name, double money, boolean isMan) {
        this(null, isActive, name, money, isMan);
    }

    public Shopper(Instrument instrument, boolean isActive, String name) {
        this(instrument, isActive, name, 0, true);
    }

    public Shopper(boolean isActive, String name) {
        this(null, isActive, name, 0, true);
    }

    public Shopper(boolean isActive, String name, double money) {
        this(null, isActive, name, money, true);
    }

    public Shopper(String name) {
        this(null, true, name, 0, true);
    }


    public void updateShopperChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.shopperImage.setX(x);
        this.shopperImage.setY(y);

        this.shopperText.setLayoutX(x - 15);
        this.shopperText.setLayoutY(y - 25);
        this.shopperText.setText(this.name + ", money: " + this.money);

        this.shadow.setLayoutX(x - 10);
        this.shadow.setLayoutY(y + 170);

        this.triangleAct.setLayoutX(x + 30);
        this.triangleAct.setLayoutY(y - 40);

        if (this.isActive) {
            this.triangleAct.setOpacity(1);
        } else {
            this.triangleAct.setOpacity(0);
        }
        if (this.instrument != null) {
            this.instrument.update(x, y);
        }

    }

    public void freeRun() {
        if (!this.isActive() && !this.stay) {
            switch (this.startDirection) {
                case 0:
                    this.yChord -= Preferences.getSPEED() / 5;
                    break;
                case 1:
                    this.yChord -= Preferences.getSPEED() / 5;
                    this.xChord += Preferences.getSPEED() / 5;
                    break;
                case 2:
                    this.xChord += Preferences.getSPEED() / 5;
                    break;
                case 3:
                    this.xChord += Preferences.getSPEED() / 5;
                    this.yChord += Preferences.getSPEED() / 5;
                    break;
                case 4:
                    this.yChord += Preferences.getSPEED() / 5;
                    break;
                case 5:
                    this.yChord += Preferences.getSPEED() / 5;
                    this.xChord -= Preferences.getSPEED() / 5;
                    break;
                case 6:
                    this.xChord -= Preferences.getSPEED() / 5;
                    break;
                case 7:
                    this.xChord -= Preferences.getSPEED() / 5;
                    this.yChord -= Preferences.getSPEED() / 5;
                    break;
            }
        }
        if (this.shopperImage.getX() + 100 >= Main.getScene().getWidth() + Main.getScrollX()) {
            this.startDirection = (byte) (Main.random.nextInt(3) + 5);
        } else if (this.shopperImage.getX() <= 0 + Main.getScrollX()) {
            this.startDirection = (byte) (Main.random.nextInt(3) + 1);
        } else if (this.shopperImage.getY() <= 0 + Main.getScrollY()) {
            this.startDirection = (byte) (Main.random.nextInt(3) + 3);
        } else if (this.shopperImage.getY() + 170 >= Main.getScene().getHeight() + Main.getScrollY()) {
            this.startDirection = (byte) Main.random.nextInt(2);
        }

    }

    public Group getShopperPicture() {
        return this.shopperPicture;
    }

    public double getXChord() {
        return xChord;
    }

    public double getYChord() {
        return yChord;
    }


    public void setXYCords(double xChord, double yChord) {
        this.xChord = xChord;
        this.yChord = yChord;
    }

    public void mouseClick(double x, double y) {
        Point2D point2D = new Point2D(x, y);
        if (this.shopperImage.getBoundsInParent().contains(point2D) && !this.stay) {
            this.isActive = !this.isActive;
        }
    }

    public void left(double boost) {
        if (isActive && xChord >= 0) {
            xChord -= Preferences.getSPEED() * boost;
        }
    }

    public void right(double boost) {
        if (isActive && xChord <= Main.getRoot().getWidth() - 100) {
            xChord += Preferences.getSPEED() * boost;
        }
    }

    public void up(double boost) {
        if (isActive && yChord >= 0) {
            yChord -= Preferences.getSPEED() * boost;
        }
    }

    public void down(double boost) {
        if (isActive && yChord <= Main.getRoot().getHeight() - 200) {
            yChord += Preferences.getSPEED() * boost;
        }
    }


    public boolean isActive() {
        return this.isActive;
    }

    public void setStay(boolean stay) {
        this.isActive = false;
        this.stay = stay;
    }

    public void buyAnInstrument(String typeOfInstrument) {
        if (this.money >= Instrument.getInstrument(typeOfInstrument).getPrise()) {
            this.instrument = Instrument.getInstrument(typeOfInstrument);
            this.money -= this.instrument.getPrise();
            this.shopperPicture.getChildren().add(this.instrument.getInstrumentImage());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Увага!");
            alert.setHeaderText("Приходьте пізніше)");
            alert.setContentText("Недостатньо коштів для покупки даного інструмента, прямуйте заробляти");
            alert.showAndWait();
        }
    }
    public void sellAnInstrument(){
        if (this.getInstrument() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Приходьте пізніше");
            alert.setContentText("Будучи початківцев, продавати ваш єдиний інструмент дуже ризиковано.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("У вас немає що продавати");
            alert.showAndWait();
        }
    }

    public Shopper education(boolean first, boolean second, double allPrice) {
        if (first && second && this.money >= allPrice) {// провырку на жынку не забути
            if (this.isMan) {
                return new OrchestraConductor(Instrument.getInstrument(this.instrument.getType()), true, this.name, this.money-allPrice);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Жінки не можуть бути дирижорами");
                alert.showAndWait();
            }
        } else if (first && !second && this.money >= allPrice) {
            return new MusicianMaster(Instrument.getInstrument(this.instrument.getType()), true, this.name,this.money- allPrice,this.isMan);
        } else if (second && !first && this.money>= allPrice) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Спочатку потрібно пройти початкове навчання, а вже потім учитися на дирижора, або ж можна на обох одразу");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Недостаньо коштів");
            alert.showAndWait();
        }
        return this;
    }

    public void playAnInstrument(){

        this.getShopperImage().setOpacity(0);

        ImageView shopperWorkSprite;
        if (this.isMan) {
            shopperWorkSprite = new ImageView(new Image("assets/shopperPlaySprite.png"));
        } else {
            shopperWorkSprite = new ImageView(new Image("assets/girlShopperPlaySprite.png"));

        }
        shopperWorkSprite.setX(this.getShopperImage().getX());
        shopperWorkSprite.setY(this.getShopperImage().getY());
        shopperWorkSprite.setPreserveRatio(true);
        shopperWorkSprite.setFitHeight(this.getShopperImage().getFitHeight());


        this.getShopperPicture().getChildren().add(4 ,shopperWorkSprite);
        this.setStay(true);

        Animation workAnimation = new Sprite(shopperWorkSprite , Duration.millis(2000),4,4,0,0,431,683);
        workAnimation.setCycleCount(10);//як довго буде грати

        String musicPath = "";
        switch (this.instrument.getType()){
            case "Guitar":
                musicPath = new String("src/assets/music/guitarBad.wav");
                break;
            case "Bayan":
                break;
            case "Drums":
                break;
            case "Piano":
                musicPath = new String("src/assets/music/terorist.mp3");

                break;
            case "Trembita":
                musicPath = new String("src/assets/music/trembitaBad.mp3");
                break;
            case "Violin":
                break;
        }

        AudioClip mediaPlayer = null;
        try {
            Media hit = new Media(Paths.get(musicPath).toUri().toString());
            mediaPlayer = new AudioClip(hit.getSource());
            mediaPlayer.setVolume(Preferences.getVOLUME());
            mediaPlayer.play();
        } catch (MediaException m){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Помилка загрузки аудіо!!");
            alert.setContentText(m.toString());
            alert.show();
        }
        AudioClip finalMediaPlayer = mediaPlayer;

        workAnimation.play();

        workAnimation.setOnFinished(event -> {
            this.getShopperImage().setOpacity(1);
            this.getShopperPicture().getChildren().remove(shopperWorkSprite);
            this.setStay(false);
            if (finalMediaPlayer != null) {
                finalMediaPlayer.stop();
            }
        });

    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public static int getNumberOfShoppers() {
        return numberOfShoppers;
    }

    public static void setNumberOfShoppers(int numberOfShoppers) {
        Shopper.numberOfShoppers = numberOfShoppers;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getShopperImage() {
        return shopperImage;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public boolean isMan() {
        return isMan;
    }

    public String getType() {
        return type;
    }

    @Override
    public Shopper clone() throws CloneNotSupportedException {
        Shopper temp = (Shopper) super.clone();
        temp.instrument = instrument.clone();
        return temp;
    }


}







