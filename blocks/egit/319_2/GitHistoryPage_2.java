
	private class CompareWithWorkingTreeAction extends Action {
		public CompareWithWorkingTreeAction() {
			super(UIText.GitHistoryPage_CompareWithWorking);
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 1) {
				Iterator<?> it = selection.iterator();
				SWTCommit commit = (SWTCommit) it.next();
				if (getInput() instanceof IFile){
					IFile file = (IFile) getInput();
					final RepositoryMapping mapping = RepositoryMapping.getMapping(file.getProject());
					final String gitPath = mapping.getRepoRelativePath(file);
					ITypedElement right = getEditableRevision(file, gitPath,
							commit);
					final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
							SaveableCompareEditorInput.createFileElement(file),
							right,
							null);
					openInCompare(in);
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
			super(UIText.GitHistoryPage_CompareVersions);
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 2) {
				Iterator<?> it = selection.iterator();
				SWTCommit commit1 = (SWTCommit) it.next();
				SWTCommit commit2 = (SWTCommit) it.next();

				if (getInput() instanceof IFile){
					IFile resource = (IFile) getInput();
					final RepositoryMapping map = RepositoryMapping
							.getMapping(resource);
					final String gitPath = map
							.getRepoRelativePath(resource);

					final ITypedElement base = getEditableRevision(resource, gitPath, commit1);
					final ITypedElement next = getEditableRevision(resource, gitPath, commit2);
					CompareEditorInput in = new GitCompareFileRevisionEditorInput(base, next, null);
					openInCompare(in);
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

