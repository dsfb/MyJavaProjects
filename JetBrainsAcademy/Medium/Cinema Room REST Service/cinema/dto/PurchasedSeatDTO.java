package cinema.dto;

public class PurchasedSeatDTO {
    private String token;

    private TicketDTO ticket;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }
}
