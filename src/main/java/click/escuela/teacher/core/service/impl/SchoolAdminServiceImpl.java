package click.escuela.teacher.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.connector.SchoolAdminConnector;
import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;

@Service
public class SchoolAdminServiceImpl {

	@Autowired
	private SchoolAdminConnector schoolAdminConnector;

	public TeacherCourseStudentsDTO getCoursesAndStudents(String schoolId, String teacherId) throws TeacherException {
		return schoolAdminConnector.getCoursesAndStudents(schoolId, teacherId);
	}

	public void getById(String schoolId, String studentId, Boolean fullDetail) throws TransactionException {
		schoolAdminConnector.getById(schoolId, studentId, fullDetail);
	}

}
