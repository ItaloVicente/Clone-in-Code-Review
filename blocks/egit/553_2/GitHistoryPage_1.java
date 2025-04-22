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
					rev = getFileRevision(resource, gitPath, commit);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath, gitPath,
							commit.getId()), e);
					errorOccured = true;
				}
				if (rev != null) {
					try {
						Utils.openEditor(getSite().getPage(), rev,
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
				MessageDialog.openError(getSite().getShell(),
						UIText.GitHistoryPage_openFailed,
						UIText.GitHistoryPage_seeLog);
			if (ids.size() > 0) {
				String idList = ""; //$NON-NLS-1$
				for (ObjectId objectId : ids) {
					idList += objectId.getName() + " "; //$NON-NLS-1$
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

	private IFileRevision getFileRevision(final IFile resource,
			final String gitPath, SWTCommit commit) throws IOException {

		TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
		if (w != null) {
			final IFileRevision fileRevision = GitFileRevision.inCommit(db,
					commit, gitPath, null);
			return fileRevision;
		}
		return null;
	}

