package click.escuela.teacher.core.connector;

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

import click.escuela.teacher.core.enumerator.StudentEnum;
import click.escuela.teacher.core.enumerator.TeacherMessage;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.SchoolAdminController;

@RunWith(MockitoJUnitRunner.class)
public class SchoolAdminConnectorTest {

	@Mock
	private SchoolAdminController schoolAdminController;

	private SchoolAdminConnector schoolAdminConnector = new SchoolAdminConnector();
	private String teacherId = UUID.randomUUID().toString();
	private String studentId = UUID.randomUUID().toString();
	private String schoolId = "1234";

	@Before
	public void setUp() throws TransactionException {
		ReflectionTestUtils.setField(schoolAdminConnector, "schoolAdminController", schoolAdminController);
	}

	@Test
	public void whenGetCourseStudentsOk() throws TeacherException {
		schoolAdminConnector.getCoursesAndStudents(schoolId, teacherId);
		verify(schoolAdminController).getCoursesAndStudents(schoolId, teacherId);
	}

	@Test
	public void whenGetCourseStudentsError() throws TeacherException {
		when(schoolAdminController.getCoursesAndStudents(Mockito.any(), Mockito.any()))
				.thenThrow(new TeacherException(TeacherMessage.GET_ERROR));

		assertThatExceptionOfType(TeacherException.class).isThrownBy(() -> {
			schoolAdminConnector.getCoursesAndStudents(schoolId, teacherId);
		}).withMessage(TeacherMessage.GET_ERROR.getDescription());
	}

	@Test
	public void whenGetByStudentIsOk() throws TransactionException {
		schoolAdminConnector.getById(schoolId, studentId, false);
		verify(schoolAdminController).getById(schoolId, studentId, false);
	}

	@Test
	public void whenGetByStudentIsError() throws TransactionException {
		doThrow(new TransactionException(StudentEnum.GET_ERROR.getCode(), StudentEnum.GET_ERROR.getDescription()))
				.when(schoolAdminController).getById(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean());

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			schoolAdminConnector.getById(schoolId, studentId, false);
		}).withMessage(StudentEnum.GET_ERROR.getDescription());
	}

}
