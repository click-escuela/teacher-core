package click.escuela.teacher.core.exception;

import click.escuela.teacher.core.enumerator.ActivityMessage;

public class ActivityException extends TransactionException {

	private static final long serialVersionUID = 1L;
	
	public ActivityException(ActivityMessage activityMessage) {
		super(activityMessage.getCode() ,activityMessage.getDescription());
	}

}
