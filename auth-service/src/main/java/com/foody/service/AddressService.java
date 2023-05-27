package com.foody.service;

import com.foody.repository.IAddressRepository;
import com.foody.repository.entity.Address;
import com.foody.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends ServiceManager<Address,Long> {
    private final IAddressRepository addressRepository;

    public AddressService(IAddressRepository addressRepository){
        super(addressRepository);
        this.addressRepository = addressRepository;
    }

}
