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

		String optionsPostFixDefault = "\"$LOCAL\" \"$REMOTE\""; //$NON-NLS-1$
		addPreDefinedTool("araxis", "compare", //$NON-NLS-1$ //$NON-NLS-2$
				"-wait -2 " + optionsPostFixDefault ); //$NON-NLS-1$

		addPreDefinedTool("bc", "bcomp", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

		addPreDefinedTool("bc3", "bcompare", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

		addPreDefinedTool("codecompare", "CodeCompare", //$NON-NLS-1$ //$NON-NLS-2$
				optionsPostFixDefault);

		addPreDefinedTool("deltawalker", "DeltaWalker", //$NON-NLS-1$ //$NON-NLS-2$
				optionsPostFixDefault);

		addPreDefinedTool("diffmerge", "diffmerge", //$NON-NLS-1$ //$NON-NLS-2$
				optionsPostFixDefault);

		addPreDefinedTool("diffuse", "diffuse", //$NON-NLS-1$ //$NON-NLS-2$
				optionsPostFixDefault);

		addPreDefinedTool("ecmerge", "ecmerge", //$NON-NLS-1$ //$NON-NLS-2$
				"--default --mode=diff2 " + optionsPostFixDefault); //$NON-NLS-1$

		addPreDefinedTool("emerge", "emacs", //$NON-NLS-1$ //$NON-NLS-2$
				"-f emerge-files-command " + optionsPostFixDefault); //$NON-NLS-1$

		String vimOptionsPrefix = "-R -f -d -c 'wincmd l' -c 'cd $GIT_PREFIX'"; //$NON-NLS-1$
		addPreDefinedTool("gvimdiff", "gvim", //$NON-NLS-1$ //$NON-NLS-2$
				vimOptionsPrefix + optionsPostFixDefault);

		addPreDefinedTool("gvimdiff2", "gvim", //$NON-NLS-1$ //$NON-NLS-2$
				vimOptionsPrefix + optionsPostFixDefault);

		addPreDefinedTool("gvimdiff3", "gvim", //$NON-NLS-1$ //$NON-NLS-2$
				vimOptionsPrefix + optionsPostFixDefault);

		addPreDefinedTool("kdiff3", "kdiff3", //$NON-NLS-1$ //$NON-NLS-2$
				"--L1 \"$MERGED (A)\" --L2 \"$MERGED (B)\" " //$NON-NLS-1$
						+ optionsPostFixDefault);

		addPreDefinedTool("kompare", "kompare", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

		addPreDefinedTool("meld", "meld", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

		addPreDefinedTool("opendiff", "opendiff", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

		addPreDefinedTool("p4merge", "p4merge", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

		addPreDefinedTool("tkdiff", "tkdiff", optionsPostFixDefault); //$NON-NLS-1$ //$NON-NLS-2$

				optionsPostFixDefault); //$NON-NLS-1$ */

		addPreDefinedTool("vimdiff", "vim", //$NON-NLS-1$ //$NON-NLS-2$
				vimOptionsPrefix + optionsPostFixDefault);

		addPreDefinedTool("vimdiff2", "vim", //$NON-NLS-1$ //$NON-NLS-2$
				vimOptionsPrefix + optionsPostFixDefault);

		addPreDefinedTool("vimdiff3", "vim", //$NON-NLS-1$ //$NON-NLS-2$
				vimOptionsPrefix + optionsPostFixDefault);

		addPreDefinedTool("xxdiff", "xxdiff", //$NON-NLS-1$ //$NON-NLS-2$
				"-R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl-G\"' " //$NON-NLS-1$
						+ optionsPostFixDefault);
	}

}
