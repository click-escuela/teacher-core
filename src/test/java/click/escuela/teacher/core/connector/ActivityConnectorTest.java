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
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.enumerator.ActivityType;
import click.escuela.teacher.core.exception.ActivityException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.ActivityController;

@RunWith(MockitoJUnitRunner.class)
public class ActivityConnectorTest {

	@Mock
	private ActivityController activityController;

	private ActivityConnector activityConnector = new ActivityConnector();
	private ActivityApi activityApi;
	private UUID courseId;
	private UUID id;
	private Integer schoolId;

	@Before
	public void setUp() throws TransactionException {
		courseId = UUID.randomUUID();
		id = UUID.randomUUID();
		schoolId = 1234;
		activityApi = ActivityApi.builder().name("Historia de las catatumbas").subject("Historia")
				.type(ActivityType.HOMEWORK.toString()).schoolId(schoolId).courseId(courseId.toString())
				.dueDate(LocalDate.now()).description("Resolver todos los puntos").build();

		ReflectionTestUtils.setField(activityConnector, "activityController", activityController);
	}

	@Test
	public void whenCreateIsOk() throws ActivityException {
		activityConnector.create(schoolId.toString(), activityApi);
		verify(activityController).create(schoolId.toString(), activityApi);
	}

	@Test
	public void whenCreateIsError() throws ActivityException {
		when(activityController.create(Mockito.any(), Mockito.any()))
				.thenThrow(new ActivityException(ActivityMessage.CREATE_ERROR));
		assertThatExceptionOfType(ActivityException.class).isThrownBy(() -> {
			activityConnector.create(schoolId.toString(), activityApi);
		}).withMessage(ActivityMessage.CREATE_ERROR.getDescription());
	}

	@Test
	public void whenUpdateIsOk() throws ActivityException {
		activityApi.setId(id.toString());
		activityConnector.update(schoolId.toString(), activityApi);
		verify(activityController).update(schoolId.toString(), activityApi);
	}

	@Test
	public void whenUpdateIsError() throws ActivityException {
		when(activityController.update(Mockito.any(), Mockito.any()))
				.thenThrow(new ActivityException(ActivityMessage.UPDATE_ERROR));
		assertThatExceptionOfType(ActivityException.class).isThrownBy(() -> {
			activityConnector.update(schoolId.toString(), activityApi);
		}).withMessage(ActivityMessage.UPDATE_ERROR.getDescription());
	}

	@Test
	public void whenDeleteIsOk() throws ActivityException {
		activityConnector.delete(schoolId.toString(), id.toString());
		verify(activityController).delete(schoolId.toString(), id.toString());
	}

	@Test
	public void whenDeleteIsError() throws ActivityException {
		when(activityController.delete(Mockito.any(), Mockito.any()))
				.thenThrow(new ActivityException(ActivityMessage.GET_ERROR));
		assertThatExceptionOfType(ActivityException.class).isThrownBy(() -> {
			activityConnector.delete("6666", UUID.randomUUID().toString());
		}).withMessage(ActivityMessage.GET_ERROR.getDescription());
	}

}
