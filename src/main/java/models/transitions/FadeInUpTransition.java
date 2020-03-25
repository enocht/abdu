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
import javafx.util.Duration;

/**
 * Animate a fade in up effect on a node
 * 
 * Port of FadeInUp from Animate.css http://daneden.me/animate by Dan Eden
 * 
 * {@literal @}keyframes fadeInUp {
 * 	0% {
 * 		opacity: 0;
 * 		transform: translateY(20px);
 * 	}
 * 	100% {
 * 		opacity: 1;
 * 		transform: translateY(0);
 * 	}
 * 

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class FadeInUpTransition extends CachedTimelineTransition{
    
     /**
     * Create new FadeInUpTransition
     * 
     * @param node The node to affect
     */
    public FadeInUpTransition(final Node node) {
        super(
            node,
                new Timeline(
                    new KeyFrame(Duration.millis(0),    
                        new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(node.translateYProperty(), 20, WEB_EASE)
                    ),
                    new KeyFrame(Duration.millis(1000),    
                        new KeyValue(node.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(node.translateYProperty(), 0, WEB_EASE)
                    )
                )
            );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0));
    }
    
}
