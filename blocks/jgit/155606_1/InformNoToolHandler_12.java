
package org.eclipse.jgit.diffmergetool;

public interface IMergeTool extends IDiffTool {

	public BooleanOption getTrustExitCode();

	abstract public String getCommand(boolean withBase);

}
