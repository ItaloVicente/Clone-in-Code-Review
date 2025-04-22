
		addPreDefinedTool("araxis", "compare", //$NON-NLS-1$ //$NON-NLS-2$
				"-wait -merge -3 -a1 \"$BASE\" \"$LOCAL\" \"$REMOTE\" \"$MERGED\"", //$NON-NLS-1$
				"-wait -2 \"$LOCAL\" \"$REMOTE\" \"$MERGED\""); //$NON-NLS-1$

		String bcOptionsWithBase = "\"$LOCAL\" \"$REMOTE\" \"$BASE\" -mergeoutput=\"$MERGED\""; //$NON-NLS-1$
		String bcOptionsWithOutBase = "\"$LOCAL\" \"$REMOTE\" -mergeoutput=\"$MERGED\""; //$NON-NLS-1$
		addPreDefinedTool("bc", "bcomp", //$NON-NLS-1$ //$NON-NLS-2$
				bcOptionsWithBase, bcOptionsWithOutBase);

		addPreDefinedTool("bc3", "bcompare", //$NON-NLS-1$ //$NON-NLS-2$
				bcOptionsWithBase, bcOptionsWithOutBase);

		addPreDefinedTool("codecompare", "CodeCompare", //$NON-NLS-1$ //$NON-NLS-2$
				"-MF=\"$LOCAL\" -TF=\"$REMOTE\" -BF=\"$BASE\" -RF=\"$MERGED\"", //$NON-NLS-1$
				"-MF=\"$LOCAL\" -TF=\"$REMOTE\" -RF=\"$MERGED\""); //$NON-NLS-1$

		    # Adding $(pwd)/ in front of $MERGED should not be necessary.
			# However without it, DeltaWalker (at least v1.9.8 on Windows)
			# crashes with a JRE exception.  The DeltaWalker user manual,
			# shows $(pwd)/ whenever the '-merged' options is given.
			# Adding it here seems to work around the problem.
			if $base_present
			then
				"$merge_tool_path" "$LOCAL" "$REMOTE" "$BASE" -merged="$(pwd)/$MERGED"
			else
				"$merge_tool_path" "$LOCAL" "$REMOTE" -merged="$(pwd)/$MERGED"
			fi >/dev/null 2>&1
			@formatter:on
