		}

		@Override
		public void open() {
			openInEditor(repository, fileDiff, side, lineNo);
		}

	}

	public static void openFileInEditor(File file, int lineNoToReveal) {
		if (!file.exists()) {
			Activator.showError(
					NLS.bind(UIText.DiffViewer_FileDoesNotExist,
							file.getPath()),
					null);
			return;
		}
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = EgitUiEditorUtils.openEditor(file, page);
		EgitUiEditorUtils.revealLine(editor, lineNoToReveal);
	}

	public static void openInEditor(Repository repository, FileDiff d,
			DiffEntry.Side side, int lineNoToReveal) {
		ObjectId[] blobs = d.getBlobs();
		switch (side) {
		case OLD:
			openInEditor(repository, d.getOldPath(), d.getCommit().getParent(0),
					blobs[0], lineNoToReveal);
			break;
		default:
			openInEditor(repository, d.getNewPath(), d.getCommit(),
					blobs[blobs.length - 1], lineNoToReveal);
			break;
		}
	}

	private static void openInEditor(Repository repository, String path,
			RevCommit commit, ObjectId blob, int reveal) {
		try {
			IFileRevision rev = CompareUtils.getFileRevision(path, commit,
					repository, blob);
			if (rev == null) {
				String message = NLS.bind(
						UIText.DiffViewer_notContainedInCommit, path,
						commit.getName());
				Activator.showError(message, null);
				return;
			}
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IEditorPart editor = EgitUiEditorUtils.openEditor(page, rev,
					new NullProgressMonitor());
			EgitUiEditorUtils.revealLine(editor, reveal);
		} catch (IOException | CoreException e) {
			Activator.handleError(UIText.GitHistoryPage_openFailed, e, true);
		}
	}

	public static void showTwoWayFileDiff(Repository repository, FileDiff d) {
		String np = d.getNewPath();
		String op = d.getOldPath();
		RevCommit c = d.getCommit();
		ObjectId[] blobs = d.getBlobs();

		final RevCommit oldCommit;
		final ObjectId oldObjectId;
		if (!d.getChange().equals(ChangeType.ADD)) {
			oldCommit = c.getParent(0);
			oldObjectId = blobs[0];
		} else {
			oldCommit = null;
			oldObjectId = null;
		}

		final RevCommit newCommit;
		final ObjectId newObjectId;
		if (d.getChange().equals(ChangeType.DELETE)) {
			newCommit = null;
			newObjectId = null;
		} else {
			newCommit = c;
			newObjectId = blobs[blobs.length - 1];
		}
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		if (oldCommit != null && newCommit != null && repository != null) {
			IFile file = np != null
					? ResourceUtil.getFileForLocation(repository, np, false)
					: null;
			try {
				if (file != null) {
					IResource[] resources = new IResource[] { file, };
					CompareUtils.compare(resources, repository, np, op,
							newCommit.getName(), oldCommit.getName(), false,
							page);
				} else {
					IPath location = new Path(
							repository.getWorkTree().getAbsolutePath())
									.append(np);
					CompareUtils.compare(location, repository,
							newCommit.getName(), oldCommit.getName(), false,
							page);
				}
			} catch (IOException e) {
				Activator.handleError(UIText.GitHistoryPage_openFailed, e,
						true);
			}
			return;
		}

		final ITypedElement oldSide = createTypedElement(repository, op,
				oldCommit, oldObjectId);
		final ITypedElement newSide = createTypedElement(repository, np,
				newCommit, newObjectId);
		CompareUtils.openInCompare(page,
				new GitCompareFileRevisionEditorInput(newSide, oldSide, null));
