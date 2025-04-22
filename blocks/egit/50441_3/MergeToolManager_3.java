				"--auto --L1 \"$MERGED (Base)\" --L2 \"$MERGED (Local)\" --L3 \"$MERGED (Remote)\" -o \"$MERGED\" \"$BASE\" \"$LOCAL\" \"$REMOTE\"", //$NON-NLS-1$
				"--auto --L1 \"$MERGED (Local)\" --L2 \"$MERGED (Remote)\" -o \"$MERGED\" \"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$

		addPreDefinedTool("kompare", "kompare", "\"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ */

		addPreDefinedTool("meld", "meld", //$NON-NLS-1$ //$NON-NLS-2$
				"--output \"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\"", //$NON-NLS-1$
				"--output \"$MERGED\" \"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$

		addPreDefinedTool("opendiff", "opendiff", //$NON-NLS-1$ //$NON-NLS-2$
				"\"$LOCAL\" \"$REMOTE\" -ancestor \"$BASE\" -merge \"$MERGED\"", //$NON-NLS-1$
				"\"$LOCAL\" \"$REMOTE\" -merge \"$MERGED\""); //$NON-NLS-1$

		addPreDefinedTool("p4merge", "p4merge", //$NON-NLS-1$ //$NON-NLS-2$
				"\"$BASE\" \"$REMOTE\" \"$LOCAL\" \"$MERGED\"", //$NON-NLS-1$
				"\"$LOCAL\" \"$REMOTE\" \"$LOCAL\" \"$MERGED\""); //$NON-NLS-1$

		addPreDefinedTool("tkdiff", "tkdiff", //$NON-NLS-1$ //$NON-NLS-2$
				"-a \"$BASE\" -o \"$MERGED\" \"$LOCAL\" \"$REMOTE\"", //$NON-NLS-1$
				"-o \"$MERGED\" \"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$

		addPreDefinedTool("tortoisemerge", "tortoisegitmerge", //$NON-NLS-1$ //$NON-NLS-2$
				"-base \"$BASE\" -mine \"$LOCAL\" -theirs \"$REMOTE\" -merged \"$MERGED\""); //$NON-NLS-1$

		addPreDefinedTool("vimdiff", "vim", //$NON-NLS-1$ //$NON-NLS-2$
				vimdifOptionsWithBase, vimdifOptionsWithOutBase);

		addPreDefinedTool("vimdiff2", "vim", //$NON-NLS-1$ //$NON-NLS-2$
				vimdif2Options);

		addPreDefinedTool("vimdiff3", "vim", //$NON-NLS-1$ //$NON-NLS-2$
				vimdif3OptionsWithBase, vimdif3OptionsWithOutBase);

		addPreDefinedTool("xxdiff", "xxdiff", //$NON-NLS-1$ //$NON-NLS-2$
				"-X --show-merged-pane -R 'Accel.SaveAsMerged: \"Ctrl-S\"' -R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl-G\"' --merged-file \"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\"", //$NON-NLS-1$
				"-X $extra -R 'Accel.SaveAsMerged: \"Ctrl-S\"' -R 'Accel.Search: \"Ctrl+F\"' -R 'Accel.SearchForward: \"Ctrl-G\"' --merged-file \"$MERGED\" \"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$
