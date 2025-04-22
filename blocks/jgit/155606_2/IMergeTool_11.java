
package org.eclipse.jgit.diffmergetool;

public interface IDiffTool {

	abstract public String getName();

	abstract String getPath();

	abstract public String getCommand();

	abstract public boolean isAvailable();

}
