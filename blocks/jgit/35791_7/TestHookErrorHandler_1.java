package org.eclipse.jgit.util;

import org.eclipse.jgit.api.HookFailureHandler;

public class TestHookErrorHandler implements HookFailureHandler {

	Hook hook;

	ProcessResult processResult;

	String errorStreamContent;

	int invocationCount;

	@Override
	public void hookExecutionFailed(Hook aHook
			ProcessResult aProcessResult
		invocationCount++;
		this.hook = aHook;
		this.processResult = aProcessResult;
		this.errorStreamContent = anErrorStreamContent;
	}
}
