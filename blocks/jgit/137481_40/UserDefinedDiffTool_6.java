
package org.eclipse.jgit.internal.diffmergetool;

@SuppressWarnings("nls")
public class PreDefinedDiffTool extends UserDefinedDiffTool {

	public PreDefinedDiffTool(String name
		super(name
	}

	public PreDefinedDiffTool(CommandLineDiffTool tool) {
		this(tool.name()
	}

	public void setPath(String path) {
			}
			}
		}
		this.path = path;
	}

	@Override
	public String getCommand() {
		return path + " " + super.getCommand();
	}

}
