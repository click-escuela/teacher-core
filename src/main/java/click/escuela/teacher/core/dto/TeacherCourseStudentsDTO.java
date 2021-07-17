package click.escuela.teacher.core.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import click.escuela.teacher.core.enumerator.DocumentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherCourseStudentsDTO {

	@JsonProperty(value = "id")
	private String id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "surname")
	private String surname;

	@JsonProperty(value = "documentType")
	private DocumentType documentType;

	@JsonProperty(value = "document")
	private String document;

	@JsonProperty(value = "birthday")
	private LocalDate birthday;

	@JsonProperty(value = "adress")
	private AdressDTO adress;

	@JsonProperty(value = "cellPhone")
	private String cellPhone;

	@JsonProperty(value = "email")
	private String email;
	
	@JsonProperty(value = "school_id")
	private Integer schoolId;
	
	@JsonProperty(value = "courses")
	private List<CourseStudentsDTO> courses;
}