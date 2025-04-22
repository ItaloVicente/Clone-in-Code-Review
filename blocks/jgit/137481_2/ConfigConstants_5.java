
package org.eclipse.jgit.diffmergetool;

public class UserDefinedDiffTool implements ITool {

	protected final String name;

	protected String path;

	protected String cmd;

	protected final boolean trustExitCode;

	public UserDefinedDiffTool(final String name
			final String cmd
		this.name = name;
		this.path = path;
		this.cmd = cmd;
		this.trustExitCode = trustExitCode;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getCommand() {
		return cmd;
	}

	@Override
	public boolean isTrustExitCode() {
		return trustExitCode;
	}

}
