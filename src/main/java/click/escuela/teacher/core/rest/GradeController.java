package click.escuela.teacher.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.enumerator.GradeEnum;
import click.escuela.teacher.core.exception.TransactionException;
import click.escuela.teacher.core.service.impl.GradeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/school/{schoolId}/grade")
public class GradeController {

	@Autowired
	private GradeServiceImpl gradeService;

	@Operation(summary = "Create grade", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")) })
	@PostMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<GradeEnum> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated GradeApi gradeApi) throws TransactionException {
		gradeService.create(schoolId, gradeApi);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(GradeEnum.CREATE_OK);
	}
}
