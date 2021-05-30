package click.escuela.teacher.core.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
@Schema(description = "Grade Api")
@SuperBuilder
public class GradeApi {
	
	@NotBlank(message = "Student cannot be empty")
	@Size(max = 50, message = "Student must be less than 50 characters")
	@JsonProperty(value = "studentId", required = true)
	private String studentId;

	@NotBlank(message = "Name cannot be empty")
	@Size(max = 50, message = "Name must be less than 50 characters")
	@JsonProperty(value = "name", required = true)
	private String name;

	@NotBlank(message = "Course cannot be empty")
	@Size(max = 50, message = "Course must be less than 50 characters")
	@JsonProperty(value = "courseId", required = true)
	private String courseId;

	@NotBlank(message = "Subject cannot be empty")
	@Size(max = 50, message = "Subject must be less than 50 characters")
	@JsonProperty(value = "subject", required = true)
	private String subject;

	@ApiModelProperty(dataType = "enum", example = "EXAM,PRACTICAL_WORK,HOMEWORK")
	@NotBlank(message = "Type cannot be empty")
	@Size(max = 50, message = "Type must be less than 50 characters")
	@JsonProperty(value = "type", required = true)
	private String type;

	@NotNull(message = "Number cannot be null")
	@JsonProperty(value = "number", required = true)
	private Integer number;

}
