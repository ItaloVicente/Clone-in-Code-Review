		if (useInternalMergeTool) {
			if (mergeMode == 0) {
				MergeModeDialog dlg = new MergeModeDialog(getShell(event));
				if (dlg.open() != Window.OK)
					return null;
				input = new GitMergeEditorInput(dlg.getMergeMode(), locations);
			} else {
				MergeInputMode mode = MergeInputMode.fromInteger(mergeMode);
				input = new GitMergeEditorInput(mode, locations);
			}
