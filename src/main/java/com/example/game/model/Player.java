package com.example.game.model;

import com.example.game.constants.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by akash on 23/11/18.
 */
@Getter
@Setter
public class Player {

    private ArrayList<Card> hand;
    private int score;

    public Player() {
        hand = new ArrayList<>(Constants.HAND_SIZE);
        score = 0;
    }

    public void incrementScore() {
        this.score++;
    }
}
