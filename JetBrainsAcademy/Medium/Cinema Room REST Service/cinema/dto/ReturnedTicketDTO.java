package cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnedTicketDTO {
    @JsonProperty("returned_ticket")
    private TicketDTO ticket;

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }
}
