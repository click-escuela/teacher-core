package click.escuela.teacher.core.service;

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
import click.escuela.teacher.core.enumerator.GradeEnum;
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
	private String schoolId;

	@Before
	public void setUp() throws TransactionException {
		schoolId = UUID.randomUUID().toString();

		gradeApi = GradeApi.builder().name("Examen").subject("Matematica").type(GradeType.HOMEWORK.toString())
				.number(10).build();

		ReflectionTestUtils.setField(gradeConnector, "studentController", studentController);

		ReflectionTestUtils.setField(gradeConnector, "gradeController", gradeController);
	}

	@Test
	public void whenCreateIsOk() {
		boolean hasError = false;

		try {
			gradeConnector.create(schoolId, gradeApi);
		} catch (Exception e) {
			assertThat(hasError).isFalse();
		}
	}

	@Test
	public void whenCreateIsErrorByStudent() throws TransactionException {

		when(studentController.getById(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new TransactionException(
				StudentEnum.CREATE_ERROR.getCode(), StudentEnum.CREATE_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {

			gradeConnector.create(schoolId, gradeApi);
		}).withMessage(StudentEnum.CREATE_ERROR.getDescription());
	}

	@Test
	public void whenCreateIsErrorByGrade() throws TransactionException {

		when(gradeController.create(Mockito.any(), Mockito.any())).thenThrow(
				new TransactionException(GradeEnum.CREATE_ERROR.getCode(), GradeEnum.CREATE_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {

			gradeConnector.create(schoolId, gradeApi);
		}).withMessage(GradeEnum.CREATE_ERROR.getDescription());
	}
}
