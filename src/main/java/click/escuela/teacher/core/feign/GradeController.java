package click.escuela.teacher.core.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.api.GradeCreateApi;
import click.escuela.teacher.core.dto.CourseStudentsShortDTO;
import click.escuela.teacher.core.dto.GradeDTO;

import click.escuela.teacher.core.enumerator.GradeMessage;
import click.escuela.teacher.core.exception.TransactionException;

@FeignClient(name = "grades")
public interface GradeController {

	// GradeController
	@GetMapping(value = "/school/{schoolId}/grade/{gradeId}")
	public GradeDTO getById(@PathVariable("schoolId") String schoolId, @PathVariable("gradeId") String gradeId)
			throws TransactionException;

	@GetMapping(value = "/school/{schoolId}/grade")
	public List<GradeDTO> getBySchool(@PathVariable("schoolId") String schoolId);

	@GetMapping(value = "/school/{schoolId}/grade/student/{studentId}")
	public List<GradeDTO> getByStudent(@PathVariable("schoolId") String schoolId,
			@PathVariable("studentId") String studentId);

	@GetMapping(value = "/school/{schoolId}/grade/course/{courseId}")
	public List<GradeDTO> getByCourse(@PathVariable("schoolId") String schoolId,
			@PathVariable("courseId") String courseId);

	@PostMapping(value = "/school/{schoolId}/grade")
	public ResponseEntity<GradeMessage> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated GradeCreateApi gradeApi) throws TransactionException;

	@PutMapping(value = "/school/{schoolId}/grade")
	public ResponseEntity<GradeMessage> update(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated GradeApi gradeApi) throws TransactionException;

	@PutMapping(value = "/school/{schoolId}/grade/courses")
	public List<CourseStudentsShortDTO> getCoursesWithGrades(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated List<CourseStudentsShortDTO> courses);
}
