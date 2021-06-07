package click.escuela.teacher.core.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActivityDTO {
	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "school_id")
	private Integer schoolId;

	@JsonProperty(value = "course_id")
	private UUID courseId;

	@JsonProperty(value = "due_date")
	private LocalDate dueDate;

	@JsonProperty(value = "type")
	private String type;

	@JsonProperty(value = "subject")
	private String subject;

	@JsonProperty(value = "description")
	private String description;
}
