package click.escuela.teacher.core.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import click.escuela.teacher.core.api.ActivityApi;
import click.escuela.teacher.core.dto.ActivityDTO;

import click.escuela.teacher.core.exception.ActivityException;
import io.swagger.v3.oas.annotations.Parameter;

@FeignClient(name = "activities")
public interface ActivityController {

	// ActivityController
	@PostMapping(value = "/school/{schoolId}/activity")
	public String create(@PathVariable("schoolId") String schoolId, @RequestBody @Validated ActivityApi activityApi)
			throws ActivityException;

	@PutMapping(value = "/school/{schoolId}/activity")
	public String update(@PathVariable("schoolId") String schoolId, @RequestBody @Validated ActivityApi activityApi)
			throws ActivityException;


	@DeleteMapping(value = "/school/{schoolId}/activity/{activityId}")
	public String delete(@PathVariable("schoolId") String schoolId,

			@Parameter(name = "Activity id", required = true) @PathVariable("activityId") String activityId)
			throws ActivityException;

	@GetMapping(value = "/school/{schoolId}/activity/student/{studentId}")
	public List<ActivityDTO> getByStudentId(@PathVariable("schoolId") String schoolId,
			@PathVariable("studentId") String studentId);

	@GetMapping(value = "/school/{schoolId}/activity/course/{courseId}")
	public List<ActivityDTO> getByCourseId(@PathVariable("schoolId") String schoolId,
			@PathVariable("courseId") String courseId);

	@GetMapping(value = "/school/{schoolId}/activity")
	public List<ActivityDTO> getBySchoolId(@PathVariable("schoolId") String schoolId);

	@GetMapping(value = "/school/{schoolId}/activity/{activityId}")
	public ActivityDTO getById(@PathVariable("schoolId") String schoolId, @PathVariable("activityId") String activityId)
			throws ActivityException;
}