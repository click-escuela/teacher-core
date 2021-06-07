package click.escuela.teacher.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.enumerator.ActivityType;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.rest.ActivityController;
import click.escuela.teacher.core.rest.handler.Handler;
import click.escuela.teacher.core.service.impl.ActivityServiceImpl;

@EnableWebMvc
@RunWith(MockitoJUnitRunner.class)
public class ActivityControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ActivityController activityController;

	@Mock
	private ActivityServiceImpl activityService;

	private ObjectMapper mapper;
	private ActivityApi activityApi;
	private static String EMPTY = "";
	private String schoolId;
	private String courseId;

	@Before
	public void setup() throws TransactionException {
		mockMvc = MockMvcBuilders.standaloneSetup(activityController).setControllerAdvice(new Handler()).build();
		mapper = new ObjectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ReflectionTestUtils.setField(activityController, "activityService", activityService);

		schoolId = "1234";
		courseId = UUID.randomUUID().toString();
		activityApi = ActivityApi.builder().name("Historia de las catatumbas").subject("Historia")
				.type(ActivityType.HOMEWORK.toString()).schoolId(Integer.valueOf(schoolId))
				.courseId(courseId.toString()).dueDate(LocalDate.now()).description("Resolver todos los puntos")
				.build();

		doNothing().when(activityService).create(Mockito.any(), Mockito.any());
	}

	@Test
	public void whenCreateIsOk() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().is2xxSuccessful()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains(ActivityMessage.CREATE_OK.name());
	}

	@Test
	public void whenCreateButNameEmpty() throws JsonProcessingException, Exception {
		activityApi.setName(EMPTY);
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("Name cannot be empty");
	}

	@Test
	public void whenCreateButSubjectEmpty() throws JsonProcessingException, Exception {
		activityApi.setSubject(EMPTY);
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("Subject cannot be empty");
	}

	@Test
	public void whenCreateButDescriptionEmpty() throws JsonProcessingException, Exception {
		activityApi.setDescription(EMPTY);
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("Description cannot be empty");
	}

	@Test
	public void whenCreateButCourseEmpty() throws JsonProcessingException, Exception {
		activityApi.setCourseId(EMPTY);
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("Course Id cannot be empty");
	}

	@Test
	public void whenCreateButTypeEmpty() throws JsonProcessingException, Exception {
		activityApi.setType(EMPTY);
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("Type cannot be empty");
	}

	@Test
	public void whenCreateButSchoolNull() throws JsonProcessingException, Exception {
		activityApi.setSchoolId(null);
		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("School Id cannot be null");
	}

	@Test
	public void whenCreateErrorService() throws JsonProcessingException, Exception {
		doThrow(new TransactionException(ActivityMessage.CREATE_ERROR.getCode(),
				ActivityMessage.CREATE_ERROR.getDescription())).when(activityService).create(Mockito.anyString(),
						Mockito.any());

		MvcResult result = mockMvc.perform(post("/school/{schoolId}/activity", schoolId)
				.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andExpect(status().isBadRequest()).andReturn();
		String response = result.getResponse().getContentAsString();
		assertThat(response).contains("");
	}

	private String toJson(final Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}
}
