
package org.eclipse.jgit.diffmergetool;

public class PreDefinedDiffTool extends UserDefinedDiffTool {

	public PreDefinedDiffTool(final String name
			final String parameters) {
		super(name
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setParameters(String parameters) {
		this.cmd = parameters;
	}

	@Override
	public String getCommand() {
	}

}
