/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter.service.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CE155742
 */
public class LoggerUtil {
    
    public void LogDisplay(String req, Level logType)
    {
        System.out.println(req);
        Logger.getLogger(LoggerUtil.class.getName()).log(logType, (String)null, req);
    }
}
