
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();
    }

    public void createFullDeck() {
        // generate cards
        for (Suits cardSuits :Suits.values()){
          for(Values cardValues: Values.values()){
            this.deck.add(new Card(cardSuits, cardValues));
         }
      }
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public String toString(){
      String cardListOutput ="";
      for (Card card: this.deck){
        cardListOutput+= " " + card.toString();
      }
      return cardListOutput;
    }

    public Card getCard(int i){
        return this.deck.get(i);
    }

    public void removeCard(int i){
        this.deck.remove(i);
    }

    public void addCard(Card addCard) {
        this.deck.add(addCard);
    }

    // Get the size of the deck
    public int deckSize() {
        return this.deck.size();
    }

    // Draws from the deck
    public void draw(Deck comingFrom) {
      this.deck.add(comingFrom.getCard(0));
      comingFrom.removeCard(0);
    
    }

    // This will move cards back into the deck to continue playing
    public void moveAllToDeck(Deck moveTo) {
      int thisDeckSize = this.deck.size();
      for(int i = 0; i < thisDeckSize; i++) {
        moveTo.addCard(this.getCard(i));
      }
      for(int i=0; i < thisDeckSize; i++){
        this.removeCard(0);
      } 
    }
      
      public int cardsValue() {
        int total = 0;
        int aces = 0;


        // Get the value of every card in the deck, protperty of the object card.getVal
        for (Card card : this.deck) {
            Values cardValue = card.getValue();
            if(cardValue!=Values.ACE){
                total+= cardValue.valueValue;
            }else{
                aces++;
            }
            }

        // Ace will be worth 1 if the player total is above 10. Ace will be worth 11 if the player total is 10 or less
        for (int i = 0; i < aces; i++) {
            if (total > 10) {
                total += 1;
            } else {
                total += 11;
            }
        }

        return total;
    }
}