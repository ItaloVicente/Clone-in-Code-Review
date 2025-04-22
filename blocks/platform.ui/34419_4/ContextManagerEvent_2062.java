package org.eclipse.ui.contexts;

@Deprecated
public abstract class ContextException extends Exception {
	
	private static final long serialVersionUID= -5143404124388080211L;
	
	
	private Throwable cause;

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }
    
    @Override
	public Throwable getCause() {
        return cause;
    }

}
