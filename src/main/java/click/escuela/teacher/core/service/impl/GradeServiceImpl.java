package click.escuela.teacher.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.connector.GradeConnector;
import click.escuela.teacher.core.exception.TransactionException;

@Service
public class GradeServiceImpl {

	@Autowired
	private GradeConnector gradeConnector;

	public void create(String schoolId, GradeApi gradeApi) throws TransactionException {
		gradeConnector.create(schoolId, gradeApi);
	}
}
