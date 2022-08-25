package com.converter.service;

import com.converter.service.entities.ConversionTypeDao;
import com.converter.service.entities.MeasuringUnitDao;
import com.converter.service.repositories.ConversionTypeRepository;
import com.converter.service.repositories.MeasuringUnitRepository;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConverterServiceApplication {

     @Value ("${measuring.default.list}")
     private String measuringUnit;
     
     @Value ("${conversion.state}")
     private String convertTypes;
     
     @Autowired MeasuringUnitRepository MeasuringUnitRepo;
     @Autowired ConversionTypeRepository ConversionTypeRepo;
     
	public static void main(String[] args) {
		SpringApplication.run(ConverterServiceApplication.class, args);
	}
        
        @Bean
        public RestTemplate getRestTemplate() 
        {
            
            return new RestTemplate();
        }

           @Bean
	CommandLineRunner runner()
	{
                return args ->
		{
            if(MeasuringUnitRepo.findAll().size()<1)
            {
                try
                {
                String[] measuringUnitArr = measuringUnit.split(",");
                for(int i=0; i<measuringUnitArr.length; i++)
                {
                    String measureUnitArr = measuringUnitArr[i].trim();
                    
                    String[] measureUnit = measureUnitArr.split(";");
                    
                    MeasuringUnitDao req =new MeasuringUnitDao();
                    req.setMeasureFromName(measureUnit[0]);
                    req.setConverFromType(measureUnit[1]);                     
                    req.setMeasureToName(measureUnit[2]);
                    req.setConverToType(measureUnit[3]);
                    req.setUnitValue(measureUnit[4]);
                    req.setDateCreated(new Timestamp(System.currentTimeMillis()));
                    MeasuringUnitRepo.save(req);
                }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    Logger.getLogger(ConverterServiceApplication.class.getName()).log(Level.SEVERE, (String)null, ex);
                }
            }
            
            if(ConversionTypeRepo.findAll().size()<1)
            {
                try
                {
                String[] convertTypesArr = convertTypes.split(",");
                for(int i=0; i<convertTypesArr.length; i++)
                {
                    ConversionTypeDao convertData = new ConversionTypeDao();
                    convertData.setConversionType(convertTypesArr[i].trim());
                    ConversionTypeRepo.save(convertData);
                }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    Logger.getLogger(ConverterServiceApplication.class.getName()).log(Level.SEVERE, (String)null, ex);
                }
            }			
		};
	}
}
