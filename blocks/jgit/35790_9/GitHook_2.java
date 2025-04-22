package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.Hook;

public class HookFailureException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	private final Hook hook;

	private final int returnCode;

	public HookFailureException(String message
		super(message);
		this.hook = hook;
		this.returnCode = returnCode;
	}

	public Hook getHook() {
		return hook;
	}

	public int getReturnCode() {
		return returnCode;
	}

	@Override
	public String getMessage() {
		return MessageFormat.format(JGitText.get().commandRejectedByHook
				hook.getName()
	}
}
