package click.escuela.teacher.core.connector;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.dto.CourseStudentsShortDTO;
import click.escuela.teacher.core.dto.StudentDTO;
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

	public StudentDTO getById(String schoolId, String studentId, Boolean fullDetail) throws TransactionException {
		return schoolAdminController.getById(schoolId, studentId, fullDetail);
	}

	public List<CourseStudentsShortDTO> getCourses(String schoolId, String teacherId) throws TeacherException {
		return schoolAdminController.getCourses(schoolId, teacherId);
	}
}
