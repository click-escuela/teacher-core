package click.escuela.teacher.core.exception;

import click.escuela.teacher.core.enumerator.GradeMessage;

public class GradeException extends TransactionException {

	private static final long serialVersionUID = 1L;

	public GradeException(GradeMessage gradeMessage) {
		super(gradeMessage.getCode(), gradeMessage.getDescription());
	}

}
