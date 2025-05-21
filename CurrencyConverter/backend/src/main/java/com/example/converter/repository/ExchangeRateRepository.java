package com.example.converter.repository;

import com.example.converter.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
