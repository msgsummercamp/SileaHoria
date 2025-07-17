package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testReturnAllUsersSuccess() throws Exception {
		this.mockMvc.perform(get("/users")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"));
	}

	@Test
	void testReturn404WhenUserIdNotFound() throws Exception {
		this.mockMvc.perform(get("/users").param("id", "999"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}
