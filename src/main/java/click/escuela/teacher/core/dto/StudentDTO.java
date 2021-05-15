package click.escuela.teacher.core.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class StudentDTO {

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "surname")
	private String surname;

	@JsonProperty(value = "document")
	private String document;

	@JsonProperty(value = "gender")
	private String gender;

	@JsonProperty(value = "grade")
	private String grade;

	@JsonProperty(value = "division")
	private String division;

	@JsonProperty(value = "level")
	private String level;

	@JsonProperty(value = "birthday")
	private LocalDate birthday;

	@JsonProperty(value = "cellPhone")
	private String cellPhone;

	@JsonProperty(value = "email")
	private String email;

}