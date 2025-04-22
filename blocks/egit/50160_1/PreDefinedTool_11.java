
package org.eclipse.egit.ui.internal.externaltools;

public class MergeToolManager extends BaseToolManager {

	private static MergeToolManager instance = null;

	public static synchronized MergeToolManager getInstance() {
		if (MergeToolManager.instance == null) {
			MergeToolManager.instance = new MergeToolManager();
		}
		return MergeToolManager.instance;
	}

	private MergeToolManager() {
		addPreDefinedTool("kdiff3", "kdiff3", //$NON-NLS-1$ //$NON-NLS-2$
				"\"$BASE\" \"$LOCAL\" \"$REMOTE\" -o \"$MERGED\""); //$NON-NLS-1$
	}

}
