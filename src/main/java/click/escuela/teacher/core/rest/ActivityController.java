package click.escuela.teacher.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.exception.ActivityException;
import click.escuela.teacher.core.service.impl.ActivityServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/school/{schoolId}/activity")
public class ActivityController {

	@Autowired
	private ActivityServiceImpl activityService;

	@Operation(summary = "Create Activity", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ActivityMessage> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated ActivityApi activityApi) throws ActivityException {
		activityService.create(schoolId, activityApi);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(ActivityMessage.CREATE_OK);
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
