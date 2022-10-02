package cinema.entity;

import cinema.dto.PurchasedSeatDTO;
import cinema.dto.ReturnedTicketDTO;
import cinema.dto.StatsDTO;
import cinema.dto.TicketDTO;

import java.util.*;

public class MovieTheater {
    private int totalRows;
    private int totalColumns;
    private List<Seat> availableSeats;

    private Map<String, Seat> purchasedSeats;

    private int currentIncome = 0;

    private String password = "super_secret";

    public MovieTheater(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                Seat seat = new Seat(i, j);
                this.availableSeats.add(seat);
            }
        }
        this.purchasedSeats = new HashMap<>();
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public PurchasedSeatDTO purchaseSeat(int row, int column) {
        Seat seat = null;
        for (Seat aSeat : this.getAvailableSeats()) {
            if (aSeat.getRow() == row &&
                    aSeat.getColumn() == column) {
                aSeat.setPurchased(true);
                seat = aSeat;
                break;
            }
        }

        if (seat == null) {
            throw new IllegalArgumentException();
        }

        this.currentIncome += seat.getPrice();
        this.availableSeats.remove(seat);

        UUID uuid = UUID.randomUUID();
        this.purchasedSeats.put(uuid.toString(), seat);
        PurchasedSeatDTO response = new PurchasedSeatDTO();
        response.setToken(uuid.toString());
        TicketDTO ticket = new TicketDTO();
        ticket.setColumn(seat.getColumn());
        ticket.setPrice(seat.getPrice());
        ticket.setRow(seat.getRow());
        response.setTicket(ticket);
        return response;
    }

    public boolean isPurchasedToken(String token) {
        return this.purchasedSeats.containsKey(token);
    }

    public ReturnedTicketDTO returnTicket(String token) {
        Seat aSeat = this.purchasedSeats.get(token);
        this.purchasedSeats.remove(token);
        this.availableSeats.add(aSeat);

        this.currentIncome -= aSeat.getPrice();

        TicketDTO ticket = new TicketDTO();
        ticket.setRow(aSeat.getRow());
        ticket.setColumn(aSeat.getColumn());
        ticket.setPrice(aSeat.getPrice());

        ReturnedTicketDTO response = new ReturnedTicketDTO();
        response.setTicket(ticket);
        return response;
    }

    public StatsDTO getStats(String password) {
        if (this.password.equals(password)) {
            StatsDTO stats = new StatsDTO();
            stats.setCurrentIncome(this.currentIncome);
            stats.setNumberAvailableSeats(this.availableSeats.size());
            stats.setNumberPurchasedTickets(this.purchasedSeats.size());
            return stats;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
