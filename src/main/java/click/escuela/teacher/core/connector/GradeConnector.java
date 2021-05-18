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
		gradeController.create(schoolId, gradeApi);
	}

	public void update(String schoolId, GradeApi gradeApi) throws TransactionException {
		gradeController.update(schoolId, gradeApi);
	}
	
	public void getById(String schoolId, String studentId, Boolean fullDetail) throws TransactionException {
		studentController.getById(schoolId, studentId, fullDetail);
	}

}
