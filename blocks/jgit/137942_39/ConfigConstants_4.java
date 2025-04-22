
package org.eclipse.jgit.internal.diffmergetool;

import org.eclipse.jgit.lib.internal.BooleanTriState;

public class UserDefinedMergeTool extends UserDefinedDiffTool
		implements ExternalMergeTool {

	private final BooleanTriState trustExitCode;

	public UserDefinedMergeTool(String name
			BooleanTriState trustExitCode) {
		super(name
		this.trustExitCode = trustExitCode;
	}

	@Override
	public boolean isTrustExitCode() {
		return trustExitCode == BooleanTriState.TRUE;
	}

	public BooleanTriState getTrustExitCode() {
		return trustExitCode;
	}

}
