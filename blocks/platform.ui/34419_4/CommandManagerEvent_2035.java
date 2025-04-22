package org.eclipse.ui.commands;

@Deprecated
@SuppressWarnings("all")
public abstract class CommandException extends Exception {

	private static final long serialVersionUID= 1776879459633730964L;
	
	
	private Throwable cause;

	@Deprecated
    public CommandException(String message) {
        super(message);
    }

	@Deprecated
    public CommandException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

	@Override
	@Deprecated
    public Throwable getCause() {
        return cause;
    }
}
