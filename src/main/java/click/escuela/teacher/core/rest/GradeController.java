package click.escuela.teacher.core.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.api.GradeCreateApi;
import click.escuela.teacher.core.dto.GradeDTO;

import click.escuela.teacher.core.enumerator.GradeMessage;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.service.impl.GradeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/school/{schoolId}/grade")
public class GradeController {

	@Autowired
	private GradeServiceImpl gradeService;

	@Operation(summary = "Get grade by Id", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GradeDTO.class))) })
	@GetMapping(value = "{gradeId}")
	public ResponseEntity<GradeDTO> getById(@PathVariable("schoolId") String schoolId,
			@PathVariable("gradeId") String gradeId) throws TransactionException {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(gradeService.getById(schoolId, gradeId));
	}

	@Operation(summary = "Get grade by schoolId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GradeDTO.class))) })
	@GetMapping(value = "")
	public ResponseEntity<List<GradeDTO>> getBySchool(@PathVariable("schoolId") String schoolId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(gradeService.getBySchoolId(schoolId));
	}

	@Operation(summary = "Get grade by studentId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GradeDTO.class))) })
	@GetMapping(value = "student/{studentId}")
	public ResponseEntity<List<GradeDTO>> getByStudentId(@PathVariable("schoolId") String schoolId,
			@PathVariable("studentId") String studentId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(gradeService.getByStudentId(schoolId, studentId));
	}

	@Operation(summary = "Get grade by courseId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GradeDTO.class))) })
	@GetMapping(value = "course/{courseId}")
	public ResponseEntity<List<GradeDTO>> getByCourseId(@PathVariable("schoolId") String schoolId,
			@PathVariable("courseId") String courseId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(gradeService.getByCourseId(schoolId, courseId));
	}

	@Operation(summary = "Create grade", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })

	public ResponseEntity<GradeMessage> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated GradeCreateApi gradeApi) throws TransactionException {
		gradeService.create(schoolId, gradeApi);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(GradeMessage.CREATE_OK);
	}

	@Operation(summary = "Update grade", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GradeMessage> update(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated GradeApi gradeApi) throws TransactionException {
		gradeService.update(schoolId, gradeApi);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(GradeMessage.UPDATE_OK);
	}

}
