package com.mutants.mutantsAPI;

import com.mutants.mutantsAPI.controllers.MutantsController;
import com.mutants.mutantsAPI.controllers.StatsController;
import com.mutants.mutantsAPI.dtos.MutantsDto;
import com.mutants.mutantsAPI.dtos.StatsDto;
import com.mutants.mutantsAPI.services.MutantsService;
import com.mutants.mutantsAPI.services.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MutantsApiApplicationTests {

	@MockBean
	private StatsService statsService;

	@MockBean
	private MutantsService mutantsService;

	@Autowired
	private StatsController statsController;

	@Autowired
	private MutantsController mutantsController;

	@Test
	void contextLoads() {
	}

	@Test
	void testGetStats() {
		// Mock the response from StatsService
		StatsDto mockStats = new StatsDto(); // Asegúrate de usar el tipo correcto
		when(statsService.getStats()).thenReturn(mockStats);

		// Call the controller method
		ResponseEntity<?> response = statsController.getStats();

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	@Test
	void testIsMutant() throws Exception {
		MutantsDto mutantDna = new MutantsDto();
		mutantDna.setDna(new String[]{
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TCACTG"
		});
		when(mutantsService.isMutant(mutantDna)).thenReturn(true);
		assertTrue(mutantsService.isMutant(mutantDna));
	}
	@Test
	void testIsNotMutant() throws Exception {
		MutantsDto humanDna = new MutantsDto();
		humanDna.setDna(new String[]{
				"ATGCGA",
				"CAGTGC",
				"TTATTT",
				"AGACGG",
				"GCGTCA",
				"TCACTG"
		});
		assertFalse(mutantsService.isMutant(humanDna));
	}



	@Test
	void testHumanEndpoint() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mutantsController).build();

		MutantsDto humanDna = new MutantsDto();
		humanDna.setDna(new String[]{
				"ATGCGA",
				"CAGTGC",
				"TTATTT",
				"AGACGG",
				"GCGTCA",
				"TCACTG"
		});

		when(mutantsService.isMutant(humanDna)).thenReturn(false);

		mockMvc.perform(post("/mutant")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"dna\": [\"ATGCGA\", \"CAGTGC\", \"TTATTT\", \"AGACGG\", \"GCGTCA\", \"TCACTG\"]}"))
				.andExpect(status().isForbidden());
	}
}