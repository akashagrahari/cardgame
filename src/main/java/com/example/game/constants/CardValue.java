package com.example.game.constants;

import lombok.Getter;

/**
 * Created by akash on 23/11/18.
 */
public enum CardValue {
    Ace (13),
    King (12),
    Queen (11),
    Jack (10),
    Ten (9),
    Nine (8),
    Eight (7),
    Seven (6),
    Six (5),
    Five (4),
    Four (3),
    Three (2),
    Two (1);

    @Getter
    private final int value;

    CardValue(int value) {
        this.value = value;
    }

}
