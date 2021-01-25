package javafx.src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Checker {
    public static ImageView redImageView;
    public static ImageView blackImageView;
    
    public Checker(String s) {
        redImageView = new ImageView(new Image("red_piece.png"));
    }

    
}
