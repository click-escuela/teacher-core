package click.escuela.teacher.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import click.escuela.teacher.core.dto.TeacherCourseStudentsDTO;
import click.escuela.teacher.core.exception.TeacherException;
import click.escuela.teacher.core.service.impl.SchoolAdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/school/{schoolId}")
public class TeacherController {

	@Autowired
	private SchoolAdminServiceImpl schoolAdminServiceImpl;

	@Operation(summary = "Get courses and students by teacherId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherCourseStudentsDTO.class))) })
	@GetMapping(value = "/teacher/{teacherId}/courses", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<TeacherCourseStudentsDTO> getCoursesAndStudents(@PathVariable("schoolId") String schoolId,
			@PathVariable("teacherId") String teacherId) throws TeacherException {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(schoolAdminServiceImpl.getCoursesAndStudents(schoolId, teacherId));
	}

}
