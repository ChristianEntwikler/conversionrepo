/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter.service.entities;

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
@Table(name="conversiontypetbl")
public class ConversionTypeDao {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;
    
    @Column(unique=true, name="conversiontype")
    private String conversionType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }
}
