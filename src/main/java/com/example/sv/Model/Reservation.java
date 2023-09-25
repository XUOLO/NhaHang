package com.example.sv.Model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_number")
    private String reservationNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "reservation_product",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    // Constructors, getters, and setters
    // ...

    public void addProduct(Product product) {
        products.add(product);
        product.getReservations().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getReservations().remove(this);
    }
}