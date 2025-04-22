package org.eclipse.egit.core.internal.signing;

public class GpgConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GpgConfigurationException() {
		super();
	}

	public GpgConfigurationException(String message) {
		super(message);
	}

	public GpgConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
