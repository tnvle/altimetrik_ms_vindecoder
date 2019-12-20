package com.example.demoalti.controller;

import com.example.demoalti.model.VINDecoderObj;
import com.example.demoalti.service.VinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class VinDecoderController {

    @Autowired
    private VinService vinService;

    @GetMapping("/vindecoder/{vin}")
    public ResponseEntity<?> vinDecode(@PathVariable(name="vin") String vin){

        VINDecoderObj vinobj = vinService.decodeVin(vin);
        if(vinobj != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(vinobj);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
