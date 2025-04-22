
package org.eclipse.jgit.diffmergetool;

public class PreDefinedMergeTool extends UserDefinedMergeTool {

	private final String parametersWithoutBase;

	public PreDefinedMergeTool(String name
			String parametersWithBase
			BooleanOption trustExitCode) {
		super(name
		this.parametersWithoutBase = parametersWithoutBase;
	}

	public PreDefinedMergeTool(CommandLineMergeTool tool) {
		this(tool.name()
				tool.getParameters(false)
				BooleanOption.toConfigured(tool.isExitCodeTrustable()));
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTrustExitCode(BooleanOption trustExitCode) {
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
