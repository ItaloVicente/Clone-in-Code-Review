
package org.eclipse.jgit.internal.diffmergetool;

import org.eclipse.jgit.lib.internal.BooleanTriState;

public interface ExternalMergeTool extends ExternalDiffTool {

	BooleanTriState getTrustExitCode();

	String getCommand(boolean withBase);

}
