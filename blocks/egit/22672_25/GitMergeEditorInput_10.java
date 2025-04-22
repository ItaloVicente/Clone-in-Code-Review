	private void setLabels(Repository repository, RevCommit rightCommit,
			RevCommit leftCommit, RevCommit ancestorCommit)
			throws InvocationTargetException {
		CompareConfiguration config = getCompareConfiguration();
		config.setRightLabel(NLS.bind(LABELPATTERN,
				rightCommit.getShortMessage(),
				CompareUtils.truncatedRevision(rightCommit.name())));

		if (!useWorkspace)
			config.setLeftLabel(NLS.bind(LABELPATTERN,
					leftCommit.getShortMessage(),
					CompareUtils.truncatedRevision(leftCommit.name())));
		else
			config.setLeftLabel(UIText.GitMergeEditorInput_WorkspaceHeader);

		if (ancestorCommit != null)
			config.setAncestorLabel(NLS.bind(LABELPATTERN,
					ancestorCommit.getShortMessage(),
					CompareUtils.truncatedRevision(ancestorCommit.name())));

		final String fullBranch;
		try {
			fullBranch = repository.getFullBranch();
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
		setTitle(NLS.bind(
				UIText.GitMergeEditorInput_MergeEditorTitle,
				new Object[] {
						Activator.getDefault().getRepositoryUtil()
								.getRepositoryName(repository),
						rightCommit.getShortMessage(), fullBranch }));
	}

