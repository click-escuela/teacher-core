package click.escuela.teacher.core.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import click.escuela.teacher.core.dto.CourseDTO;
import click.escuela.teacher.core.dto.StudentDTO;
import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.exception.TransactionException;

@FeignClient(name = "school-admin")
public interface SchoolAdminController {

	@GetMapping(value = "/school/{schoolId}/student/{studentId}")
	public StudentDTO getById(@PathVariable("schoolId") String schoolId, @PathVariable("studentId") String studentId,
			@RequestParam("fullDetail") Boolean fullDetail) throws TransactionException;

	@GetMapping(value = "/school/{schoolId}/teacher/{teacherId}/courses")
	public TeacherCourseStudentsDTO getCoursesAndStudents(@PathVariable("schoolId") String schoolId,
			@PathVariable("teacherId") String teacherId) throws TeacherException;

	@GetMapping(value = "/school/{schoolId}/teacher/{teacherId}/coursesList")
	public List<CourseDTO> getCourses(@PathVariable("schoolId") String schoolId,
			@PathVariable("teacherId") String teacherId) throws TeacherException;

}
