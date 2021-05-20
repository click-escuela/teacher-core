package click.escuela.teacher.core.connector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

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
import click.escuela.teacher.core.enumerator.StudentEnum;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.GradeController;
import click.escuela.teacher.core.feign.StudentController;

@RunWith(MockitoJUnitRunner.class)
public class GradeConnectorTest {

	@Mock
	private GradeController gradeController;

	@Mock
	private StudentController studentController;

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

		ReflectionTestUtils.setField(gradeConnector, "studentController", studentController);
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
	public void whenGetByIdIsOk() {
		boolean hasError = false;

		try {
			gradeConnector.getById(schoolId.toString(), gradeApi.getStudentId(), false);
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenGetByIdIsError() throws TransactionException {

		when(studentController.getById(Mockito.any(), Mockito.any(), Mockito.anyBoolean()))
				.thenThrow(new TransactionException(StudentEnum.CREATE_ERROR.getCode(),
						StudentEnum.CREATE_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			gradeConnector.getById(schoolId.toString(), gradeApi.getStudentId(), false);
		}).withMessage(StudentEnum.CREATE_ERROR.getDescription());
	}
}
