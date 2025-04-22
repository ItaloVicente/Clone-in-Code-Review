
package org.eclipse.jgit.errors;


public class RevisionSyntaxException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	private final String revstr;

	public RevisionSyntaxException(String revstr) {
		this.revstr = revstr;
	}

	public RevisionSyntaxException(String message
		super(message);
		this.revstr = revstr;
	}

	@Override
	public String toString() {
	}
}
