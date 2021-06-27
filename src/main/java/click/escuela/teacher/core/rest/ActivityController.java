package click.escuela.teacher.core.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.dto.ActivityDTO;
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.exception.ActivityException;
import click.escuela.teacher.core.service.impl.ActivityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/school/{schoolId}/activity")
public class ActivityController {

	@Autowired
	private ActivityServiceImpl activityService;

	@Operation(summary = "Get activity by schoolId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDTO.class))) })
	@GetMapping(value = "")
	public ResponseEntity<List<ActivityDTO>> getBySchool(@PathVariable("schoolId") String schoolId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(activityService.getBySchool(schoolId));
	}

	@Operation(summary = "Get activity by courseId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDTO.class))) })
	@GetMapping(value = "course/{courseId}")
	public ResponseEntity<List<ActivityDTO>> getByCourse(@PathVariable("schoolId") String schoolId,
			@PathVariable("courseId") String courseId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(activityService.getByCourse(schoolId, courseId));
	}

	@Operation(summary = "Get activity by studentId", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDTO.class))) })
	@GetMapping(value = "student/{studentId}")
	public ResponseEntity<List<ActivityDTO>> getByStudent(@PathVariable("schoolId") String schoolId,
			@PathVariable("studentId") String studentId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(activityService.getByStudent(schoolId, studentId));
	}

	@Operation(summary = "Create Activity", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ActivityMessage> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated ActivityApi activityApi) throws ActivityException {
		activityService.create(schoolId, activityApi);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ActivityMessage.CREATE_OK);
	}

	@Operation(summary = "Update Activity", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@PutMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ActivityMessage> update(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated ActivityApi activityApi) throws ActivityException {
		activityService.update(schoolId, activityApi);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ActivityMessage.UPDATE_OK);
	}

	@Operation(summary = "Delete Activity", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@DeleteMapping(value = "/{activityId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ActivityMessage> delete(@PathVariable("schoolId") String schoolId,
			@Parameter(name = "Activity id", required = true) @PathVariable("activityId") String activityId)
			throws ActivityException {
		activityService.delete(schoolId, activityId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ActivityMessage.DELETE_OK);
	}
}
