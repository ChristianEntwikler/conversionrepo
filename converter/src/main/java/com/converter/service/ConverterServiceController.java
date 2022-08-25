/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.converter.service;

import com.converter.service.Dto.ConversionResponseDto;
import com.converter.service.Dto.MeasuringUnitDto;
import com.converter.service.Dto.ResponseDto;
import com.converter.service.entities.ConversionTypeDao;
import com.converter.service.entities.MeasuringUnitDao;
import com.converter.service.repositories.ConversionTypeRepository;
import com.converter.service.repositories.MeasuringUnitRepository;
import static com.converter.service.utils.ConverterUtil.toJson;
import com.converter.service.utils.LoggerUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author CE155742
 */
@RestController
@RequestMapping("/api")
public class ConverterServiceController {
    
    @Autowired MeasuringUnitRepository MeasuringUnitRepo;
    @Autowired ConversionTypeRepository ConversionTypeRepo;
    
    @RequestMapping(value ="measurementunit/add",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ResponseDto> AddMeasurement(@RequestBody MeasuringUnitDto request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ResponseDto resp = new ResponseDto();
            
            try
            {
            if((request.getConverFromType()!=null && !request.getConverFromType().isEmpty()) 
                    && (request.getConverToType()!=null && !request.getConverToType().isEmpty())
                    && (request.getMeasureFromName()!=null && !request.getMeasureFromName().isEmpty())
                    && (request.getMeasureToName()!=null && !request.getMeasureToName().isEmpty())
                    && (request.getUnitValue()!=null && !request.getUnitValue().isEmpty()))
            {
                //ADDING NEW MEASURING UNIT START
                MeasuringUnitDao req =new MeasuringUnitDao();
                req.setConverFromType(request.getConverFromType());
                req.setConverToType(request.getConverToType());
                req.setMeasureFromName(request.getMeasureFromName());
                req.setMeasureToName(request.getMeasureToName());
                req.setUnitValue(request.getUnitValue());
                req.setDateCreated(new Timestamp(System.currentTimeMillis()));
                MeasuringUnitRepo.save(req);
                
                    resp.setStatusCode("00");
                    resp.setStatusMessage("Measurement Unit Added Successfully");
                    return ResponseEntity.ok().body(resp);
                
                //ADDING NEW MEASURING UNIT STOP

            }
            else
            {
                resp.setStatusCode("01");
                resp.setStatusMessage("All values required");
                return ResponseEntity.ok().body(resp);
            }
            
            }
            catch(DataIntegrityViolationException dup)
		{
                        new LoggerUtil().LogDisplay("Error: " + dup.getStackTrace().toString(), Level.SEVERE);
			ResponseDto reply = new ResponseDto();
			reply.setStatusCode("94");
			reply.setStatusMessage("Data flagged as duplicate");			
			return new ResponseEntity<ResponseDto>(reply, HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
                        resp.setStatusCode("96");
                        resp.setStatusMessage("System failure");
			return new ResponseEntity<ResponseDto>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
            
            
        }
    
    @RequestMapping(value ="measurement/convert",produces = "application/json",method=RequestMethod.POST)
    public ResponseEntity<ConversionResponseDto> convert(@RequestBody MeasuringUnitDto request, @RequestHeader HttpHeaders headers)
        {
            new LoggerUtil().LogDisplay("request: " + toJson(request), Level.INFO);
            ConversionResponseDto resp = new ConversionResponseDto();
            
            try
            {
            if((request.getConverFromType()!=null && !request.getConverFromType().isEmpty()) 
                    && (request.getConverToType()!=null && !request.getConverToType().isEmpty())
                    && (request.getMeasureFromName()!=null && !request.getMeasureFromName().isEmpty())
                    && (request.getMeasureToName()!=null && !request.getMeasureToName().isEmpty())
                    && (request.getUnitValue()!=null && !request.getUnitValue().isEmpty()))
            {
                //CONVERSION START
                    List<MeasuringUnitDao> convertFrom = MeasuringUnitRepo.findByMeasureFromName(request.getMeasureFromName());
                    if(convertFrom.size() > 0)
                    {
                        if(convertFrom.stream().filter(p->p.getMeasureToName().equalsIgnoreCase(request.getMeasureToName())).findFirst().isPresent())
                        {
                    MeasuringUnitDao converterFrom = convertFrom.stream().filter(p->p.getMeasureToName().equalsIgnoreCase(request.getMeasureToName())).findFirst().get();
                    Double rslt = 0.0;
                    if(converterFrom.getConverFromType().equalsIgnoreCase("Celsius") || converterFrom.getConverFromType().equalsIgnoreCase("Fahrenheit"))
                    {
                        rslt = Double.parseDouble(converterFrom.getUnitValue().replace("#VALUE#", request.getUnitValue()));
                    }
                    else
                    {
                        rslt = Double.parseDouble(request.getUnitValue()) * Double.parseDouble(converterFrom.getUnitValue());
                    }
                    
                    resp.setStatusCode("00");
                    resp.setStatusMessage("Successful");
                    resp.setResult(String.valueOf(rslt));
                    return ResponseEntity.ok().body(resp);
                        }
                        else
                        {
                            resp.setStatusCode("02");
                            resp.setStatusMessage("Invalid combination");
                            resp.setResult(String.valueOf(""));
                            return ResponseEntity.ok().body(resp); 
                        }
                    }
                    else
                        {
                            resp.setStatusCode("05");
                            resp.setStatusMessage("Convert From not found");
                            resp.setResult(String.valueOf(""));
                            return ResponseEntity.ok().body(resp); 
                        }
                
                //CONVERSION STOP

            }
            else
            {
                resp.setStatusCode("01");
                resp.setStatusMessage("All values required");
                return ResponseEntity.ok().body(resp);
            }
            
            }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
                        resp.setStatusCode("96");
                        resp.setStatusMessage("System failure");
			return new ResponseEntity<ConversionResponseDto>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
            
            
        }
    
    @RequestMapping(value ="/list/measurement",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<MeasuringUnitDao>> fetchAllMeasurementTypes()
        {    
             List<MeasuringUnitDao> resp = new ArrayList<MeasuringUnitDao>();
             try
             {
             //FETCH ALL MEASUREMENT TYPES
             resp = MeasuringUnitRepo.findAll();
             //FETCH ALL MEASUREMENT TYPES
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<MeasuringUnitDao>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
    @RequestMapping(value ="/list/conversionTypes",produces = "application/json",method=RequestMethod.GET)
    public ResponseEntity<List<ConversionTypeDao>> fetchAllConversionTypes()
        {    
             List<ConversionTypeDao> resp = new ArrayList<ConversionTypeDao>();
             try
             {
             //FETCH ALL MEASUREMENT TYPES
             resp = ConversionTypeRepo.findAll();
             //FETCH ALL MEASUREMENT TYPES
             }
		catch(Exception e)
		{
			e.printStackTrace();
                        new LoggerUtil().LogDisplay("Error: " + e.getStackTrace().toString(), Level.SEVERE);
			return new ResponseEntity<List<ConversionTypeDao>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
            return ResponseEntity.ok().body(resp);
        }
    
}
