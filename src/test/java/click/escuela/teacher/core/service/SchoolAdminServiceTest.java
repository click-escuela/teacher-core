package click.escuela.teacher.core.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import click.escuela.teacher.core.connector.SchoolAdminConnector;
import click.escuela.teacher.core.enumerator.StudentEnum;
import click.escuela.teacher.core.enumerator.TeacherMessage;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.service.impl.SchoolAdminServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SchoolAdminServiceTest {

	@Mock
	private SchoolAdminConnector schoolAdminConnector;

	private SchoolAdminServiceImpl schoolAdminService = new SchoolAdminServiceImpl();
	private String teacherId = UUID.randomUUID().toString();
	private String studentId = UUID.randomUUID().toString();
	private String schoolId = "1234";

	@Before
	public void setUp() throws TransactionException {
		ReflectionTestUtils.setField(schoolAdminService, "schoolAdminConnector", schoolAdminConnector);
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

}
