package com.example.converter.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;


@Data
@Entity
public class Conversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String fromCurrency;
    private String toCurrency;
    private double exchangeRate;
    private double result;
    private LocalDateTime date;

    @ManyToOne
    private User user;

    
}
