package click.escuela.teacher.core.enumerator;

public enum TeacherMessage {

	GET_ERROR("GET_ERROR", "No se pudo encontrar el profesor");

	private String code;
	private String description;

	TeacherMessage(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
