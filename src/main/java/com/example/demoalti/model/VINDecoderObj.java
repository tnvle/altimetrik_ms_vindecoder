package com.example.demoalti.model;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VINDecoderObj {

    private List<VinAttribute> details;
    private List<Make> makes;
    private List<Model> models;
}
