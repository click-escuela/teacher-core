package click.escuela.teacher.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import click.escuela.teacher.core.dto.StudentDTO;
import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.exception.TeacherException;

@FeignClient(name = "students", url = "${provider.student.url}")
public interface SchoolAdminController {
	public final String URL = "/click-escuela/school-admin/school/{schoolId}";

	@GetMapping(value = URL + "/student/{studentId}")
	public StudentDTO getById(@PathVariable("schoolId") String schoolId, @PathVariable("studentId") String studentId,
			@RequestParam("fullDetail") Boolean fullDetail) throws TeacherException;

	@GetMapping(value = URL + "/teacher/{teacherId}/courses")
	public TeacherCourseStudentsDTO getCoursesAndStudents(@PathVariable("schoolId") String schoolId,
			@PathVariable("teacherId") String teacherId) throws TeacherException;

}
