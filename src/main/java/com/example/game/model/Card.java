package com.example.game.model;

import com.example.game.constants.CardValue;
import com.example.game.constants.Suite;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by akash on 23/11/18.
 */

@Getter
@AllArgsConstructor
public class Card {

    private Suite suiteValue;
    private CardValue cardValue;
}
