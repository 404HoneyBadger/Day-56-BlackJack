
import java.util.Scanner;

public class Game {

 public static int playerMoney = 200;
 public static boolean endRound = false;
 public static int playerBet;
 public static Deck playingDeck = new Deck();

 // create dealer hand, player hand and split hand if it's needed
 public static Deck playerHand = new Deck();
 public static Deck dealerHand = new Deck();

 // initialize variables for player hit or stand choice
 public static String answerdd = "";
 public static String hitOrStand = "";

 public static void main(String[] args) throws InterruptedException {

  System.out.println("Welcome to Blackjack!");

  playingDeck.createFullDeck();
  playingDeck.shuffleDeck();

  // Set up scanner
  Scanner userInput = new Scanner(System.in);

  // Game loops while player still has money
  do {
   // Take the player's bet
   Thread.sleep(800);
   System.out.print("You have $" + playerMoney + ", how much would you like to bet? $");
   playerBet = userInput.nextInt();
   // if statement that will not allow player to bet unless they are betting in
   // increments of $5
   try {
    if (playerBet % 5 != 0) {
     throw new ArithmeticException("Sorry - you are only allowed to bet in $5 increments.");
    }
   } catch (Exception e) {
    System.out.println(e.getMessage());
    continue;
   }

   try {
    if (playerBet > playerMoney) {
     throw new ArithmeticException("You cannot bet more than you have. Please leave.");
    }

   } catch (Exception e) {
    System.out.println(e.getMessage());
    break;
   }

   // Player gets two cards
   playerHand.draw(playingDeck);
   playerHand.draw(playingDeck);

   dealerHand.draw(playingDeck);
   dealerHand.draw(playingDeck);

   endRound = false;
   answerdd = "";

   Thread.sleep(800);
   System.out.println("Your hand: " + playerHand.toString());
   Thread.sleep(800);
   System.out.println("Your hand is valued at: " + playerHand.cardsValue());

   // Display dealer hand - one card is hidden until the player busts or stands
   Thread.sleep(800);
   System.out.println("\nDealer Hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");

   // Player hits second hand or stood on the first hand
   System.out.print("Would you like to hit or stand? ");
   hitOrStand = userInput.next();
  do{
  if(hitOrStand.equalsIgnoreCase("hit")) {
    playerHand.draw(playingDeck);
    Thread.sleep(800);
    System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
    Thread.sleep(800);
    System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
    // determineWinner();
    System.out.print("Would you like to hit or stand? ");
   hitOrStand = userInput.next();
  }
  
}while(hitOrStand=="hit");
  
    // Player stands
    if (hitOrStand.equalsIgnoreCase("stand")) {
     determineWinner();
    }

    playerHas21(playerHand);
    // Bust if > 21
    Thread.sleep(800);
    if (playerHand.cardsValue() > 21) {
     System.out.println("Your hand went over 21. You busted!");
     break;
    }

   }while (playerMoney > 0);
   

   // Move the player's cards and the dealer's cards back into the deck
   playerHand.moveAllToDeck(playingDeck);
   dealerHand.moveAllToDeck(playingDeck);

   Thread.sleep(800);
   System.out.println("End of hand");

  
 }

 // method that determines winner of the hand
 public static void determineWinner() throws InterruptedException {

  // Adjust player balance if they busted
  if (playerHand.cardsValue() > 21) {
   Thread.sleep(800);
   System.out.println("\nYou busted! You lose $" + playerBet + "\n Money to bet with $" + (playerMoney -= playerBet));
   playerMoney -= playerBet;
   playerHand.moveAllToDeck(playingDeck);
   dealerHand.moveAllToDeck(playerHand);
   endRound = true;
  
  }
  // if (playerHand.cardsValue() == 21) {
  //  System.out.println("YOU WIN $" + playerBet + "\n You have $" + 
  //  (playerMoney += playerBet) );
  //  playerMoney += playerBet;
  //  endRound = true;
  // }

  // Dealer draws at 16 and below - stands at 17
  while ((dealerHand.cardsValue() < 17) && !endRound) {
   dealerHand.draw(playingDeck);
   Thread.sleep(800);
   System.out.println("Dealer Draws: " + dealerHand.getCard(dealerHand.deckSize() - 1).toString());
  }

  Thread.sleep(800);
  // Display Total Value for Dealer and Player
  System.out.println(
    "\nDealer's Hand value: " + dealerHand.cardsValue() + ". Player's Hand value: " + playerHand.cardsValue() + ".");

  // Dealer busts if their cards are more than 21
  if ((dealerHand.cardsValue() >21) && !endRound) {
   Thread.sleep(800);
   System.out.println("Dealer busts! You win $" + playerBet + "\nMoney to bet with $" + (playerMoney += playerBet));
   playerMoney += playerBet;
   playerHand.moveAllToDeck(playingDeck);
   dealerHand.moveAllToDeck(playerHand);
   endRound = true;
  
  }

  // Check if dealer beat the player
  if ((dealerHand.cardsValue() >= 17) && (dealerHand.cardsValue() > playerHand.cardsValue()) && !endRound) {
   Thread.sleep(800);
   System.out.println("Dealer beats you! You lose $" + playerBet + "\n Money to bet with $" + (playerMoney -= playerBet));
   playerMoney -= playerBet;
   playerHand.moveAllToDeck(playingDeck);
   dealerHand.moveAllToDeck(playingDeck);
   endRound = true;
  }

  // Determine if it's a push
  if ((playerHand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
   Thread.sleep(800);
   System.out.println("Push");
   endRound = true;
  }

  // Determine the winner of the hand and recalculate the player's betting balance
  if ((playerHand.cardsValue() > dealerHand.cardsValue()) && !endRound) {
   Thread.sleep(800);
   determineWinner();

  } else if (!endRound) {
   Thread.sleep(800);
   System.out.println("You lose the hand! You lose $" + playerBet + ".");
   playerMoney -= playerBet;
   playerHand.moveAllToDeck(playingDeck);
   dealerHand.moveAllToDeck(playingDeck);
  }
 }

 // Player has 21. Method will tell them they have 21 and they will not be able
 // to hit
 public static void playerHas21(Deck playerHand) throws InterruptedException {
  if (playerHand.cardsValue() == 21) {

   Thread.sleep(800);
   System.out.println("You have 21!");
   System.out.println("You win the hand! You win $" + playerBet + ".");
   playerHand.moveAllToDeck(playingDeck);
   dealerHand.moveAllToDeck(playingDeck);
  //  playerMoney += playerBet;
   
  }
 }
}
