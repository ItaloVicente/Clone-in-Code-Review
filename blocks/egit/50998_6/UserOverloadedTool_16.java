
package org.eclipse.egit.ui.internal.externaltools;

public class UserDefinedTool extends BaseTool {

	private String cmd = null;

	public UserDefinedTool(String name, String cmd) {
		super(name);
		this.cmd = cmd;
	}

	@Override
	public String getCommand(int... optionsNr) {
		return cmd;
	}
}
