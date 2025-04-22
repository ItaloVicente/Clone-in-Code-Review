package org.eclipse.jgit.api.errors;

public class RefNotAdvertisedException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RefNotAdvertisedException(String message) {
		super(message);
	}
}
