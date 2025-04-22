
package org.eclipse.egit.ui.internal.externaltools;

public class UserOverloadedTool extends PreDefinedTool {

	public UserOverloadedTool(ITool tool, String path) {
		super(tool.getName(), path, tool.getOptions());
	}

}
