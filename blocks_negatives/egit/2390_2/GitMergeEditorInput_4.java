		CompareConfiguration config = getCompareConfiguration();
		config.setRightLabel(NLS.bind(LABELPATTERN, rightCommit
				.getShortMessage(), rightCommit.name()));

		if (!useWorkspace)
			config.setLeftLabel(NLS.bind(LABELPATTERN, headCommit
					.getShortMessage(), headCommit.name()));
		else
			config.setLeftLabel(UIText.GitMergeEditorInput_WorkspaceHeader);

		if (ancestorCommit != null)
			config.setAncestorLabel(NLS.bind(LABELPATTERN, ancestorCommit
					.getShortMessage(), ancestorCommit.name()));

		setTitle(NLS.bind(UIText.GitMergeEditorInput_MergeEditorTitle,
				new Object[] {
						Activator.getDefault().getRepositoryUtil()
								.getRepositoryName(repo),
						rightCommit.getShortMessage(), fullBranch }));

		try {
			for (IFile file : files) {
				if (monitor.isCanceled())
					throw new InterruptedException();
