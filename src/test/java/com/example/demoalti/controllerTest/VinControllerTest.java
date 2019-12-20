package com.example.demoalti.controllerTest;

import com.example.demoalti.controller.VinDecoderController;
import com.example.demoalti.model.VINDecoderObj;
import com.example.demoalti.model.VinAttribute;
import com.example.demoalti.service.VinService;
import com.example.demoalti.service.impl.VinServiceImpl;

        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.Mockito;
        import org.springframework.http.HttpHeaders;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class VinControllerTest {

    @InjectMocks
    VinDecoderController vinDecoderController;
    @Mock
    VinServiceImpl vinService;

    @Test
    public void vinDecode() {

        String vin = "5UXWX7C5*BA";
        List<VinAttribute> details = new ArrayList<>();
        details.add(new VinAttribute("Vehicle Type", "MULTIPURPOSE PASSENGER VEHICLE (MPV)"));
        VINDecoderObj vinobj = new VINDecoderObj(details, new ArrayList<>(), new ArrayList<>());

        Mockito.when(vinService.decodeVin(vin)).thenReturn(vinobj);

        assertEquals((vinDecoderController.vinDecode(vin).getBody()), vinobj);
    }


}
