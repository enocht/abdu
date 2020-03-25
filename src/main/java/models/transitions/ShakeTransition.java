/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.transitions;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Animate a shake effect on the given node
 * 
 * Port of Shake from Animate.css http://daneden.me/animate by Dan Eden
 * 
 * {@literal @}keyframes shake {
 * 	0%, 100% {transform: translateX(0);}
 * 	10%, 30%, 50%, 70%, 90% {transform: translateX(-10px);}
 * 	20%, 40%, 60%, 80% {transform: translateX(10px);}
 * }
/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class ShakeTransition extends CachedTimelineTransition {
       /**
     * Create new ShakeTransition
     * 
     * @param node The node to affect
     */
    public ShakeTransition(final Node node) {
        super(node, new Timeline(
                    new KeyFrame(Duration.millis(0), new KeyValue(node.translateXProperty(), 0, WEB_EASE)),
                    new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(400), new KeyValue(node.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(600), new KeyValue(node.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(700), new KeyValue(node.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(800), new KeyValue(node.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(900), new KeyValue(node.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(node.translateXProperty(), 0, WEB_EASE))));
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
    
    public ShakeTransition(final Label label) {
        super(
            label,
                new Timeline(
                    new KeyFrame(Duration.millis(0), new KeyValue(label.translateXProperty(), 0, WEB_EASE)),
                    new KeyFrame(Duration.millis(100), new KeyValue(label.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(200), new KeyValue(label.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(300), new KeyValue(label.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(400), new KeyValue(label.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(500), new KeyValue(label.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(600), new KeyValue(label.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(700), new KeyValue(label.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(800), new KeyValue(label.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(900), new KeyValue(label.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(label.translateXProperty(), 0, WEB_EASE))
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
    
    public ShakeTransition(final ComboBox combo) {
        super(
            combo,
                new Timeline(
                    new KeyFrame(Duration.millis(0), new KeyValue(combo.translateXProperty(), 0, WEB_EASE)),
                    new KeyFrame(Duration.millis(100), new KeyValue(combo.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(200), new KeyValue(combo.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(300), new KeyValue(combo.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(400), new KeyValue(combo.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(500), new KeyValue(combo.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(600), new KeyValue(combo.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(700), new KeyValue(combo.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(800), new KeyValue(combo.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(900), new KeyValue(combo.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(combo.translateXProperty(), 0, WEB_EASE))
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
    
    public ShakeTransition(final TextField textfield) {
        super(
            textfield,
            new Timeline(
                    new KeyFrame(Duration.millis(0), new KeyValue(textfield.translateXProperty(), 0, WEB_EASE)),
                    new KeyFrame(Duration.millis(100), new KeyValue(textfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(200), new KeyValue(textfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(300), new KeyValue(textfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(400), new KeyValue(textfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(500), new KeyValue(textfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(600), new KeyValue(textfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(700), new KeyValue(textfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(800), new KeyValue(textfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(900), new KeyValue(textfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(textfield.translateXProperty(), 0, WEB_EASE))
                )
        );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
    
    public ShakeTransition(final PasswordField passwordfield) {
        super(
            passwordfield,
                new Timeline(
                    new KeyFrame(Duration.millis(0), new KeyValue(passwordfield.translateXProperty(), 0, WEB_EASE)),
                    new KeyFrame(Duration.millis(100), new KeyValue(passwordfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(200), new KeyValue(passwordfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(300), new KeyValue(passwordfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(400), new KeyValue(passwordfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(500), new KeyValue(passwordfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(600), new KeyValue(passwordfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(700), new KeyValue(passwordfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(800), new KeyValue(passwordfield.translateXProperty(), 10, WEB_EASE)),
                    new KeyFrame(Duration.millis(900), new KeyValue(passwordfield.translateXProperty(), -10, WEB_EASE)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(passwordfield.translateXProperty(), 0, WEB_EASE))
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
    
    
}
