
package org.eclipse.jgit.diffmergetool;

@SuppressWarnings("nls")
public class PreDefinedMergeTool extends UserDefinedMergeTool {

	protected String parametersWithoutBase;

	public PreDefinedMergeTool(final String name
			final String parametersWithBase
			final BooleanOption trustExitCode) {
		super(name
		this.parametersWithoutBase = parametersWithoutBase;
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
		return Utils.quotePath(path) + " "
				+ (withBase ? super.getCommand() : parametersWithoutBase);
	}

}
