	protected DiffContainer buildInput(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		Repository repo = getRepository();
		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit leftCommit = leftVersion == null ? null
					: rw.parseCommit(repo.resolve(leftVersion));
			RevCommit rightCommit = rw.parseCommit(repo.resolve(rightVersion));

			CompareConfiguration config = getCompareConfiguration();
			config.setDefaultLabelProvider(new GitCompareLabelProvider());
			if (leftVersion == null) {
				config.setLeftLabel(
						UIText.GitCompareEditorInput_WorkingTreeFallbackLabel);
			} else {
				config.setLeftLabel(leftVersion);
			}
			config.setRightLabel(rightVersion);

			setTitle(MessageFormat.format(
					UIText.GitCompareEditorInput_EditorTitle,
					RepositoryUtil.INSTANCE.getRepositoryName(repo),
					leftVersion == null
							? UIText.GitCompareEditorInput_WorkingTreeSourceName
							: CompareUtils.truncatedRevision(leftVersion),
					CompareUtils.truncatedRevision(rightVersion)));

			return buildDiffContainer(leftCommit, rightCommit, monitor);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}
