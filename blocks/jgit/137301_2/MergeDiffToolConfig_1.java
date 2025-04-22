package org.eclipse.jgit.mergetool;

public class MergeDiffTool {
	private final String name;

	private final String cmd;

	private final String path;

	private final boolean trustExitCode;

	protected MergeDiffTool(final String name
			final String path
		this.name = name;
		this.cmd = cmd;
		this.path = path;
		this.trustExitCode = trustExitCode;
	}

	public String getName() {
		return name;
	}

	public String getCmd() {
		return cmd;
	}

	public String getPath() {
		return path;
	}

	public boolean isTrustExitCode() {
		return trustExitCode;
	}
}
