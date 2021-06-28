package click.escuela.teacher.core.connector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.dto.ActivityDTO;
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
	private UUID studentId;
	private UUID id;
	private Integer schoolId;

	@Before
	public void setUp() throws TransactionException {
		courseId = UUID.randomUUID();
		studentId = UUID.randomUUID();
		id = UUID.randomUUID();
		schoolId = 1234;
		activityApi = ActivityApi.builder().name("Historia de las catatumbas").subject("Historia")
				.type(ActivityType.HOMEWORK.toString()).schoolId(schoolId).courseId(courseId.toString()).studentId(studentId.toString())
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

	@Test
	public void whenGetByIdIsOk() throws ActivityException {
		activityConnector.getById(schoolId.toString(),id.toString());
		verify(activityController).getById(schoolId.toString(),id.toString());
	}

	@Test
	public void whenGetByIsError() throws ActivityException {
		when(activityController.getById(Mockito.any(), Mockito.any()))
				.thenThrow(new ActivityException(ActivityMessage.GET_ERROR));
		assertThatExceptionOfType(ActivityException.class).isThrownBy(() -> {
			activityConnector.getById("6666", UUID.randomUUID().toString());
		}).withMessage(ActivityMessage.GET_ERROR.getDescription());
	}

	@Test
	public void whenGetByCourseIdIsOk() {
		activityConnector.getByCourseId(schoolId.toString(),courseId.toString());
		verify(activityController).getByCourseId(schoolId.toString(),courseId.toString());
	}

	@Test
	public void whenGetByCourseIsEmpty() {
		courseId = UUID.randomUUID();
		List<ActivityDTO> listEmpty = activityConnector.getByCourseId(schoolId.toString(),courseId.toString());
		assertThat(listEmpty).isEmpty();
	}
	
	@Test
	public void whenGetByStudentIdIsOk() {
		activityConnector.getByStudentId(schoolId.toString(),studentId.toString());
		verify(activityController).getByStudentId(schoolId.toString(),studentId.toString());
	}

	@Test
	public void whenGetByStudentIsEmpty() {
		studentId = UUID.randomUUID();
		List<ActivityDTO> listEmpty = activityConnector.getByStudentId(schoolId.toString(),studentId.toString());
		assertThat(listEmpty).isEmpty();
	}
	
	@Test
	public void whenGetBySchoolIdIsOk() throws ActivityException {
		activityConnector.getBySchoolId(schoolId.toString());
		verify(activityController).getBySchoolId(schoolId.toString());
	}

	@Test
	public void whenGetBySchoolIdIsEmty() {
		schoolId = 6666;
		List<ActivityDTO> listEmpty = activityConnector.getBySchoolId(schoolId.toString());
		assertThat(listEmpty).isEmpty();
	}
}
