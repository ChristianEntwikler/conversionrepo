/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter.service.repositories;

import com.converter.service.entities.ConversionTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author CE155742
 */
public interface ConversionTypeRepository extends JpaRepository<ConversionTypeDao, Long>{
    
}
