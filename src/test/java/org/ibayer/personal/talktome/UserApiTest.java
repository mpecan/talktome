package org.ibayer.personal.talktome;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.ibayer.personal.talktome.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class UserApiTest {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	User user;

	@Before
	public void before() {
		user = new User();
		user.setName("test");
		user.setPassword(new Character[] { 'x', '3', 'd' });
	}

	@Test
	public void postUser() throws Exception {

		ResultActions result = mockMvc
				.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user)));

		result.andExpect(jsonPath("$.name").value(user.getName()));
		result.andExpect(jsonPath("$._links.self.href").exists());
	}

	@Test
	public void searchUser() throws Exception {
		ResultActions result = mockMvc
				.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user)));
		result = mockMvc.perform(
				MockMvcRequestBuilders.get("/users?name=" + user.getName()).contentType(APPLICATION_JSON_UTF8));
		result.andExpect(jsonPath("$._embedded").exists());
		result.andExpect(jsonPath("$._embedded.users[0]").exists());
		result.andExpect(jsonPath("$._embedded.users[0].name").value(user.getName()));
	}

	@Test
	public void get() throws Exception {
		ResultActions result = mockMvc
				.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user)));
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		user = mapper.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
		result = mockMvc.perform(
				MockMvcRequestBuilders.get("/users/" + user.getUserId()).contentType(APPLICATION_JSON_UTF8));
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.userId").exists());
		result.andExpect(jsonPath("$.name").value(user.getName()));
	}

}
