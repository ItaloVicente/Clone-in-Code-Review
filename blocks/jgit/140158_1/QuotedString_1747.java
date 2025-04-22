package org.eclipse.jgit.util;

public class ProcessResult {
	public static enum Status {
		OK

		NOT_PRESENT

		NOT_SUPPORTED;
	}

	private final int exitCode;

	private final Status status;

	public ProcessResult(Status status) {
		this(-1
	}

	public ProcessResult(int exitCode
		this.exitCode = exitCode;
		this.status = status;
	}

	public int getExitCode() {
		return exitCode;
	}

	public Status getStatus() {
		return status;
	}

	public boolean isExecutedWithError() {
		return getStatus() == ProcessResult.Status.OK && getExitCode() != 0;
	}
}
