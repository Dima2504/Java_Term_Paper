package objects.micro;


import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import objects.firstMacro.Guitar;


import java.util.*;

public class Shopper implements Cloneable /*, Comparable<Shopper>*/{


    private ImageView shopperImage;
    private ImageView instrumentImage;
    private Label shopperText;
    private boolean isActive;


    public Shopper(Image shopperImage,Image instrumentImage,boolean isActive) {
        this.isActive = isActive;

        this.shopperImage = new ImageView(shopperImage);
        this.instrumentImage = new ImageView(instrumentImage);
        this.shopperImage.setPreserveRatio(true);
        this.instrumentImage.setPreserveRatio(true);
        this.shopperImage.setFitHeight(190);
        this.instrumentImage.setFitHeight(80);

    }

    public void setShopperCoords(double x, double y){
        this.shopperImage.setX(x);
        this.shopperImage.setY(y);
        this.instrumentImage.setX(x+10);
        this.instrumentImage.setY(y+70);
        this.shopperText = new Label("TEXT");
        this.shopperText.setFont(new Font("Segoe UI Black Italic", 13));
        this.shopperText.setLayoutX(x);
        this.shopperText.setLayoutY(y+190);

    }

    public ImageView getShopperImage() {
        return shopperImage;
    }

    public ImageView getInstrumentImage() {
        return instrumentImage;
    }

    public Label getShopperText() {
        return shopperText;
    }
    public void mouseClick(double x, double y){
        Point2D point2D = new Point2D(x,y);
        if (this.shopperImage.getBoundsInParent().contains(point2D)){
            System.out.println("click");
        }
    }



/*
    private Guitar guitar= null;
    private double money;
    private String name;
    private int age;
    private ArrayList<String> skills;
    private static int numberOfShoppers = 0;



    public static int getNumberOfShoppers() {
        return numberOfShoppers;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<String> getSkills() {
        for (String s : skills) {
            System.out.println(s);
        }
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    public void removeSkill(String skill) {
        this.skills.remove(skill);
    }


    public Guitar getGuitar() {
        return guitar;
    }

    public void setGuitar(Guitar guitar) {
        this.guitar = guitar;
    }

    public Shopper(double money, String name, int age, ArrayList<String> skills) {
        System.out.println("_________________________________________");
        System.out.println("Був викликаний конструктор класу Shopper.");
        this.money = money;
        this.name = name;
        this.age = age;
        this.skills = skills;
        System.out.println("Був створений об'єкт " + this.name + " класу Shopper.");
        System.out.println("_____________________________________________________");
        numberOfShoppers++;

    }

    public Shopper(double money, String name, int age) {
        this(money,name,age, new ArrayList<String>());
    }

    public Shopper() {

        this(0, "Name is not specified", 0, new ArrayList<String>());
    }



    public void education(String skill) {
        switch (skill) {
            case "Guitar":
                if (this.money >= 30) {
                    System.out.println("Ви навчилися грати на гітарі.");
                    this.skills.add(skill);
                    this.money -= 30;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на гітарі.");
                break;
            case "Piano":
                if (this.money >= 60) {
                    System.out.println("Ви навчилися грати на піаніно.");
                    this.skills.add(skill);
                    this.money -= 60;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на піаніно.");
                break;
            case "Bayan":
                if (this.money >= 90) {
                    System.out.println("Ви навчилися грати на баяні.");
                    this.skills.add(skill);
                    this.money -= 90;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на баяні.");
                break;
            case "Violin":
                if (this.money >= 120) {
                    System.out.println("Ви навчилися грати на скрипці.");
                    this.skills.add(skill);
                    this.money -= 120;
                } else System.out.println("Недостатньо  коштів, щоб навчитися грати на скрипці.");
                break;
            default:
                System.out.println("На даний момент на такому інструменті неможливо навчитися грати.\nВибирайте із даних: \"Guitar\", \"Piano\", \"Bayan\", \"Violin\"");
                break;


        }
    }

    public void earnMoney() {
        if (this.skills.contains("Guitar") && (guitar!=null)) {
            Date d = new Date();
            Random r = new Random(d.getTime());
            int kesh = r.nextInt(50);
            String s = Integer.toString(kesh);
            System.out.println("Ви грали цілий день у метро і заробили " + s + " грошей.");
            this.money += kesh;
        } else if (!this.skills.contains("Guitar")) System.out.println("Ви не вмієте грати на гітарі");
        else if (guitar==null) System.out.println("Спочатку придбайте гітару");

    }
    public void buyGuitar(Guitar guitar)
    {
        if (guitar.getCost()<= this.money) {
            this.guitar = guitar;
            this.money-=guitar.getCost();
            System.out.println("Ви успішно придбали гітару.");
        }
            else System.out.println("Недостатньо коштів, щоб придбати гітару.");
    }


    @Override
    public String toString() {
        return "Shopper{" +
                "money=" + money +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", skills=" + skills +
                '}';
    }

    @Override
    protected Shopper clone() throws CloneNotSupportedException {
        Shopper shopper = (Shopper)super.clone();
        if (guitar !=null)
            shopper.guitar = guitar.clone();

        shopper.name = name;
        shopper.skills = new ArrayList<String>(skills);
        return shopper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shopper shopper = (Shopper) o;
        return Double.compare(shopper.money, money) == 0 &&
                age == shopper.age &&
                Objects.equals(guitar, shopper.guitar) &&
                Objects.equals(name, shopper.name) &&
                Objects.equals(skills, shopper.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guitar, money, name, age, skills);
    }

    *//*@Override
    public int compareTo(Shopper o) {
        if (o.getAge()>this.getAge())
            return -1;
        else if (o.getAge()<this.getAge())
            return 1;
        return 0;
    }*//*
    //переписаний метод для сортування по віку з взятий з інтерфейсу compareble


    public static Comparator<Shopper> moneyComparator = new Comparator<Shopper>() {
        @Override
        public int compare(Shopper o1, Shopper o2) {
            if (o1.money<o2.money)
                return -1;
            else if (o1.money>o2.money)
                return 1;
            return 0;
        }
    };
    public static Comparator<Shopper> ageComparator = new Comparator<Shopper>() {
        @Override
        public int compare(Shopper o1, Shopper o2) {
            if (o1.age<o2.age)
                return -1;
            else if (o1.age>o2.age)
                return 1;
            return 0;
        }
    };
    public static Comparator<Shopper> nameComparator = new Comparator<Shopper>() {
        @Override
        public int compare(Shopper o1, Shopper o2) {
            return o1.name.compareTo(o2.name);
        }
    };*/

}







