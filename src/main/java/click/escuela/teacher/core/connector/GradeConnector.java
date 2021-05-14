package click.escuela.teacher.core.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.GradeController;
import click.escuela.teacher.core.feign.StudentController;

@Service
public class GradeConnector {

	@Autowired
	private StudentController studentController;

	@Autowired
	private GradeController gradeController;

	public void create(String schoolId, GradeApi gradeApi) throws TransactionException {
		studentController.getById(schoolId, gradeApi.getStudentId(), false);
		gradeController.create(schoolId, gradeApi);
	}

}
