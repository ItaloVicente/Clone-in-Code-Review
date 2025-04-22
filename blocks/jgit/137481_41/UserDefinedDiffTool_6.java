
package org.eclipse.jgit.internal.diffmergetool;

@SuppressWarnings("nls")
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
		setPath(path);
	}

	@Override
	public String getCommand() {
		return getPath() + " " + super.getCommand();
	}

}
