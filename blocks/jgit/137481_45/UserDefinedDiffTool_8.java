
package org.eclipse.jgit.internal.diffmergetool;

public class PreDefinedDiffTool extends UserDefinedDiffTool {

	public PreDefinedDiffTool(String name
		super(name
	}

	public PreDefinedDiffTool(CommandLineDiffTool tool) {
		this(tool.name()
	}

	@Override
	public void setPath(String path) {
			}
			}
		}
		super.setPath(path);
	}

	@Override
	public String getCommand() {
	}

}
