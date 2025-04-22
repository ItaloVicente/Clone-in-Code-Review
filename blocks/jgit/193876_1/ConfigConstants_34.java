
package org.eclipse.jgit.internal.diffmergetool;

import org.eclipse.jgit.lib.internal.BooleanTriState;

public class UserDefinedMergeTool extends UserDefinedDiffTool
		implements ExternalMergeTool {

	private BooleanTriState trustExitCode;

	public UserDefinedMergeTool(String name
			BooleanTriState trustExitCode) {
		super(name
		this.trustExitCode = trustExitCode;
	}
	@Override
	public BooleanTriState getTrustExitCode() {
		return trustExitCode;
	}

	protected void setTrustExitCode(BooleanTriState trustExitCode) {
		this.trustExitCode = trustExitCode;
	}

	@Override
	public String getCommand(boolean withBase) {
		return getCommand();
	}
}
