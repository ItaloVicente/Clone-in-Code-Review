package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class AbortedByHookException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	private final String hookName;

	private final int returnCode;

	public AbortedByHookException(String message
			int returnCode) {
		super(message);
		this.hookName = hookName;
		this.returnCode = returnCode;
	}

	public String getHookName() {
		return hookName;
	}

	public int getReturnCode() {
		return returnCode;
	}

	@Override
	public String getMessage() {
		return MessageFormat.format(JGitText.get().commandRejectedByHook
				hookName
	}
}
