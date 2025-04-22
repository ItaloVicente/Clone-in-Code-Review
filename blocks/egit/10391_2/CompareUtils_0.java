	public static void compareWorkspaceWithRef(Repository repository,
			IFile file, String refName, IWorkbenchPage page) throws IOException {
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
		if (page != null) {
			openInCompare(page, in);
		} else {
			CompareUI.openCompareEditor(in);
		}
	}

	public static void compareLocalWithRef(Repository repository, File file,
			String refName, IWorkbenchPage page) throws IOException {
		final String gitPath = getRepoRelativePath(repository, file);
		final ObjectId headCommitId = repository.resolve(Constants.HEAD);
		final RevWalk walk = new RevWalk(repository);
		final RevCommit head = walk.parseCommit(headCommitId);
		walk.release();
		final ITypedElement base = CompareUtils.getFileRevisionTypedElement(
				gitPath, head, repository);

		final ObjectId destCommitId = repository.resolve(refName);
		RevWalk rw = new RevWalk(repository);
		RevCommit commit = rw.parseCommit(destCommitId);
		rw.release();
		final ITypedElement destCommit = getFileRevisionTypedElement(gitPath,
				commit, repository);

		final ITypedElement commonAncestor;
		if (base != null && commit != null) {
			commonAncestor = getFileRevisionTypedElementForCommonAncestor(
					gitPath, headCommitId, destCommitId, repository);
		} else {
			commonAncestor = null;
		}

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, destCommit, commonAncestor, null);
		in.getCompareConfiguration().setRightLabel(refName);
		if (page != null) {
			openInCompare(page, in);
		} else {
			CompareUI.openCompareEditor(in);
		}
	}

	private static String getRepoRelativePath(Repository repository, File file) {
		IPath workdirPath = new Path(repository.getWorkTree().getPath());
		IPath filePath = new Path(file.getPath()).setDevice(null);
		return filePath.removeFirstSegments(workdirPath.segmentCount())
				.toString();
	}

