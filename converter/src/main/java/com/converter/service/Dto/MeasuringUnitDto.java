/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter.service.Dto;

/**
 *
 * @author CE155742
 */
public class MeasuringUnitDto {
     private String measureFromName;
    private String converFromType;
    private String measureToName;
    private String converToType;
    private String unitValue;

    public String getMeasureFromName() {
        return measureFromName;
    }

    public void setMeasureFromName(String measureFromName) {
        this.measureFromName = measureFromName;
    }

    public String getConverFromType() {
        return converFromType;
    }

    public void setConverFromType(String converFromType) {
        this.converFromType = converFromType;
    }

    public String getMeasureToName() {
        return measureToName;
    }

    public void setMeasureToName(String measureToName) {
        this.measureToName = measureToName;
    }

    public String getConverToType() {
        return converToType;
    }

    public void setConverToType(String converToType) {
        this.converToType = converToType;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }
    
}
