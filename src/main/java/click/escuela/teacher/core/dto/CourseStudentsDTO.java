package click.escuela.teacher.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseStudentsDTO {

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "year")
	private Integer year;

	@JsonProperty(value = "division")
	private String division;

	@JsonProperty(value = "countStudent")
	private Integer countStudent;

	@JsonProperty(value = "students")
	private List<StudentDTO> students;

}
