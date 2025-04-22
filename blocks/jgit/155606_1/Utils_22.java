
package org.eclipse.jgit.diffmergetool;

public class UserDefinedMergeTool extends UserDefinedDiffTool
		implements IMergeTool {

	protected BooleanOption trustExitCode;

	public UserDefinedMergeTool(final String name
			final String cmd
		super(name
		this.trustExitCode = trustExitCode;
	}

	@Override
	public BooleanOption getTrustExitCode() {
		return trustExitCode;
	}

	@Override
	public String getCommand(boolean withBase) {
		return getCommand();
	}

}
