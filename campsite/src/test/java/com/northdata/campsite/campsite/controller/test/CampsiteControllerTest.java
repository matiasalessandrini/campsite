package com.northdata.campsite.campsite.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.truenorth.campsite.CampsiteApplication;
import com.truenorth.campsite.campsite.model.CampsiteStatus;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CampsiteApplication.class })
@AutoConfigureMockMvc
public class CampsiteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testAvailabilityDefault() throws Exception {

		mockMvc.perform(get("/availability")
				.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(CampsiteStatus.AVAILABLE.getStatus()));
	}
	

	@Test
	public void testAvailabilityByDates() throws Exception {
		
		String dateFrom = LocalDate.now().plusDays(2).toString();
		String dateTo =  LocalDate.now().plusDays(4).toString();
	
		mockMvc.perform(get("/availability?dateFrom=" + dateFrom + "&dateTo=" + dateTo)
				.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(CampsiteStatus.AVAILABLE.getStatus()));
	}

}
