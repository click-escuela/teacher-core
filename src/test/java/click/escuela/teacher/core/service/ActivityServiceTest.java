package click.escuela.teacher.core.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

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
import click.escuela.teacher.core.connector.ActivityConnector;
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.enumerator.ActivityType;
import click.escuela.teacher.core.exception.ActivityException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.service.impl.ActivityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {

	@Mock
	private ActivityConnector activityConnector;

	private ActivityServiceImpl activityService = new ActivityServiceImpl();
	private ActivityApi activityApi;
	private UUID courseId;
	private UUID id;
	private Integer schoolId;

	@Before
	public void setUp() throws TransactionException {

		courseId = UUID.randomUUID();
		schoolId = 1234;
		id = UUID.randomUUID();
		activityApi = ActivityApi.builder().name("Historia de las catatumbas").subject("Historia")
				.type(ActivityType.HOMEWORK.toString()).schoolId(schoolId).courseId(courseId.toString())
				.dueDate(LocalDate.now()).description("Resolver todos los puntos").build();

		ReflectionTestUtils.setField(activityService, "activityConnector", activityConnector);
	}

	@Test
	public void whenCreateIsOk() throws TransactionException {
		activityService.create(schoolId.toString(), activityApi);
		verify(activityConnector).create(schoolId.toString(), activityApi);
	}

	@Test
	public void whenCreateIsError() throws TransactionException {
		doThrow(new ActivityException(ActivityMessage.CREATE_ERROR)).when(activityConnector).create(Mockito.anyString(),
						Mockito.any());
		assertThatExceptionOfType(TransactionException.class).isThrownBy(() -> {
			activityService.create(schoolId.toString(), activityApi);
		}).withMessage(ActivityMessage.CREATE_ERROR.getDescription());
	}
	
	@Test
	public void whenDeleteIsOk() throws ActivityException{
		activityService.delete(schoolId.toString(),id.toString());
		verify(activityConnector).delete(schoolId.toString(),id.toString());
	}
	
	@Test
	public void whenDeleteIsError() throws ActivityException{
		doThrow(new ActivityException(ActivityMessage.GET_ERROR)).when(activityConnector).delete(Mockito.any(), Mockito.any());
		assertThatExceptionOfType(ActivityException.class).isThrownBy(() -> {
			activityService.delete("6666",UUID.randomUUID().toString());
		}).withMessage(ActivityMessage.GET_ERROR.getDescription());
	}

}
