	public static void compare(IResource[] resources, Repository repository,
			String srcRev, String dstRev, boolean includeLocal)
			throws IOException {
		if (resources.length == 1 && resources[0] instanceof IFile
				&& canDirectlyOpenInCompare((IFile) resources[0])) {
			if (includeLocal)
				compareWorkspaceWithRef(repository, (IFile) resources[0],
						dstRev, null);
			else
				compareBetween(repository, (IFile) resources[0], srcRev,
						dstRev, null);
		} else {
			GitModelSynchronize.synchronize(resources, repository, srcRev,
					dstRev, includeLocal);
		}
	}

	private static void compareBetween(Repository repository, IFile file,
			String srcRev, String dstRev, IWorkbenchPage page)
			throws IOException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		final String gitPath = mapping.getRepoRelativePath(file);

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
			final RevWalk rw = new RevWalk(repository);
			final ObjectId id = repository.resolve(rev);
			final RevCommit revCommit = rw.parseCommit(id);
			typedElement = getFileRevisionTypedElement(gitPath,
					revCommit, repository);
			rw.release();
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
	}

