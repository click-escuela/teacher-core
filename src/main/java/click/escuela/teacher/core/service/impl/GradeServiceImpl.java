package click.escuela.teacher.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.connector.GradeConnector;
import click.escuela.teacher.core.dto.GradeDTO;

import click.escuela.teacher.core.exception.TransactionException;

@Service
public class GradeServiceImpl {

	@Autowired
	private GradeConnector gradeConnector;

	public void create(String schoolId, GradeApi gradeApi) throws TransactionException {
		gradeConnector.getById(schoolId, gradeApi.getStudentId(), false);
		gradeConnector.create(schoolId, gradeApi);
	}

	public void update(String schoolId, GradeApi gradeApi) throws TransactionException {
		gradeConnector.getById(schoolId, gradeApi.getStudentId(), false);
		gradeConnector.update(schoolId, gradeApi);
	}
	
	public GradeDTO getById(String schoolId, String id) throws TransactionException {
		return gradeConnector.getById(schoolId, id);
	}
	
	public List<GradeDTO> getBySchoolId(String schoolId) {
		return gradeConnector.getBySchool(schoolId);
	}

	public List<GradeDTO> getByStudentId(String schoolId, String studentId) {
		return gradeConnector.getByStudent(schoolId, studentId);
	}

	public List<GradeDTO> getByCourseId(String schoolId, String courseId) {
		return gradeConnector.getByCourse(schoolId, courseId);
	}

}
