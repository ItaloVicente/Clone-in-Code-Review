package org.eclipse.egit.gitflow;

public class WrongGitFlowStateException extends Exception {

	private static final long serialVersionUID = 3091117695421525438L;

	public WrongGitFlowStateException(Exception e) {
		super(e);
	}

	public WrongGitFlowStateException(String string) {
		super(string);
	}
}
