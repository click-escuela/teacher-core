package click.escuela.teacher.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class GradeDTO {

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "subject")
	private String subject;

	@JsonProperty(value = "type")
	private String type;

	@JsonProperty(value = "number")
	private Integer number;
	
	@JsonProperty(value = "studentId")
	private String studentId;
}
