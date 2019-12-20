package com.example.demoalti.service.impl;

import com.example.demoalti.controller.VinDecoderController;
import com.example.demoalti.model.Make;
import com.example.demoalti.model.Model;
import com.example.demoalti.model.VINDecoderObj;
import com.example.demoalti.model.VinAttribute;
import com.example.demoalti.service.VinService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VinServiceImpl implements VinService {

    @Value("https://vpic.nhtsa.dot.gov/api/vehicles/DecodeVin/%s?format=json")
    private String vinDecodeAPI;

    @Value("https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/%s?format=json")
    private String makeForVehicleTypeAPI;

    @Value("https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMake/%s?format=json")
    private String modelForMakeAPI;

    final private String VEHICLE_TYPE = "Vehicle Type";
    final private String MAKE = "Make";

    public VINDecoderObj decodeVin(String vin){


        try{
            final String decodeVinURL = String.format(vinDecodeAPI, vin);
            RestTemplate vinRestTemple = new RestTemplate();

            //step 1: vin decode
            Map result = vinRestTemple.getForObject(decodeVinURL, LinkedHashMap.class);

            ArrayList<LinkedHashMap> attrList = (ArrayList<LinkedHashMap>)result.get("Results");

            Map<String, String> vinAtrMap = attrList.stream().map(attr -> new VinAttribute(attr.get("Variable") != null?attr.get("Variable").toString(): "", attr.get("Value") != null? attr.get("Value").toString(): "")).collect(Collectors.toMap(e -> e.getName(), e -> e.getValue()));
            List<VinAttribute> vinAtrList = attrList.stream().map(attr -> new VinAttribute(attr.get("Variable") != null?attr.get("Variable").toString(): "", attr.get("Value") != null? attr.get("Value").toString(): "")).collect(Collectors.toList());

//        for(int i = 0; i < vinAtrList.size(); i++) {
//            if (vinAtrList.get(i).getName() == null)
//                System.out.print("AAA");
//            if (vinAtrList.get(i).getValue() == null)
//                System.out.print("AAA");
//        }

            //step2: get all MAKES from Vehicle Type
            String vehicleType = vinAtrMap.get(VEHICLE_TYPE);
            List<Make> makeList = getMakesByVehicleType(vehicleType);

            //step 3: get all MODELs from Make
            String make = vinAtrMap.get(MAKE);
            List<Model> modelList = getModelsByMake(make);

            VINDecoderObj vinobj = new VINDecoderObj(vinAtrList, makeList, modelList);
            return vinobj;
        }
        catch(Exception e){
            return null;
        }
    }

    private List<Make> getMakesByVehicleType(String vehicleType){

        final String makeByeVehicelTypeURL = String.format(makeForVehicleTypeAPI, vehicleType);
        RestTemplate vinRestTemple = new RestTemplate();

        Map result = vinRestTemple.getForObject(makeByeVehicelTypeURL, LinkedHashMap.class);
        ArrayList<LinkedHashMap> makeList = (ArrayList<LinkedHashMap>)result.get("Results");

        List<Make> makes  = makeList.stream().map(attr -> new Make(attr.get("MakeId") != null?attr.get("MakeId").toString(): "", attr.get("MakeName") != null? attr.get("MakeName").toString(): "")).collect(Collectors.toList());
        return makes;
    }

    private List<Model> getModelsByMake(String make){

        final String modelsForMakeURL = String.format(modelForMakeAPI, make);
        RestTemplate vinRestTemple = new RestTemplate();

        Map result = vinRestTemple.getForObject(modelsForMakeURL, LinkedHashMap.class);
        ArrayList<LinkedHashMap> modelList = (ArrayList<LinkedHashMap>)result.get("Results");

        List<Model> models  = modelList.stream().map(attr -> new Model(attr.get("Model_ID") != null?attr.get("Model_ID").toString(): "", attr.get("Model_Name") != null? attr.get("Model_Name").toString(): "")).collect(Collectors.toList());
        return models;
    }
}
