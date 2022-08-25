/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter.service.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author CE155742
 */

@Entity
@Table(name="measuringunittbl")
public class MeasuringUnitDao {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;
    
    @Column(name="measurefromname")
    private String measureFromName;
    
    @Column(name="convertfromype")
    private String converFromType;
    
    @Column(name="measuretoame")
    private String measureToName;
    
    @Column(name="converttoype")
    private String converToType;
    
    @Column(name="unitvalue")
    private String unitValue;
    
    @Column(name="datecreated")
    private Timestamp dateCreated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

   
}
