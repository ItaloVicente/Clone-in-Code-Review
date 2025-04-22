	private void discardChange(IResource res) throws IOException {
		IProject[] proj = new IProject[] { res.getProject() };
		Repository repository = getRepositoriesFor(proj)[0];

		String resRelPath = RepositoryMapping.getMapping(res).getRepoRelativePath(res);
		Entry e = repository.getIndex().getEntry(resRelPath);

		if (e != null && e.getStage() == 0 && e.isModified(repository.getWorkDir())) {
			repository.getIndex().checkoutEntry(repository.getWorkDir(), e);

			try {
				res.refreshLocal(0, new NullProgressMonitor());
			} catch (CoreException e1) {
				Activator.handleError(UIText.DiscardChangesAction_refreshErrorMessage, e1, true);
			}

			repository.getIndex().write();
		}
	}
