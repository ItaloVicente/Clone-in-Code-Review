
package org.eclipse.jgit.errors;

public class StopWalkException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public static final StopWalkException INSTANCE = new StopWalkException();

	private StopWalkException() {
	}
}
