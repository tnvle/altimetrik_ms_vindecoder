package com.example.demoalti.service;

import com.example.demoalti.model.VINDecoderObj;
import org.springframework.http.ResponseEntity;

public interface VinService {

    VINDecoderObj decodeVin(String vin);
}
