	public static void compareWorkspaceWithRef(Repository repository,
			IFile file, String refName) throws IOException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		final String gitPath = mapping.getRepoRelativePath(file);
		final ITypedElement base = SaveableCompareEditorInput
				.createFileElement(file);

		final ObjectId destCommitId = repository.resolve(refName);
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(destCommitId);
		rw.release();
		final ITypedElement destCommit = getFileRevisionTypedElement(gitPath,
				commit, repository);

		final ITypedElement commonAncestor;
		if (base != null && commit != null) {
			final ObjectId headCommitId = repository.resolve(Constants.HEAD);
			commonAncestor = getFileRevisionTypedElementForCommonAncestor(
					gitPath, headCommitId, destCommitId, repository);
		} else {
			commonAncestor = null;
		}

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, destCommit, commonAncestor, null);
		in.getCompareConfiguration().setRightLabel(refName);
		CompareUI.openCompareEditor(in);
	}

