package com.converter.service;

import com.converter.service.entities.ConversionTypeDao;
import com.converter.service.entities.MeasuringUnitDao;
import com.converter.service.repositories.ConversionTypeRepository;
import com.converter.service.repositories.MeasuringUnitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(ConverterServiceController.class)
class ConverterServiceApplicationTests {

//	@Test
//	void contextLoads() {
//	}
    
    
    @Value ("${measuring.default.list}")
     private String measuringUnit;
     
     @Value ("${conversion.state}")
     private String convertTypes;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper mapper;
    
    
    @MockBean MeasuringUnitRepository MeasuringUnitRepo;
    @MockBean ConversionTypeRepository ConversionTypeRepo;
    
    
@Test
public void getListConversionTypes() {
        
          if(ConversionTypeRepo.findAll().size()<1)
            {
                String[] convertTypesArr = convertTypes.split(",");
                for(int i=0; i<convertTypesArr.length; i++)
                {
                    ConversionTypeDao convertData = new ConversionTypeDao();
                    convertData.setConversionType(convertTypesArr[i].trim());
                    ConversionTypeRepo.save(convertData);
                }
            }
        
       List<ConversionTypeDao> records = ConversionTypeRepo.findAll();
    Mockito.when(ConversionTypeRepo.findAll()).thenReturn(records);

}
         
@Test
public void getListMeasurementTypes() {

        if(MeasuringUnitRepo.findAll().size()<1)
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
        
        List<MeasuringUnitDao> records = MeasuringUnitRepo.findAll();
        Mockito.when(MeasuringUnitRepo.findAll()).thenReturn(records);

}
         
@Test
public void addMeasurementType() {
    try
    {
            MeasuringUnitDao req =new MeasuringUnitDao();
                req.setConverFromType("Metric");
                req.setConverToType("Imperial");
                req.setMeasureFromName("Grams");
                req.setMeasureToName("Ounces");
                req.setUnitValue("0.035");
                req.setDateCreated(new Timestamp(System.currentTimeMillis()));
                MeasuringUnitRepo.save(req);
            List<MeasuringUnitDao> records = new ArrayList<MeasuringUnitDao>();
            records.add(req);
            
    Mockito.when(MeasuringUnitRepo.findAll()).thenReturn(records);
    
    List<MeasuringUnitDao> val = MeasuringUnitRepo.findByMeasureFromName("Grams");
    Assert.assertEquals("Ounces", val.stream().filter(p->p.getMeasureFromName().equals("Grams")).findFirst().get().getMeasureToName());
    Assert.assertEquals("Imperial", val.stream().filter(p->p.getMeasureFromName().equals("Grams")).findFirst().get().getConverToType());

    Mockito.verify(MeasuringUnitRepo).findByMeasureFromName("Grams");
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    

}

@Test
public void convert() {
    try
    {
            MeasuringUnitDao req =new MeasuringUnitDao();
                req.setConverFromType("Metric");
                req.setConverToType("Imperial");
                req.setMeasureFromName("Grams");
                req.setMeasureToName("Ounces");
                req.setUnitValue("2");
                req.setDateCreated(new Timestamp(System.currentTimeMillis()));
            List<MeasuringUnitDao> records = new ArrayList<MeasuringUnitDao>();
            records.add(req);
            

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/measurement/convert")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(req));

    mockMvc.perform(mockRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.statusCode", is("05")));
    
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    

}

}
