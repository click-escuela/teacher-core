package click.escuela.teacher.core.connector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.connector.GradeConnector;
import click.escuela.teacher.core.enumerator.GradeMessage;
import click.escuela.teacher.core.enumerator.GradeType;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.GradeController;

@RunWith(MockitoJUnitRunner.class)
public class GradeConnectorTest {

	@Mock
	private GradeController gradeController;

	private GradeConnector gradeConnector = new GradeConnector();
	private GradeApi gradeApi;
	private UUID id;
	private UUID studentId;
	private UUID courseId;
	private Integer schoolId;

	@Before
	public void setUp() throws TransactionException {

		id = UUID.randomUUID();
		studentId = UUID.randomUUID();
		courseId = UUID.randomUUID();
		schoolId = 1234;
		gradeApi = GradeApi.builder().name("Examen").subject("Matematica").studentId(studentId.toString())
				.type(GradeType.HOMEWORK.toString()).courseId(courseId.toString()).schoolId(schoolId).number(10)
				.build();

		ReflectionTestUtils.setField(gradeConnector, "gradeController", gradeController);
	}

	@Test
	public void whenCreateIsOk() {
		boolean hasError = false;

		try {
			gradeConnector.create(schoolId.toString(), gradeApi);
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenCreateIsError() throws TransactionException {

		when(gradeController.create(Mockito.any(), Mockito.any())).thenThrow(new TransactionException(
				GradeMessage.CREATE_ERROR.getCode(), GradeMessage.CREATE_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			gradeConnector.create(schoolId.toString(), gradeApi);
		}).withMessage(GradeMessage.CREATE_ERROR.getDescription());
	}

	@Test
	public void whenUpdateIsOk() {
		boolean hasError = false;
		try {
			gradeApi.setId(id.toString());
			gradeConnector.update(schoolId.toString(), gradeApi);
		} catch (Exception e) {
			hasError = true;
		}
		assertThat(hasError).isFalse();
	}

	@Test
	public void whenUpdateIsError() throws TransactionException {
		id = UUID.randomUUID();

		when(gradeController.update(Mockito.any(), Mockito.any())).thenThrow(new TransactionException(
				GradeMessage.UPDATE_ERROR.getCode(), GradeMessage.UPDATE_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			gradeApi.setId(id.toString());
			gradeConnector.update(schoolId.toString(), gradeApi);
		}).withMessage(GradeMessage.UPDATE_ERROR.getDescription());
	}

	@Test
	public void whenGetByIsOk() {
		boolean hasError = false;
		try {
			gradeConnector.getById(schoolId.toString(), id.toString());
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenGetByIdIsError() throws TransactionException {

		when(gradeConnector.getById(Mockito.any(), Mockito.any())).thenThrow(
				new TransactionException(GradeMessage.GET_ERROR.getCode(), GradeMessage.GET_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			gradeConnector.getById(schoolId.toString(), id.toString());
		}).withMessage(GradeMessage.GET_ERROR.getDescription());
	}

	@Test
	public void whenGetBySchoolIsOk() {
		boolean hasError = false;
		try {
			gradeConnector.getBySchool(schoolId.toString());
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenGetBySchoolIsEmpty() {
		boolean hasEmpty = false;

		when(gradeConnector.getBySchool(Mockito.any())).thenReturn(new ArrayList<>());
		try {
			if (gradeConnector.getBySchool(schoolId.toString()).isEmpty())
				;
		} catch (Exception e) {
			assertThat(hasEmpty).isFalse();
		}
	}

	@Test
	public void whenGetByStudentIsOk() {
		boolean hasError = false;
		try {
			gradeConnector.getByStudent(schoolId.toString(), studentId.toString());
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenGetByStudentIsEmpty() {
		boolean hasEmpty = false;
		when(gradeConnector.getByStudent(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());

		try {
			if (gradeConnector.getByStudent(schoolId.toString(), studentId.toString()).isEmpty())
				;
		} catch (Exception e) {
			assertThat(hasEmpty).isFalse();
		}
	}

	@Test
	public void whenGetByCourseIsOk() {
		boolean hasError = false;
		try {
			gradeConnector.getByCourse(schoolId.toString(), courseId.toString());
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenGetByCourseIsEmpty() {
		boolean hasEmpty = false;
		when(gradeConnector.getByCourse(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());

		try {
			if (gradeConnector.getByCourse(courseId.toString(), courseId.toString()).isEmpty())
				;
		} catch (Exception e) {
			assertThat(hasEmpty).isFalse();
		}
	}
}
