package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.JGitText;

public class TooLargeObjectInPackException extends IOException {
	private static final long serialVersionUID = 1L;


	public TooLargeObjectInPackException() {
		super(JGitText.get().receivePackObjectTooLarge);
	}
}
