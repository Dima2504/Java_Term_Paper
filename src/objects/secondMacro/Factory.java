package objects.secondMacro;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import objects.micro.Shopper;
import sample.Main;
import sample.Sprite;
import sample.windows.preferencesWindow.Preferences;

import java.nio.file.Paths;
import java.util.EventListener;


public class Factory extends Building{

    private ImageView smokeSprite = new ImageView(new Image("assets/sprite.png"));
    private Animation smokeAnimation;

    public Factory(double xChord, double yChord){
        this.xChord = xChord;
        this.yChord = yChord;
        Building.numberOfBuildings++;
        this.buildingType = "Factory";
        this.buildingImage = new ImageView(new Image("assets/factory.png"));
        this.buildingImage.setPreserveRatio(true);
        this.buildingImage.setFitHeight(270);
        this.buildingText = new Label("Factory");
        this.buildingText.setFont(new Font("Segoe UI Black Italic", 13));
        this.shadow = new Ellipse(175, 40);
        this.shadow.setFill(Color.BLACK);
        this.shadow.setOpacity(0.8);
        this.shadow.getTransforms().add(new Rotate(10));
        this.shadow.setEffect(new GaussianBlur(40));
        this.smokeSprite.setViewport(new Rectangle2D(0,100,100,100)); // це погане рішення для того щоб зробити спрайт диму невидимий, але воно працює!
        this.smokeSprite.setPreserveRatio(true);
        this.smokeSprite.setFitHeight(60);
        this.smokeAnimation = new Sprite(smokeSprite, Duration.millis(1000), 10, 10, 0, 0,100,100 );
        this.smokeAnimation.setCycleCount(Animation.INDEFINITE);

        this.setBuildingInChords();
        this.buildingPicture = new Group(shadow, buildingImage, buildingText, smokeSprite);
        smokeAnimation.play();
    }

    @Override
    public void setBuildingInChords() {
        double x = this.xChord;
        double y = this.yChord;
        this.buildingImage.setX(x);
        this.buildingImage.setY(y);
        this.buildingText.setLayoutX(x+179);
        this.buildingText.setLayoutY(y+280);
        this.shadow.setLayoutX(x+90);
        this.shadow.setLayoutY(y+230);

        this.smokeSprite.setX(x+190);
        this.smokeSprite.setY(y-70);
    }

    public Animation smoke(){
        return this.smokeAnimation;
    }
    @Override
    public void interact(Shopper shopper, boolean isShiftDown) {
        if (shopper.getShopperImage().getBoundsInParent().intersects(this.getBuildingImage().getBoundsInParent())) {
            if (shopper.isMan()) {


                shopper.getShopperImage().setOpacity(0);

                ImageView shopperWorkSprite = new ImageView(new Image("assets/shopperWorkSprite.png"));
                shopperWorkSprite.setX(shopper.getShopperImage().getX());
                shopperWorkSprite.setY(shopper.getShopperImage().getY());
                shopperWorkSprite.setPreserveRatio(true);
                shopperWorkSprite.setFitHeight(shopper.getShopperImage().getFitHeight());


                shopper.getShopperPicture().getChildren().add(shopperWorkSprite);
                shopper.setStay(true);
                if (shopper.getInstrument() != null) {
                    shopper.getInstrument().getInstrumentImage().setOpacity(0);
                }
                Animation workAnimation = new Sprite(shopperWorkSprite, Duration.millis(1500), 8, 8, 0, 0, 498, 684);
                workAnimation.setCycleCount(20);//як довго буде працювати


                AudioClip mediaPlayer = null;
                try {
                    Media hit = new Media(Paths.get("src/assets/music/dyHast.mp3").toUri().toString());
                    mediaPlayer = new AudioClip(hit.getSource());
                    mediaPlayer.setVolume(Preferences.getVOLUME()*0.05);
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

                    shopper.setMoney(shopper.getMoney() +Preferences.getCOMPLEXITY().getSalary());
                    shopper.getShopperImage().setOpacity(1);
                    shopper.getShopperPicture().getChildren().remove(shopperWorkSprite);
                    shopper.setStay(false);
                    finalMediaPlayer.stop();
                    if (shopper.getInstrument() != null) {
                        shopper.getInstrument().getInstrumentImage().setOpacity(1);
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Жінки не можуть працювати на тяжкій роботі");
                alert.showAndWait();
            }
        }
    }

}
