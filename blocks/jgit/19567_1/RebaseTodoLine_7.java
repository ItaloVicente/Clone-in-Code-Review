package org.eclipse.jgit.errors;

public class IllegalTodoFileModification extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalTodoFileModification(final String msg) {
		super(msg);
	}
}
