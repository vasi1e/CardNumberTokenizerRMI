package Classes;

public class PairOfCardNumberAndToken {
    private String cardNumber;
    private String token;

    public PairOfCardNumberAndToken(String _cardNumber, String _token) {
        this.cardNumber = _cardNumber;
        this.token = _token;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getToken() {
        return token;
    }
}
