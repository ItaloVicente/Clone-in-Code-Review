
package org.eclipse.jgit.internal.diffmergetool;

import java.util.Optional;

public class UserDefinedMergeTool extends UserDefinedDiffTool
		implements ExternalMergeTool {

	private final Optional<Boolean> trustExitCode;

	public UserDefinedMergeTool(String name
			Optional<Boolean> trustExitCode) {
		super(name
		this.trustExitCode = trustExitCode;
	}

	@Override
	public boolean isTrustExitCode() {
		return trustExitCode.get().booleanValue();
	}

	public Optional<Boolean> getTrustExitCode() {
		return trustExitCode;
	}

}
