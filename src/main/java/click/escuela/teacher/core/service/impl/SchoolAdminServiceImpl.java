package click.escuela.teacher.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import click.escuela.teacher.core.connector.SchoolAdminConnector;
import click.escuela.teacher.core.dto.CourseDTO;
import click.escuela.teacher.core.dto.StudentDTO;
import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;

@Service
public class SchoolAdminServiceImpl {

	@Autowired
	private SchoolAdminConnector schoolAdminConnector;
	
	@Autowired
	private GradeServiceImpl gradeServiceImpl;

	public TeacherCourseStudentsDTO getCoursesAndStudents(String schoolId, String teacherId) throws TeacherException {
		return schoolAdminConnector.getCoursesAndStudents(schoolId, teacherId);
	}

	public StudentDTO getById(String schoolId, String studentId, Boolean fullDetail) throws TransactionException {
		return schoolAdminConnector.getById(schoolId, studentId, fullDetail);
	}
	
	public List<CourseDTO> getCoursesWithGrades(String schoolId, String teacherId) throws TeacherException {
		List<CourseDTO> courses = schoolAdminConnector.getCourses(schoolId, teacherId);
		if(!courses.isEmpty()) {
			courses = gradeServiceImpl.getCoursesWithGrades(schoolId, courses); 
		}
		return courses;
	}

}
