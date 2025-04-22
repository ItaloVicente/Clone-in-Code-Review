
package org.eclipse.jgit.internal.diffmergetool;

import java.util.Optional;

public class PreDefinedMergeTool extends UserDefinedMergeTool {

	private final String parametersWithoutBase;

	public PreDefinedMergeTool(String name
			String parametersWithBase
			Optional<Boolean> trustExitCode) {
		super(name
		this.parametersWithoutBase = parametersWithoutBase;
	}

	public PreDefinedMergeTool(CommandLineMergeTool tool) {
		this(tool.name()
				tool.getParameters(false)
				Optional.of(Boolean.valueOf(tool.isExitCodeTrustable())));
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTrustExitCode(Optional<Boolean> trustExitCode) {
		this.trustExitCode = trustExitCode;
	}

	@Override
	public String getCommand() {
		return getCommand(true);
	}

	@Override
	public String getCommand(boolean withBase) {
				+ (withBase ? super.getCommand() : parametersWithoutBase);
	}

}
