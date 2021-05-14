package click.escuela.teacher.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.enumerator.GradeEnum;
import click.escuela.teacher.core.exception.TransactionException;

@FeignClient(name = "grades", url = "localhost:8091")
public interface GradeController {

	// GradeController
	@PostMapping(value = "/click-escuela/school/{schoolId}/grade")
	public ResponseEntity<GradeEnum> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated GradeApi gradeApi) throws TransactionException;

}
