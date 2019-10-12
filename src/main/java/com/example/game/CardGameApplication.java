package com.example.game;

import com.example.game.constants.CardValue;
import com.example.game.constants.Constants;
import com.example.game.constants.Suite;
import com.example.game.model.Card;
import com.example.game.model.Player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Stack;

/**
 * Created by akash on 23/11/18.
 */
public class CardGameApplication {

    private CardGameEngine cardGameEngine;
    private Player[] players;
    private Stack<Card> deck;

    public static void main(String args[]) {
        CardGameApplication cardGameApplication = new CardGameApplication();
        cardGameApplication.createPlayers();
        Card[] deckCards = cardGameApplication.createCards();
        Card[] shuffledDeckCards =  cardGameApplication.shuffleCards(deckCards);
        cardGameApplication.createFinalDeck(shuffledDeckCards);
        cardGameApplication.distributeCards();
        cardGameApplication.initializeEngine();
        cardGameApplication.runGame();
    }

    private void createFinalDeck(Card[] deckCards) {
        deck = new Stack<>();
        for(Card card : deckCards) {
            deck.push(card);
        }
    }

    private Card[] shuffleCards(Card[] deckCards) {
        Random rand = new Random();

        for (int i = 0; i < Constants.DECK_SIZE; i++)
        {
            int r = i + rand.nextInt(Constants.DECK_SIZE - i);
            Card temp = deckCards[r];
            deckCards[r] = deckCards[i];
            deckCards[i] = temp;
        }
        return deckCards;
    }

    private void runGame() {
        cardGameEngine.runGame();
    }

    private void initializeEngine() {
        cardGameEngine = new CardGameEngine(getRandomStartingPlayerId(), Suite.randomSuite(), players);
    }


    private int getRandomStartingPlayerId() {
        return new Random().nextInt(Constants.NUMBER_OF_PLAYERS);
    }

    private void distributeCards() {
        int count = 0;
        while(!deck.empty()) {
            Player player = players[count % Constants.NUMBER_OF_PLAYERS];
            ArrayList<Card> playerHand = player.getHand();
            playerHand.add(deck.pop());
            count++;
        }
    }

    private Card[] createCards() {
        EnumSet<Suite> allSuites = EnumSet.allOf(Suite.class);
        EnumSet<CardValue> allCardValues = EnumSet.allOf(CardValue.class);
        Card[] deckCards = new Card[Constants.DECK_SIZE];
        int index = -1;
        for(Suite suite : allSuites) {
            for(CardValue cardValue: allCardValues) {
                Card card = new Card(suite, cardValue);
                deckCards[++index] = card;
            }
        }
        return deckCards;
    }

    private void createPlayers() {
        players = new Player[Constants.NUMBER_OF_PLAYERS];
        for(int i=0; i < players.length; i++) {
            players[i] = new Player();
        }
    }
}
