package com.example.converter.controller;

import com.example.converter.model.Conversion;
import com.example.converter.model.User;
import com.example.converter.repository.UserRepository;
import com.example.converter.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ConversionController {

    @Autowired
    private ConversionService service;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/convert")
    public Conversion convert(@RequestParam double amount,
                              @RequestParam String from,
                              @RequestParam String to,
                              @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepo.findByUsername(userDetails.getUsername());
        return service.convert(from, to, amount, user);
    }

    @GetMapping("/history")
public Page<Conversion> getHistory(
    @AuthenticationPrincipal UserDetails userDetails,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size)
     {
    User user = userRepo.findByUsername(userDetails.getUsername());
    return service.getHistory(user, page, size);
}


}
