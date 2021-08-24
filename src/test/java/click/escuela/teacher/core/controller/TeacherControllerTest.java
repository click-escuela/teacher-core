package click.escuela.teacher.core.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import click.escuela.teacher.core.dto.AdressDTO;
import click.escuela.teacher.core.dto.CourseStudentsDTO;
import click.escuela.teacher.core.dto.CourseStudentsShortDTO;
import click.escuela.teacher.core.dto.StudentShortDTO;
import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.enumerator.DocumentType;
import click.escuela.teacher.core.enumerator.TeacherMessage;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.rest.TeacherController;
import click.escuela.teacher.core.rest.handler.Handler;
import click.escuela.teacher.core.service.impl.SchoolAdminServiceImpl;

@EnableWebMvc
@RunWith(MockitoJUnitRunner.class)
public class TeacherControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private TeacherController teacherController;

	@Mock
	private SchoolAdminServiceImpl schoolAdminServiceImpl;

	private ObjectMapper mapper;
	private String teacherId = UUID.randomUUID().toString();
	private String schoolId = "1234";
	private String courseId = UUID.randomUUID().toString();
	private String studentId = UUID.randomUUID().toString();
	private final static String URL = "/school/{schoolId}/teacher/";
	private List<CourseStudentsShortDTO> coursesStudents = new ArrayList<>();

	@Before
	public void setUp() throws TransactionException {
		mockMvc = MockMvcBuilders.standaloneSetup(teacherController).setControllerAdvice(new Handler()).build();
		mapper = new ObjectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false)
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		ReflectionTestUtils.setField(teacherController, "schoolAdminServiceImpl", schoolAdminServiceImpl);
		CourseStudentsDTO course = new CourseStudentsDTO();
		course.setCountStudent(20);
		course.setDivision("f");
		course.setId(UUID.randomUUID().toString());
		course.setYear(2021);
		course.setStudents(new ArrayList<>());
		List<CourseStudentsDTO> courses = new ArrayList<>();
		courses.add(course);
		TeacherCourseStudentsDTO teacherCourseStudentsDTO = TeacherCourseStudentsDTO.builder().id(teacherId)
				.name("Mariana").surname("Lopez").birthday(LocalDate.now()).documentType(DocumentType.DNI)
				.document("25897863").cellPhone("1589632485").email("mariAna@gmail.com").courses(courses)
				.schoolId(Integer.valueOf(schoolId)).adress(new AdressDTO()).build();
		
		CourseStudentsShortDTO courseStudent = new CourseStudentsShortDTO();
		courseStudent.setCountStudent(20);
		courseStudent.setDivision("B");
		courseStudent.setId(courseId);
		courseStudent.setYear(10);
		StudentShortDTO student = new StudentShortDTO();
		student.setId(studentId);
		student.setName("Anotnio");
		student.setSurname("Liendro");
		List<StudentShortDTO> students = new ArrayList<>();
		students.add(student);
		courseStudent.setStudents(students);
		coursesStudents.add(courseStudent);
		
		Mockito.when(schoolAdminServiceImpl.getCoursesAndStudents(schoolId, teacherId))
				.thenReturn(teacherCourseStudentsDTO);
		Mockito.when(schoolAdminServiceImpl.getCoursesWithGrades(schoolId, teacherId)).thenReturn(coursesStudents);
	}

	@Test
	public void getByCoursesAndStudentsTests() throws JsonProcessingException, Exception {
		assertThat(mapper.readValue(result(get(URL + "{teacherId}/courses", schoolId, teacherId)),
				TeacherCourseStudentsDTO.class)).hasFieldOrPropertyWithValue("id", teacherId);

		doThrow(new TeacherException(TeacherMessage.GET_ERROR)).when(schoolAdminServiceImpl)
				.getCoursesAndStudents(schoolId, teacherId);
		assertThat(result(get(URL + "{teacherId}/courses", schoolId, teacherId)))
				.contains(TeacherMessage.GET_ERROR.getDescription());
	}
	
	@Test
	public void getCoursesWithGradesTests() throws JsonProcessingException, Exception {
		assertThat(mapper.readValue(result(get(URL + "{teacherId}/coursesList", schoolId, teacherId)),
				new TypeReference<List<CourseStudentsShortDTO>>() {}).get(0).getId()).contains(courseId);

		doThrow(new TeacherException(TeacherMessage.GET_ERROR)).when(schoolAdminServiceImpl)
				.getCoursesWithGrades(schoolId, teacherId);
		assertThat(result(get(URL + "{teacherId}/coursesList", schoolId, teacherId)))
				.contains(TeacherMessage.GET_ERROR.getDescription());
	}

	private String result(MockHttpServletRequestBuilder requestBuilder) throws JsonProcessingException, Exception {
		return mockMvc.perform(requestBuilder.contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();
	}
}
