			input = new GitMergeEditorInput(MergeInputMode.STAGE_2, locations);
		}
		if (useInternalMergeTool) {
			openMergeToolInternal(input);
		} else {
			openMergeToolExternal(input);
