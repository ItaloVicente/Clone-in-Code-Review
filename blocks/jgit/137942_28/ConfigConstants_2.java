
package org.eclipse.jgit.diffmergetool;

public class UserDefinedMergeTool extends UserDefinedDiffTool
		implements ExternalMergeTool {

	private final BooleanOption trustExitCode;

	public UserDefinedMergeTool(String name
			BooleanOption trustExitCode) {
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
