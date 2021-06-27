package click.escuela.teacher.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.dto.ActivityDTO;
import click.escuela.teacher.core.connector.ActivityConnector;
import click.escuela.teacher.core.exception.ActivityException;

@Service
public class ActivityServiceImpl {

	@Autowired
	private ActivityConnector activityConnector;

	public void create(String schoolId, ActivityApi activityApi) throws ActivityException {
		activityConnector.create(schoolId, activityApi);
	}

	public void update(String schoolId, ActivityApi activityApi) throws ActivityException {
		activityConnector.update(schoolId, activityApi);
	}

	public void delete(String schoolId, String activityId) throws ActivityException {
		activityConnector.delete(schoolId, activityId);
	}

	public List<ActivityDTO> getByStudent(String schoolId, String studentId) {
		return activityConnector.getByStudentId(schoolId, studentId);
	}

	public List<ActivityDTO> getByCourse(String schoolId, String courseId) {
		return activityConnector.getByCourseId(schoolId, courseId);
	}

	public List<ActivityDTO> getBySchool(String schoolId) {
		return activityConnector.getBySchoolId(schoolId);

	}
}
