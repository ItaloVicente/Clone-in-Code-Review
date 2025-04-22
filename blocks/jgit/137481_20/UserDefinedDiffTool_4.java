
package org.eclipse.jgit.diffmergetool;

@SuppressWarnings("nls")
public class PreDefinedDiffTool extends UserDefinedDiffTool {

	public PreDefinedDiffTool(String name
		super(name
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getCommand() {
		return path + " " + super.getCommand();
	}

}
