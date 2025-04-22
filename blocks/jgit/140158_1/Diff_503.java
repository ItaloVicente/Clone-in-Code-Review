
package org.eclipse.jgit.pgm;

public class Die extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private boolean aborted;

	public Die(String why) {
		super(why);
	}

	public Die(String why
		super(why
	}

	public Die(boolean aborted) {
		this(aborted
	}

	public Die(boolean aborted
		super(cause != null ? cause.getMessage() : null
		this.aborted = aborted;
	}

	public boolean isAborted() {
		return aborted;
	}
}
