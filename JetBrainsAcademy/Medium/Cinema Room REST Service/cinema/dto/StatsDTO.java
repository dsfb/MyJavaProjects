package cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsDTO {
    @JsonProperty("current_income")
    private int currentIncome;

    @JsonProperty("number_of_available_seats")
    private int numberAvailableSeats;

    @JsonProperty("number_of_purchased_tickets")
    private int numberPurchasedTickets;

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public int getNumberAvailableSeats() {
        return numberAvailableSeats;
    }

    public void setNumberAvailableSeats(int numberAvailableSeats) {
        this.numberAvailableSeats = numberAvailableSeats;
    }

    public int getNumberPurchasedTickets() {
        return numberPurchasedTickets;
    }

    public void setNumberPurchasedTickets(int numberPurchasedTickets) {
        this.numberPurchasedTickets = numberPurchasedTickets;
    }
}
