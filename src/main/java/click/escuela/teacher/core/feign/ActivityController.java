package click.escuela.teacher.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.enumerator.ActivityMessage;
import click.escuela.teacher.core.exception.TransactionException;

@FeignClient(name = "activities", url = "localhost:8092")
public interface ActivityController {

	// ActivityController
	@PostMapping(value = "/click-escuela/school/{schoolId}/activity")
	public ResponseEntity<ActivityMessage> create(@PathVariable("schoolId") String schoolId,
			@RequestBody @Validated ActivityApi activityApi) throws TransactionException;
}
