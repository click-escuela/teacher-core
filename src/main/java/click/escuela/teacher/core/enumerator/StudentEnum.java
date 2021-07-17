package click.escuela.teacher.core.enumerator;

public enum StudentEnum {

	GET_ERROR("GET_ERROR", "El estudiante que se busca no existe");

	private String code;
	private String description;
	
	StudentEnum(String code, String description) {
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
