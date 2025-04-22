package net.spy.memcached.tapmessage;

public class FieldDoesNotExistException extends RuntimeException{
	private static final long serialVersionUID = -9031043272617359144L;

	public FieldDoesNotExistException() {
        super();
    }
	
	public FieldDoesNotExistException(String message) {
        super(message);
    }
}
