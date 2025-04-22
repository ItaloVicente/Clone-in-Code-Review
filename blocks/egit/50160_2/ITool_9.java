package org.eclipse.egit.ui.internal.externaltools;

public class DiffToolManager extends BaseToolManager {

	private static DiffToolManager instance = null;

	public static synchronized DiffToolManager getInstance() {
		if (DiffToolManager.instance == null) {
			DiffToolManager.instance = new DiffToolManager();
		}
		return DiffToolManager.instance;
	}

	private DiffToolManager() {
		addPreDefinedTool("kdiff3", "kdiff3", "\"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
