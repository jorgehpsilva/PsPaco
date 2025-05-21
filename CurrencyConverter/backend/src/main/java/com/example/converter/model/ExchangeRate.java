package com.example.converter.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCurrency;
    private String toCurrency;
    private double rate;

    
}
