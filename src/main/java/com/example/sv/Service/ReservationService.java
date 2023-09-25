package com.example.sv.Service;

import com.example.sv.Model.Product;
import com.example.sv.Model.Reservation;
import com.example.sv.Repository.ProductRepository;
import com.example.sv.Repository.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addProductToReservation(Long reservationId, Long productId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (reservation != null && product != null) {
            reservation.addProduct(product);
            reservationRepository.save(reservation);
        }
    }

    public void removeProductFromReservation(Long reservationId, Long productId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (reservation != null && product != null) {
            reservation.removeProduct(product);
            reservationRepository.save(reservation);
        }
    }
}