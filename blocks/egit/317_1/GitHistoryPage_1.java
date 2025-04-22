
	private class CompareWithWorkingTreeAction extends Action {
		public CompareWithWorkingTreeAction() {
			super("Compare");
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 1) {
				Iterator<?> it = selection.iterator();
				SWTCommit commit1 = (SWTCommit) it.next();
				if (getInput() instanceof IFile){
					IFile file = (IFile) getInput();
					final RepositoryMapping map = RepositoryMapping
							.getMapping(file);
					final String gitPath = map
							.getRepoRelativePath(file);
					final IFileRevision file1 = GitFileRevision.inCommit(db,
							commit1, gitPath, null);
					final ITypedElement base = SaveableCompareEditorInput.createFileElement(file);

					final FileRevisionTypedElement next = new FileRevisionTypedElement(file1);

					final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
							base, next, null);
					CompareUI.openCompareEditor(in);
				}

			}

		}

		@Override
		public boolean isEnabled() {
			int size = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection()).size();
			return IFile.class.isAssignableFrom(getInput().getClass())
					&& size == 1;
		}

	}

	private class CompareVersionsAction extends Action {
		public CompareVersionsAction() {
			super("Compare versions");
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 2) {
				Iterator<?> it = selection.iterator();
				SWTCommit commit1 = (SWTCommit) it.next();
				SWTCommit commit2 = (SWTCommit) it.next();

				if (getInput() instanceof IResource){
					IResource resource = (IResource) getInput();
					final RepositoryMapping map = RepositoryMapping
							.getMapping(resource);
					final String gitPath = map
							.getRepoRelativePath(resource);
					final IFileRevision file1 = GitFileRevision.inCommit(db,
							commit1, gitPath, null);
					final IFileRevision file2 = GitFileRevision.inCommit(db,
							commit2, gitPath, null);

					final FileRevisionTypedElement base = new FileRevisionTypedElement(file1);
					final FileRevisionTypedElement next = new FileRevisionTypedElement(file2);
					CompareEditorInput in = new GitCompareFileRevisionEditorInput(base, next, null);
					CompareUI.openCompareEditor(in);
				}

			}

		}

		@Override
		public boolean isEnabled() {
			int size = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection()).size();
			return IFile.class.isAssignableFrom(getInput().getClass())
					&& size == 2;
		}

	}

