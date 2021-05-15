package click.escuela.teacher.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

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
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.service.impl.GradeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {

	@Mock
	private GradeConnector gradeConnector;

	private GradeServiceImpl gradeService = new GradeServiceImpl();
	private GradeApi gradeApi;
	private String schoolId;
	private String studentId;
	private String courseId;

	@Before
	public void setUp() throws TransactionException {

		schoolId = UUID.randomUUID().toString();
		studentId = UUID.randomUUID().toString();
		courseId = UUID.randomUUID().toString();
		gradeApi = GradeApi.builder().name("Examen").subject("Matematica").type(GradeType.HOMEWORK.toString())
				.number(10).studentId(studentId).courseId(courseId).build();

		doNothing().when(gradeConnector).create(schoolId, gradeApi);

		ReflectionTestUtils.setField(gradeService, "gradeConnector", gradeConnector);

	}

	@Test
	public void whenCreateIsOk() {
		boolean hasError = false;
		try {
			gradeService.create(schoolId, gradeApi);
		} catch (Exception e) {
			hasError = true;
		}
		assertThat(hasError).isFalse();
	}

	@Test
	public void whenCreateIsError() throws TransactionException {
		doThrow(new TransactionException(GradeEnum.CREATE_ERROR.getCode(), GradeEnum.CREATE_ERROR.getDescription()))
				.when(gradeConnector).create(Mockito.anyString(), Mockito.any());

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			gradeService.create(schoolId, gradeApi);
		}).withMessage(GradeEnum.CREATE_ERROR.getDescription());
	}

}
