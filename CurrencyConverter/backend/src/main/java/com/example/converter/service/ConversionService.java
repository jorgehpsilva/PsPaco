package com.example.converter.service;

import com.example.converter.model.Conversion;
import com.example.converter.model.ExchangeRate;
import com.example.converter.repository.ConversionRepository;
import com.example.converter.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.converter.dto.ExchangeRateResponse;
import com.example.converter.model.User;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;




@Service
public class ConversionService {

    private final String API_KEY = "0OUgcaArjmAus2Wh8gnOH2K82zm1iosZ"; 
    private final String API_URL = "https://api.apilayer.com/exchangerates_data/latest";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConversionRepository conversionRepository;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public Conversion convert(String from, String to, double amount, User user) {
        double rate = getExchangeRate(from, to);

        double result = amount * rate;

        Conversion conversion = new Conversion();
        conversion.setFromCurrency(from);
        conversion.setToCurrency(to);
        conversion.setExchangeRate(rate);
        conversion.setAmount(amount);
        conversion.setResult(result); 
        conversion.setDate(LocalDateTime.now());
        conversion.setUser(user);

        
        return conversionRepository.save(conversion);
    }

    public Page<Conversion> getHistory(User user, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
    return conversionRepository.findByUser(user, pageable);
    }


    private double getExchangeRate(String from, String to) {
        
        ExchangeRate cachedRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(from, to);
        if (cachedRate != null) {
            return cachedRate.getRate();
        }

        
        String url = API_URL + "?base=" + from + "&symbols=" + to;

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        
        ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ExchangeRateResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            ExchangeRateResponse body = response.getBody();
            if (body != null) {
                Map<String, Double> rates = body.getRates();
                Double rate = rates.get(to);

                if (rate != null) {
                    ExchangeRate newRate = new ExchangeRate();
                    newRate.setFromCurrency(from);
                    newRate.setToCurrency(to);
                    newRate.setRate(rate);
                    exchangeRateRepository.save(newRate);
                    return rate;
                }
            }
        }

        throw new RuntimeException("Não foi possível obter a taxa de câmbio.");
    }
}
