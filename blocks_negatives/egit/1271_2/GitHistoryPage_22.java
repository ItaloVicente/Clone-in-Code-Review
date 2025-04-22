
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
					ITypedElement right = CompareUtils
							.getFileRevisionTypedElement(gitPath, commit, db);
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

					final ITypedElement base = CompareUtils
							.getFileRevisionTypedElement(gitPath, commit1, db);
					final ITypedElement next = CompareUtils
							.getFileRevisionTypedElement(gitPath, commit2, db);
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

	private class ViewVersionsAction extends Action {
		public ViewVersionsAction() {
			super(UIText.GitHistoryPage_open);
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() < 1)
				return;
			if (!(getInput() instanceof IFile))
				return;
			IFile resource = (IFile) getInput();
			final RepositoryMapping map = RepositoryMapping
					.getMapping(resource);
			final String gitPath = map.getRepoRelativePath(resource);
			Iterator<?> it = selection.iterator();
			boolean errorOccured = false;
			List<ObjectId> ids = new ArrayList<ObjectId>();
			while (it.hasNext()) {
				SWTCommit commit = (SWTCommit) it.next();
				IFileRevision rev = null;
				try {
					rev = CompareUtils.getFileRevision(gitPath, commit, db, null);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath, gitPath,
							commit.getId()), e);
					errorOccured = true;
				}
				if (rev != null) {
					try {
						EgitUiEditorUtils.openEditor(getSite().getPage(), rev,
								new NullProgressMonitor());
					} catch (CoreException e) {
						Activator.logError(UIText.GitHistoryPage_openFailed, e);
						errorOccured = true;
					}
				} else {
					ids.add(commit.getId());
				}
			}
			if (errorOccured)
				Activator.showError(UIText.GitHistoryPage_openFailed, null);
			if (ids.size() > 0) {
				for (ObjectId objectId : ids) {
				}
				MessageDialog.openError(getSite().getShell(),
						UIText.GitHistoryPage_fileNotFound, NLS.bind(
								UIText.GitHistoryPage_notContainedInCommits,
								gitPath, idList));
			}

		}

		@Override
		public boolean isEnabled() {
			int size = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection()).size();
			return IFile.class.isAssignableFrom(getInput().getClass())
					&& size >= 1;
		}

	}

	private class CreatePatchAction extends Action {

		private TreeWalk walker;

		public CreatePatchAction() {
			super(UIText.GitHistoryPage_CreatePatch);
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 1) {

				Iterator<?> it = selection.iterator();
				SWTCommit commit = (SWTCommit) it.next();

				GitCreatePatchWizard.run(getHistoryPageSite().getPart(),
						commit, walker, db);
			}
		}

		public void setTreeWalk(TreeWalk walker) {
			this.walker = walker;
		}

		@Override
		public boolean isEnabled() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			Iterator<?> it = selection.iterator();
			SWTCommit commit = (SWTCommit) it.next();
			return (commit.getParentCount() == 1);

		}

	}
