		Repository repo = getRepository();
		try (RevWalk rw = new RevWalk(repo)) {
			RevCommit leftCommit = rw.parseCommit(repo.resolve(leftVersion));
			RevCommit rightCommit = rw.parseCommit(repo.resolve(rightVersion));

			CompareConfiguration config = getCompareConfiguration();
			config.setDefaultLabelProvider(new GitCompareLabelProvider());
			config.setLeftLabel(leftVersion);
			config.setRightLabel(rightVersion);

			setTitle(MessageFormat.format(
					UIText.GitCompareEditorInput_EditorTitle,
					RepositoryUtil.getInstance().getRepositoryName(repo),
					CompareUtils.truncatedRevision(leftVersion),
					CompareUtils.truncatedRevision(rightVersion)));

			return buildDiffContainer(leftCommit, rightCommit, monitor);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
