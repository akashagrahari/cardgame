package com.example.game.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by akash on 23/11/18.
 */
public enum Suite {
    SPADE ("Spade"),
    CLUB ("Club"),
    DIAMOND ("Diamond"),
    HEART ("Heart");

    private static final List<Suite> SUITE_VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int TOTAL_NUMBER_OF_SUITES = SUITE_VALUES.size();
    private static final Random RANDOM = new Random();

    @Getter
    private final String value;

    Suite(String value) {
        this.value = value;
    }

//    public String toString() {
//        return this.suiteValue;
//    }

    public static Suite randomSuite()  {
        return SUITE_VALUES.get(RANDOM.nextInt(TOTAL_NUMBER_OF_SUITES));
    }

}
