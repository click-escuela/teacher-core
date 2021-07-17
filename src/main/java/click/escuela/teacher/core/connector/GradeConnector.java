package click.escuela.teacher.core.connector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.dto.GradeDTO;

import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.GradeController;

@Service
public class GradeConnector {

	@Autowired
	private GradeController gradeController;

	public void create(String schoolId, GradeApi gradeApi) throws TransactionException {
		gradeController.create(schoolId, gradeApi);
	}

	public void update(String schoolId, GradeApi gradeApi) throws TransactionException {
		gradeController.update(schoolId, gradeApi);
	}

	public GradeDTO getById(String schoolId, String id) throws TransactionException {
		return gradeController.getById(schoolId, id);
	}

	public List<GradeDTO> getBySchool(String schoolId) {
		return gradeController.getBySchool(schoolId);
	}

	public List<GradeDTO> getByStudent(String schoolId, String studentId) {
		return gradeController.getByStudent(schoolId, studentId);
	}

	public List<GradeDTO> getByCourse(String schoolId, String courseId) {
		return gradeController.getByCourse(schoolId, courseId);
	}


}
