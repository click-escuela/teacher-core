package click.escuela.teacher.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.api.GradeCreateApi;
import click.escuela.teacher.core.connector.GradeConnector;
import click.escuela.teacher.core.dto.CourseStudentsShortDTO;
import click.escuela.teacher.core.dto.GradeDTO;
import click.escuela.teacher.core.enumerator.GradeMessage;
import click.escuela.teacher.core.exception.TransactionException;

@Service
public class GradeServiceImpl {

	@Autowired
	private GradeConnector gradeConnector;
	
	@Autowired
	private SchoolAdminServiceImpl schoolAdminService;

	public void create(String schoolId, GradeCreateApi gradeApi) throws TransactionException {
		gradeApi.getStudentId().stream().forEach(student -> {
			try {
				schoolAdminService.getById(schoolId, student, false);
			} catch (TransactionException e) {
				e.printStackTrace();
			}
		});
		
		gradeConnector.create(schoolId, gradeApi);
	}

	public void update(String schoolId, GradeApi gradeApi) throws TransactionException {
		schoolAdminService.getById(schoolId, gradeApi.getStudentId(), false);
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

	public List<CourseStudentsShortDTO> getCoursesWithGrades(String schoolId,List<CourseStudentsShortDTO> courses) {
		return gradeConnector.getCoursesWithGrades(schoolId, courses);
	}
}
