package click.escuela.teacher.core.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.exception.ActivityException;
import click.escuela.teacher.core.feign.ActivityController;

@Service
public class ActivityConnector {

	@Autowired
	private ActivityController activityController;

	public void create(String schoolId, ActivityApi activityApi) throws ActivityException {
		activityController.create(schoolId, activityApi);
	}

	public void delete(String schoolId, String teacherId) throws ActivityException {
		activityController.delete(schoolId, teacherId);
	}
}
