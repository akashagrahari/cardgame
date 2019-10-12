package com.example.game;

import com.example.game.constants.CardValue;
import com.example.game.constants.Constants;
import com.example.game.constants.Suite;
import com.example.game.model.Card;
import com.example.game.model.Player;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by akash on 23/11/18.
 */
public class CardGameEngine {
    private int roundLeaderPlayerId;
    private Suite trumpSuit;
    private Player[] players;
    private ArrayList<Card> table;

    public CardGameEngine(int roundLeaderPlayerId, Suite trumpSuit, Player[] players) {
        this.roundLeaderPlayerId = roundLeaderPlayerId;
        this.trumpSuit = trumpSuit;
        this.players = players;
        this.table = new ArrayList<>(Constants.NUMBER_OF_PLAYERS);
    }

    public void runGame() {
        for(int round = 1; round <= Constants.HAND_SIZE; round++) {
            roundLeaderPlayerId = executeRound(roundLeaderPlayerId);
            clearTable();
        }
        int gameWinnerPlayerId = gameWinnerPlayerId();
        System.out.println("The Winner is Player Id "+ gameWinnerPlayerId);
    }

    private int gameWinnerPlayerId() {
        int highestScore = players[0].getScore();
        int winnerId = 0;
        for(int i=1; i < Constants.NUMBER_OF_PLAYERS; i++) {
            if(players[i].getScore()> highestScore) {
                highestScore = players[i].getScore();
                winnerId = i;
            }
        }
        return winnerId;
    }

    private void clearTable() {
        this.table = new ArrayList<>(Constants.NUMBER_OF_PLAYERS);
    }

    //    Executes round, returns the winner of the turn
    private int executeRound(int roundLeaderPlayerId) {
        for(int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
            int currentPlayerId = (roundLeaderPlayerId + i) % Constants.NUMBER_OF_PLAYERS;
            if (i==0) {
                executeFirstTurn(currentPlayerId);
            }
            else {
                executeOtherTurn(currentPlayerId);
            }
        }
        int winnerPlayerNumber = getWinnerPlayerNumberForTable();
        int winnerPlayerId = (roundLeaderPlayerId + winnerPlayerNumber) % Constants.NUMBER_OF_PLAYERS;
        players[winnerPlayerId].incrementScore();
        return winnerPlayerId;
    }

    private int getWinnerPlayerNumberForTable() {
        Suite suiteOfHand = table.get(0).getSuiteValue();
        if(!tableContainsTrumpCards()) {
            return getPlayerTurnForHighestCardOnTableAndSuite(suiteOfHand);
        }
        else {
            return getPlayerTurnForHighestCardOnTableAndSuite(trumpSuit);
        }
    }

    private int getPlayerTurnForHighestCardOnTableAndSuite(Suite suiteOfHand) {
        int playerTurn = 0;
        int highestCardValue = table.get(0).getCardValue().getValue();
        for(int i=1; i< Constants.NUMBER_OF_PLAYERS; i++) {
            if(table.get(i).getSuiteValue().equals(suiteOfHand) &&
                    table.get(i).getCardValue().getValue() > highestCardValue) {
                playerTurn = i;
                highestCardValue = table.get(i).getCardValue().getValue();
            }
        }
        return playerTurn;
    }

    private boolean tableContainsTrumpCards() {
        for(Card card : table) {
            if(card.getSuiteValue().equals(trumpSuit)) {
                return true;
            }
        }
        return false;
    }

    private void executeOtherTurn(int playerId) {
        Player player = players[playerId];
        Suite suiteOfHand = table.get(0).getSuiteValue();
        ArrayList<Card> hand = player.getHand();
        Optional<Card> highestCardForSuite = getHighestCardForSuite(hand, suiteOfHand);
        if(highestCardForSuite.isPresent()) {
            if(isCardGreaterThanTableCards(highestCardForSuite.get())) {
                executePlay(playerId, highestCardForSuite.get(), hand);
            }
            else {
                Optional<Card> lowestCardForSuite = getLowestCardForSuite(hand, suiteOfHand);
                if(lowestCardForSuite.isPresent()) {
                    executePlay(playerId, lowestCardForSuite.get(), hand);
                }
            }
        }
        else {
            Optional<Card> lowestTrumpCardBiggerThanTableCards = getLowestCardForSuiteBiggerThanTableCards(trumpSuit);
            if(lowestTrumpCardBiggerThanTableCards.isPresent()) {
                executePlay(playerId, lowestTrumpCardBiggerThanTableCards.get(), hand);
            }
            else {
//                Optional<Card> lowestCardButNotTrump
            }
        }

    }

    private void executePlay(int playerId, Card card, ArrayList<Card> hand) {
        hand.remove(card);
        table.add(card);
        System.out.println("Player Id "+ playerId + " turn - "+ card.getCardValue() + " of " + card.getSuiteValue());
    }

    private Optional<Card> getLowestCardForSuiteBiggerThanTableCards(Suite trumpSuit) {
        return Optional.empty();
    }

    private boolean isCardGreaterThanTableCards(Card card) {
        for(Card tableCard: table) {
            if(tableCard.getSuiteValue().getValue().equals(card.getSuiteValue().getValue())) {
                if(card.getCardValue().getValue() < tableCard.getCardValue().getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void executeFirstTurn(int playerId) {
        Player player = players[playerId];
        ArrayList<Card> hand = player.getHand();
        Card highestCardInHand =  getHighestCardInHand(hand);
        executePlay(playerId, highestCardInHand, hand);
    }

    private Optional<Card> getHighestCardForSuite(ArrayList<Card> hand, Suite suite) {
        Card highestCardForSuite = new Card(suite, CardValue.Two);
        for(Card card : hand) {
            if(card.getSuiteValue().equals(suite) && card.getCardValue().getValue() >= highestCardForSuite.getCardValue().getValue()) {
                highestCardForSuite = card;
            }
        }
        if(hand.contains(highestCardForSuite)) {
            return Optional.of(highestCardForSuite);
        }
        else {
            return Optional.empty();
        }
    }

    private Optional<Card> getLowestCardForSuite(ArrayList<Card> hand, Suite suite) {
        Card lowestCard = new Card(suite, CardValue.King);
        for(Card card : hand) {
            if(card.getCardValue().getValue() < lowestCard.getCardValue().getValue() && card.getSuiteValue().equals(suite)) {
                lowestCard = card;
            }
        }
        if(hand.contains(lowestCard)) {
            return Optional.of(lowestCard);
        }
        else {
            return Optional.empty();
        }
    }

    private Card getHighestCardInHand(ArrayList<Card> hand) {
        Card highestCard = new Card(Suite.CLUB, CardValue.Two);
        for(Card card : hand) {
            if(card.getCardValue().getValue() > highestCard.getCardValue().getValue()) {
                highestCard = card;
            }
        }
        return highestCard;
    }
}
