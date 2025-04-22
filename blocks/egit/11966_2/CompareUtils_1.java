	public static void compare(IResource[] resources, Repository repository,
			String srcRev, String dstRev, boolean includeLocal,
			IWorkbenchPage page) throws IOException {
		if (resources.length == 1 && resources[0] instanceof IFile
				&& canDirectlyOpenInCompare((IFile) resources[0])) {
			if (includeLocal)
				compareWorkspaceWithRef(repository, (IFile) resources[0],
						dstRev, page);
			else {
				final IFile file = (IFile) resources[0];
				final RepositoryMapping mapping = RepositoryMapping
						.getMapping(file);
				final String gitPath = mapping.getRepoRelativePath(file);

				compareBetween(repository, gitPath, srcRev,
						dstRev, page);
			}
		} else
			GitModelSynchronize.synchronize(resources, repository, srcRev,
					dstRev, includeLocal);
	}

	public static void compare(IPath location, Repository repository,
			String srcRev, String dstRev, boolean includeLocal,
			IWorkbenchPage page) throws IOException {
		if (includeLocal)
			compareLocalWithRef(repository, location, dstRev, page);
		else {
			final String gitPath = RepositoryMapping.getMapping(location)
					.getRepoRelativePath(location);
			compareBetween(repository, gitPath, srcRev, dstRev, page);
		}
	}

	private static void compareBetween(Repository repository, String gitPath,
			String srcRev, String dstRev, IWorkbenchPage page)
			throws IOException {
		final ITypedElement src = getTypedElementFor(repository, gitPath,
				srcRev);
		final ITypedElement dst = getTypedElementFor(repository, gitPath,
				dstRev);

		final ITypedElement commonAncestor;
		if (src != null && dst != null && !GitFileRevision.INDEX.equals(srcRev)
				&& !GitFileRevision.INDEX.equals(dstRev))
			commonAncestor = getTypedElementForCommonAncestor(repository,
					gitPath, srcRev, dstRev);
		else
			commonAncestor = null;

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				src, dst, commonAncestor, null);
		in.getCompareConfiguration().setLeftLabel(srcRev);
		in.getCompareConfiguration().setRightLabel(dstRev);

		if (page != null)
			openInCompare(page, in);
		else
			CompareUI.openCompareEditor(in);
	}

	private static ITypedElement getTypedElementFor(Repository repository, String gitPath, String rev) throws IOException {
		final ITypedElement typedElement;
		if (GitFileRevision.INDEX.equals(rev))
			typedElement = getIndexTypedElement(repository, gitPath);
		else if (Constants.HEAD.equals(rev))
			typedElement = getHeadTypedElement(repository, gitPath);
		else {
			final ObjectId id = repository.resolve(rev);
			final RevWalk rw = new RevWalk(repository);
			final RevCommit revCommit = rw.parseCommit(id);
			rw.release();
			typedElement = getFileRevisionTypedElement(gitPath,
					revCommit, repository);
		}
		return typedElement;
	}

	private static ITypedElement getTypedElementForCommonAncestor(
			Repository repository, final String gitPath, String srcRev,
			String dstRev) {
		ITypedElement ancestor = null;
		RevCommit commonAncestor = null;
		try {
			final ObjectId srcID = repository.resolve(srcRev);
			final ObjectId dstID = repository.resolve(dstRev);
			if (srcID != null && dstID != null)
				commonAncestor = RevUtils.getCommonAncestor(repository, srcID,
						dstID);
		} catch (IOException e) {
			Activator
					.logError(NLS.bind(UIText.CompareUtils_errorCommonAncestor,
							srcRev, dstRev), e);
		}
		if (commonAncestor != null)
			ancestor = CompareUtils.getFileRevisionTypedElement(gitPath,
					commonAncestor, repository);
		return ancestor;
