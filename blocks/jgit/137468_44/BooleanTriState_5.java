
package org.eclipse.jgit.internal.diffmergetool;

public interface ExternalDiffTool {

	String getName();

	String getPath();

	String getCommand();

}
