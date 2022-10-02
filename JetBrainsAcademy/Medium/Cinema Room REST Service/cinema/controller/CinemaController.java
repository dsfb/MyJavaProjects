package cinema.controller;

import cinema.dto.*;
import cinema.entity.MovieTheater;
import cinema.entity.Seat;
import cinema.exception.ExceptionJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CinemaController {
    private MovieTheater movieTheater = new MovieTheater(9, 9);

    @GetMapping("/seats")
    public MovieTheater getSeats() {
        return this.movieTheater;
    }

    private boolean isValidSeat(SeatDTO seatDTO) {
        if (seatDTO.getRow() < 0 || seatDTO.getRow() > 9) {
            return false;
        } else if (seatDTO.getColumn() < 0 || seatDTO.getColumn() > 9) {
            return false;
        }

        return true;
    }

    private boolean isPurchasedSeat(SeatDTO seatDTO) {
        int index = (seatDTO.getRow() - 1) * 9 + seatDTO.getColumn() - 1;
        return movieTheater.getAvailableSeats().get(index).isPurchased();
    }

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<Object> purchaseSeat(@RequestBody SeatDTO seatDTO) {
        if (!isValidSeat(seatDTO)) {
            return new ResponseEntity(new ExceptionJson("The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } else if (isPurchasedSeat(seatDTO)) {
            return new ResponseEntity<>(new ExceptionJson("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }

        try {
            PurchasedSeatDTO response = this.movieTheater.purchaseSeat(seatDTO.getRow(),
                    seatDTO.getColumn());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExceptionJson("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    @ResponseBody
    public ResponseEntity<Object> returnSeat(@RequestBody TokenDTO token) {
        if (this.movieTheater.isPurchasedToken(token.getToken())) {
            ReturnedTicketDTO returnTicket = this.movieTheater.returnTicket(token.getToken());

            return new ResponseEntity<>(returnTicket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ExceptionJson("Wrong token!"),  HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    @ResponseBody
    public ResponseEntity<Object> getStats(@RequestParam(value = "password", required = false) String password) {
        try {
            if (password == null) {
                throw new RuntimeException();
            } else {
                StatsDTO stats = this.movieTheater.getStats(password);
                return new ResponseEntity<>(stats, HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExceptionJson("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }
}
