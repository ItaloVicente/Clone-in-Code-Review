
package org.eclipse.jgit.diffmergetool;

public class PreDefinedMergeTool extends UserDefinedMergeTool {

	public PreDefinedMergeTool(final String name
			final String parameters
		super(name
	}

	public void setPath(String path) {
			}
			}
		}
		this.path = path;
	}

	public void setParameters(String parameters) {
		this.cmd = parameters;
	}

	public void setTrustExitCode(BooleanOption trustExitCode) {
		this.trustExitCode = trustExitCode;
	}

	@Override
	public String getCommand() {
	}

}
