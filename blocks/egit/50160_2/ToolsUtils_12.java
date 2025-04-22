
package org.eclipse.egit.ui.internal.externaltools;

public class PreDefinedTool extends BaseTool {

	private String path = null;
	private String options = null;

	public PreDefinedTool(String name, String path, String options) {
		super(name);
		this.path = path;
		this.options = options;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getOptions() {
		return options;
	}

	@Override
	public String getCommand() {
		return path + " " + options; //$NON-NLS-1$
	}

}
