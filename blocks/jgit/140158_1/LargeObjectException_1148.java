
package org.eclipse.jgit.errors;

public class InvalidPatternException extends Exception {
	private static final long serialVersionUID = 1L;

	private final String pattern;

	public InvalidPatternException(String message
		super(message);
		this.pattern = pattern;
	}

	public InvalidPatternException(String message
			Throwable cause) {
		this(message
		initCause(cause);
	}

	public String getPattern() {
		return pattern;
	}

}
