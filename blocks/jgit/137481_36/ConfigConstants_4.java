
package org.eclipse.jgit.internal.diffmergetool;

public class UserDefinedDiffTool implements ExternalDiffTool {

	private final String name;

	protected String path;

	private final String cmd;

	public UserDefinedDiffTool(String name
		this.name = name;
		this.path = path;
		this.cmd = cmd;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getCommand() {
		return cmd;
	}

}
