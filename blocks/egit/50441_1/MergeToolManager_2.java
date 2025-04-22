		addPreDefinedTool("deltawalker", "DeltaWalker", //$NON-NLS-1$ //$NON-NLS-2$
				"\"$LOCAL\" \"$REMOTE\" \"$BASE\" -merged=\"$(pwd)/$MERGED\"", //$NON-NLS-1$
				"\"$LOCAL\" \"$REMOTE\" -merged=\"$(pwd)/$MERGED\""); //$NON-NLS-1$

		addPreDefinedTool("diffmerge", "diffmerge", //$NON-NLS-1$ //$NON-NLS-2$
				"--merge --result=\"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\"", //$NON-NLS-1$
				"--merge --result=\"$MERGED\" \"$LOCAL\" \"$REMOTE\""); //$NON-NLS-1$

		addPreDefinedTool("diffuse", "diffuse", //$NON-NLS-1$ //$NON-NLS-2$
				"\"$LOCAL\" \"$MERGED\" \"$REMOTE\" \"$BASE\"", //$NON-NLS-1$
				"\"$LOCAL\" \"$MERGED\" \"$REMOTE\""); //$NON-NLS-1$

		addPreDefinedTool("ecmerge", "ecmerge", //$NON-NLS-1$ //$NON-NLS-2$
				"\"$BASE\" \"$LOCAL\" \"$REMOTE\" --default --mode=merge3 --to=\"$MERGED\"", //$NON-NLS-1$
				"\"$LOCAL\" \"$REMOTE\" --default --mode=merge2 --to=\"$MERGED\""); //$NON-NLS-1$

		addPreDefinedTool("emerge", "emacs", //$NON-NLS-1$ //$NON-NLS-2$
				"-f emerge-files-with-ancestor-command \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$(basename \"$MERGED\")\"", //$NON-NLS-1$
				"-f emerge-files-command \"$LOCAL\" \"$REMOTE\" \"$(basename \"$MERGED\")\""); //$NON-NLS-1$

		String vimdifOptionsWithBase = "-f -d -c 'wincmd J' \"$MERGED\" \"$LOCAL\" \"$BASE\" \"$REMOTE\""; //$NON-NLS-1$
		String vimdifOptionsWithOutBase = "-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\""; //$NON-NLS-1$
		addPreDefinedTool("gvimdiff", "gvim", //$NON-NLS-1$ //$NON-NLS-2$
				vimdifOptionsWithBase, vimdifOptionsWithOutBase);

		String vimdif2Options = "-f -d -c 'wincmd l' \"$LOCAL\" \"$MERGED\" \"$REMOTE\""; //$NON-NLS-1$
		addPreDefinedTool("gvimdiff2", "gvim", //$NON-NLS-1$ //$NON-NLS-2$
				vimdif2Options);

		String vimdif3OptionsWithBase = "-f -d -c 'hid | hid | hid' \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$MERGED\""; //$NON-NLS-1$
		String vimdif3OptionsWithOutBase = "-f -d -c 'hid | hid' \"$LOCAL\" \"$REMOTE\" \"$MERGED\""; //$NON-NLS-1$
		addPreDefinedTool("gvimdiff3", "gvim", //$NON-NLS-1$ //$NON-NLS-2$
				vimdif3OptionsWithBase, vimdif3OptionsWithOutBase);

