package click.escuela.teacher.core.connector;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.enumerator.ActivityType;
import click.escuela.teacher.core.enumerator.GradeMessage;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.ActivityController;

@RunWith(MockitoJUnitRunner.class)
public class ActivityConnectorTest {

	@Mock
	private ActivityController activityController;

	private ActivityConnector activityConnector = new ActivityConnector();
	private ActivityApi activityApi;
	private UUID courseId;
	private Integer schoolId;

	@Before
	public void setUp() throws TransactionException {

		courseId = UUID.randomUUID();
		schoolId = 1234;
		activityApi = ActivityApi.builder().name("Historia de las catatumbas").subject("Historia")
				.type(ActivityType.HOMEWORK.toString()).schoolId(schoolId).courseId(courseId.toString())
				.dueDate(LocalDate.now()).description("Resolver todos los puntos").build();

		ReflectionTestUtils.setField(activityConnector, "activityController", activityController);
	}

	@Test
	public void whenCreateIsOk() throws TransactionException {
		activityConnector.create(schoolId.toString(), activityApi);
		verify(activityController).create(schoolId.toString(), activityApi);
	}

	@Test
	public void whenCreateIsError() throws TransactionException {

		when(activityController.create(Mockito.any(), Mockito.any())).thenThrow(new TransactionException(
				GradeMessage.CREATE_ERROR.getCode(), GradeMessage.CREATE_ERROR.getDescription()));

		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			activityConnector.create(schoolId.toString(), activityApi);
		}).withMessage(GradeMessage.CREATE_ERROR.getDescription());
	}

}
