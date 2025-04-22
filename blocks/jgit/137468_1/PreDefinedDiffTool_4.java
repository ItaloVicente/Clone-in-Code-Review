
package org.eclipse.jgit.diffmergetool;

public interface ITool {

	abstract public String getName();

	abstract String getPath();

	abstract public String getCommand();

	public boolean isTrustExitCode();

}
