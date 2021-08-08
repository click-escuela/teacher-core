package click.escuela.teacher.core.connector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.feign.SchoolAdminController;

@Service
public class SchoolAdminConnector {

	@Autowired
	private SchoolAdminController schoolAdminController;

	public TeacherCourseStudentsDTO getCoursesAndStudents(String schoolId, String teacherId) throws TeacherException {
		return schoolAdminController.getCoursesAndStudents(schoolId, teacherId);
	}

	public void getById(String schoolId, String studentId, Boolean fullDetail) throws TransactionException {
		schoolAdminController.getById(schoolId, studentId, fullDetail);
	}
}
