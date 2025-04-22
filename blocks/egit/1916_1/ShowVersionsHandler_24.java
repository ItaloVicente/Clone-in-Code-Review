		String gitPath = null;
		if (input instanceof IFile) {
			IFile resource = (IFile) input;
			final RepositoryMapping map = RepositoryMapping
					.getMapping(resource);
			gitPath = map.getRepoRelativePath(resource);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				IFileRevision rev = null;
				try {
					rev = CompareUtils.getFileRevision(gitPath, commit, map
							.getRepository(), null);
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.GitHistoryPage_errorLookingUpPath, gitPath,
							commit.getId()), e);
					errorOccured = true;
				}
				if (rev != null) {
					if (compareMode) {
						ITypedElement right = CompareUtils
								.getFileRevisionTypedElement(gitPath, commit,
										map.getRepository());
						final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
								SaveableCompareEditorInput
										.createFileElement(resource), right,
								null);
						try {
							openInCompare(event, in);
						} catch (Exception e) {
							errorOccured = true;
						}
					} else {
						try {
							EgitUiEditorUtils.openEditor(getPart(event)
									.getSite().getPage(), rev,
									new NullProgressMonitor());
						} catch (CoreException e) {
							Activator.logError(
									UIText.GitHistoryPage_openFailed, e);
							errorOccured = true;
						}
