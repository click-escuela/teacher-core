package click.escuela.teacher.core.exception;

import click.escuela.teacher.core.enumerator.TeacherMessage;

public class TeacherException extends TransactionException{
	
	private static final long serialVersionUID = -2180809463240249594L;

	public TeacherException(TeacherMessage teacherMessage) {
		super(teacherMessage.getCode(), teacherMessage.getDescription());
	}
}
