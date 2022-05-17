
public enum Suits {
    DIAMOND("Diamond"), 
    HEART("Heart"), 
    SPADE("Spade"), 
    CLUB("Club");

    String suitName;

    Suits(String suitName){
        this.suitName= suitName;
    }
    public String toString(){
        return suitName;
    }
}