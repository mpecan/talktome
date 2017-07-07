package org.ibayer.personal.talktome.swagger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class SwaggerConfigTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnApiDocs() throws Exception {
		ResultActions result = mockMvc.perform(get("http://localhost:8080/v2/api-docs"));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.paths").exists());
		result.andExpect(jsonPath("$.tags").exists());
	}
}
