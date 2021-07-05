package click.escuela.teacher.core.connector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.dto.ActivityDTO;
import click.escuela.teacher.core.exception.ActivityException;
import click.escuela.teacher.core.feign.ActivityController;

@Service
public class ActivityConnector {

	@Autowired
	private ActivityController activityController;

	public void create(String schoolId, ActivityApi activityApi) throws ActivityException {
		activityController.create(schoolId, activityApi);
	}

	public void update(String schoolId, ActivityApi activityApi) throws ActivityException {
		activityController.update(schoolId, activityApi);
	}

	public void delete(String schoolId, String teacherId) throws ActivityException {
		activityController.delete(schoolId, teacherId);
	}

	public List<ActivityDTO> getByStudentId(String schoolId, String studentId) {
		return activityController.getByStudentId(schoolId, studentId);
	}

	public List<ActivityDTO> getByCourseId(String schoolId, String courseId) {
		return activityController.getByCourseId(schoolId, courseId);
	}

	public List<ActivityDTO> getBySchoolId(String schoolId) {
		return activityController.getBySchoolId(schoolId);
	}

	public ActivityDTO getById(String schoolId, String activityId) throws ActivityException {
		return activityController.getById(schoolId, activityId);
	}
}
