package click.escuela.teacher.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.connector.ActivityConnector;
import click.escuela.teacher.core.exception.ActivityException;

@Service
public class ActivityServiceImpl {

	@Autowired
	private ActivityConnector activityConnector;

	public void create(String schoolId, ActivityApi activityApi) throws ActivityException {
		activityConnector.create(schoolId, activityApi);
	}
	
	public void delete(String schoolId, String teacherId) throws ActivityException {
		activityConnector.delete(schoolId, teacherId);
	}
}
