
package org.eclipse.jgit.diffmergetool;

public class UserDefinedDiffTool implements IDiffTool {

	private boolean available = false;

	private final String name;

	protected String path;

	private String cmd;

	public UserDefinedDiffTool(final String name
			final String cmd) {
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

	@Override
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
