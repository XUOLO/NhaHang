package com.example.sv.Controller.User;

import com.example.sv.Model.Product;

import com.example.sv.Model.Reservation;
import com.example.sv.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationUserController {


    @Autowired
    private ReservationService reservationService;

    @PostMapping("/user/{reservationId}/addProduct/{productId}")
    public void addProductToReservation(@PathVariable Long reservationId, @PathVariable Long productId) {
        reservationService.addProductToReservation(reservationId, productId);
    }

    @PostMapping("/user/{reservationId}/removeProduct/{productId}")
    public void removeProductFromReservation(@PathVariable Long reservationId, @PathVariable Long productId) {
        reservationService.removeProductFromReservation(reservationId, productId);
    }

}
