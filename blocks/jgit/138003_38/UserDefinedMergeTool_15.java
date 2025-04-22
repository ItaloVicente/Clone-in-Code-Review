
package org.eclipse.jgit.internal.diffmergetool;

import org.eclipse.jgit.lib.internal.BooleanTriState;

public class PreDefinedMergeTool extends UserDefinedMergeTool {

	private final String parametersWithoutBase;

	public PreDefinedMergeTool(String name
			String parametersWithBase
			BooleanTriState trustExitCode) {
		super(name
		this.parametersWithoutBase = parametersWithoutBase;
	}

	public PreDefinedMergeTool(CommandLineMergeTool tool) {
		this(tool.name()
				tool.getParameters(false)
				tool.isExitCodeTrustable() ? BooleanTriState.TRUE
						: BooleanTriState.FALSE);
	}

	@Override
	public void setTrustExitCode(BooleanTriState trustExitCode) {
		super.setTrustExitCode(trustExitCode);
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
