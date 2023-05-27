package com.foody.controller;

import com.foody.repository.entity.Address;
import com.foody.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.foody.constants.ApiUrls.*;

import java.util.List;

@RestController
@RequestMapping(ADDRESSS)
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Address>> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }
}
