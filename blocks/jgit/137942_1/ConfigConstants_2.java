
package org.eclipse.jgit.diffmergetool;

public class UserDefinedMergeTool extends UserDefinedDiffTool
		implements IMergeTool {

	protected final BooleanOption trustExitCode;

	public UserDefinedMergeTool(final String name
			final String cmd
		super(name
		this.trustExitCode = trustExitCode;
	}

	@Override
	public boolean isTrustExitCode() {
		return trustExitCode.toBoolean();
	}

	public BooleanOption getTrustExitCode() {
		return trustExitCode;
	}

}
