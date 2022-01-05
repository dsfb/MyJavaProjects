package cinema.controller;

import cinema.dto.SeatDTO;
import cinema.entity.MovieTheater;
import cinema.entity.Seat;
import cinema.exception.ExceptionJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Seat seat = null;
        for (Seat aSeat : movieTheater.getAvailableSeats()) {
            if (aSeat.getRow() == seatDTO.getRow() &&
                aSeat.getColumn() == seatDTO.getColumn()) {
                aSeat.setPurchased(true);
                seat = aSeat;
                break;
            }
        }

        return new ResponseEntity<>(seat, HttpStatus.OK);
    }
}
