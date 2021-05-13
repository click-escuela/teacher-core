package click.escuela.teacher.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import click.escuela.teacher.core.api.GradeApi;
import click.escuela.teacher.core.dto.StudentDTO;
import click.escuela.teacher.core.exception.TransactionException;

@FeignClient(name = "teacher", url = "localhost:8081")
public interface GradeController {

	// GradeController
	@PostMapping(value = "/click-escuela/school/{schoolId}/grade")
	public StudentDTO getById(@RequestBody @Validated GradeApi gradeApi) throws TransactionException;

}
