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
					try {
						EgitUiEditorUtils.openTextEditor(getPart(event)
								.getSite().getPage(), rev, null);
					} catch (CoreException e) {
						Activator.logError(e.getMessage(), e);
						errorOccured = true;
					}
				} else {
					ids.add(commit.getId());
				}
