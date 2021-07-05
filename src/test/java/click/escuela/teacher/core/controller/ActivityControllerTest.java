package click.escuela.teacher.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.enumerator.ActivityType;
import click.escuela.teacher.core.enumerator.ActivityValidation;
import click.escuela.teacher.core.exception.ActivityException;
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
	private String id;
	private String schoolId;
	private String courseId;
	private final static String URL = "/school/{schoolId}/activity";


	@Before
	public void setup() throws TransactionException {
		mockMvc = MockMvcBuilders.standaloneSetup(activityController).setControllerAdvice(new Handler()).build();
		mapper = new ObjectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ReflectionTestUtils.setField(activityController, "activityService", activityService);

		schoolId = "1234";
		id = UUID.randomUUID().toString();
		courseId = UUID.randomUUID().toString();
		activityApi = ActivityApi.builder().name("Historia de las catatumbas").subject("Historia")
				.type(ActivityType.HOMEWORK.toString()).schoolId(Integer.valueOf(schoolId))
				.courseId(courseId.toString()).dueDate(LocalDate.now()).description("Resolver todos los puntos")
				.build();

	}

	@Test
	public void whenCreateIsOk() throws JsonProcessingException, Exception {
		assertThat(resultActivityApi(post(URL, schoolId))).contains(ActivityMessage.CREATE_OK.name());

	}

	@Test
	public void whenCreateButNameEmpty() throws JsonProcessingException, Exception {
		activityApi.setName(StringUtils.EMPTY);
		assertThat(resultActivityApi(post(URL, schoolId))).contains(ActivityValidation.NAME_EMPTY.getDescription());

	}

	@Test
	public void whenCreateButSubjectEmpty() throws JsonProcessingException, Exception {
		activityApi.setSubject(StringUtils.EMPTY);
		assertThat(resultActivityApi(post(URL, schoolId))).contains(ActivityValidation.SUBJECT_EMPTY.getDescription());

	}

	@Test
	public void whenCreateButDescriptionEmpty() throws JsonProcessingException, Exception {
		activityApi.setDescription(StringUtils.EMPTY);
		assertThat(resultActivityApi(post(URL, schoolId)))
				.contains(ActivityValidation.DESCRIPTION_EMPTY.getDescription());

	}

	@Test
	public void whenCreateButCourseEmpty() throws JsonProcessingException, Exception {
		activityApi.setCourseId(StringUtils.EMPTY);
		assertThat(resultActivityApi(post(URL, schoolId)))
				.contains(ActivityValidation.COURSE_ID_EMPTY.getDescription());

	}

	@Test
	public void whenCreateButTypeEmpty() throws JsonProcessingException, Exception {
		activityApi.setType(StringUtils.EMPTY);
		String response = resultActivityApi(post(URL, schoolId));

		assertThat(response).contains(ActivityValidation.TYPE_EMPTY.getDescription());
	}

	@Test
	public void whenCreateButSchoolNull() throws JsonProcessingException, Exception {
		activityApi.setSchoolId(null);
		assertThat(resultActivityApi(post(URL, schoolId))).contains(ActivityValidation.SCHOOL_ID_NULL.getDescription());

	}

	@Test
	public void whenCreateErrorService() throws JsonProcessingException, Exception {
		doThrow(new ActivityException(ActivityMessage.CREATE_ERROR)).when(activityService).create(Mockito.any(),Mockito.any());
		assertThat(resultActivityApi(post(URL, schoolId))).contains(ActivityMessage.CREATE_ERROR.getDescription());
	}
	
	@Test
	public void whenUpdateIsOk() throws JsonProcessingException, Exception {
		activityApi.setId(id);
		assertThat(resultActivityApi(put(URL, schoolId))).contains(ActivityMessage.UPDATE_OK.name());
	}
	
	@Test
	public void whenUpdateErrorService() throws JsonProcessingException, Exception {
		doThrow(new ActivityException(ActivityMessage.UPDATE_ERROR)).when(activityService).update(Mockito.any(),Mockito.any());
		assertThat(resultActivityApi(put(URL, schoolId))).contains(ActivityMessage.UPDATE_ERROR.getDescription());

	}
	
	@Test
	public void whenDeleteIsOk() throws JsonProcessingException, Exception {
		assertThat(resultActivityApi(delete(URL + "/{activityId}", schoolId, id)))
		.contains(ActivityMessage.DELETE_OK.name());

	}

	@Test
	public void whenDeleteErrorService() throws JsonProcessingException, Exception {
		doThrow(new ActivityException(ActivityMessage.GET_ERROR)).when(activityService).delete(schoolId,id);
		assertThat(resultActivityApi(delete(URL + "/{activityId}", schoolId, id)))
		.contains(ActivityMessage.GET_ERROR.getDescription());

	}

	private String toJson(final Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}
	
	private String resultActivityApi(MockHttpServletRequestBuilder requestBuilder)
			throws JsonProcessingException, Exception {
		return mockMvc.perform(requestBuilder.contentType(MediaType.APPLICATION_JSON).content(toJson(activityApi)))
				.andReturn().getResponse().getContentAsString();

	}
}
