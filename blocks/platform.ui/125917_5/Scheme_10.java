package org.eclipse.urischeme.internal.registration;

@SuppressWarnings("javadoc")
public class Scheme {

	private String scheme;
	private String description;

	public Scheme(String scheme, String description) {
		super();
		this.scheme = scheme;
		this.description = description;
	}

	public String getScheme() {
		return scheme;
	}

	public String getDescription() {
		return description;
	}
}
