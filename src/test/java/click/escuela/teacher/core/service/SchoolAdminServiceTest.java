package click.escuela.teacher.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import click.escuela.teacher.core.connector.SchoolAdminConnector;
import click.escuela.teacher.core.dto.CourseStudentsShortDTO;
import click.escuela.teacher.core.dto.StudentShortDTO;
import click.escuela.teacher.core.enumerator.StudentEnum;
import click.escuela.teacher.core.enumerator.TeacherMessage;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.service.impl.GradeServiceImpl;
import click.escuela.teacher.core.service.impl.SchoolAdminServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SchoolAdminServiceTest {

	@Mock
	private SchoolAdminConnector schoolAdminConnector;
	
	@Mock
	private GradeServiceImpl gradeServiceImpl;

	private SchoolAdminServiceImpl schoolAdminService = new SchoolAdminServiceImpl();
	private String teacherId = UUID.randomUUID().toString();
	private String studentId = UUID.randomUUID().toString();
	private String schoolId = "1234";
	private String courseId = UUID.randomUUID().toString();
	private List<CourseStudentsShortDTO> coursesStudents = new ArrayList<>();


	@Before
	public void setUp() throws TransactionException {
		
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
		
		Mockito.when(schoolAdminConnector.getCourses(schoolId, teacherId)).thenReturn(coursesStudents);
		Mockito.when(gradeServiceImpl.getCoursesWithGrades(schoolId, coursesStudents)).thenReturn(coursesStudents);
		ReflectionTestUtils.setField(schoolAdminService, "schoolAdminConnector", schoolAdminConnector);
		ReflectionTestUtils.setField(schoolAdminService, "gradeServiceImpl", gradeServiceImpl);
	}

	@Test
	public void whenGetCourseStudentsOk() throws TeacherException {
		schoolAdminService.getCoursesAndStudents(schoolId, teacherId);
		verify(schoolAdminConnector).getCoursesAndStudents(schoolId, teacherId);
	}

	@Test
	public void whenGetCourseStudentsError() throws TeacherException {
		when(schoolAdminConnector.getCoursesAndStudents(Mockito.any(), Mockito.any()))
				.thenThrow(new TeacherException(TeacherMessage.GET_ERROR));

		assertThatExceptionOfType(TeacherException.class).isThrownBy(() -> {
			schoolAdminService.getCoursesAndStudents(schoolId, teacherId);
		}).withMessage(TeacherMessage.GET_ERROR.getDescription());
	}

	@Test
	public void whenGetByStudentIsOk() throws TransactionException {
		schoolAdminService.getById(schoolId, studentId, false);
		verify(schoolAdminConnector).getById(schoolId, studentId, false);
	}

	@Test
	public void whenGetByStudentIsError() throws TransactionException {
		doThrow(new TransactionException(StudentEnum.GET_ERROR.getCode(), StudentEnum.GET_ERROR.getDescription()))
				.when(schoolAdminConnector).getById(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean());

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			schoolAdminService.getById(schoolId, studentId, false);
		}).withMessage(StudentEnum.GET_ERROR.getDescription());
	}
	
	@Test
	public void whengGetCoursesByTeacherIdIsOk() throws TeacherException {
		List<CourseStudentsShortDTO> courseStudents = schoolAdminService.getCoursesWithGrades(schoolId, teacherId);
		verify(schoolAdminConnector).getCourses(schoolId, teacherId);
		assertThat(courseStudents).isNotEmpty();
	}
	
	@Test
	public void whengGetCoursesByTeacherIdIsEmpty() throws TeacherException {
		Mockito.when(schoolAdminConnector.getCourses(schoolId, teacherId)).thenReturn(new ArrayList<>());
		List<CourseStudentsShortDTO> courseStudents = schoolAdminService.getCoursesWithGrades(schoolId, teacherId);
		verify(schoolAdminConnector).getCourses(schoolId, teacherId);
		assertThat(courseStudents).isEmpty();
	}
	
}
