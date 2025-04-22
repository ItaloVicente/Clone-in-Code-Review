		Repository newRep = getRepositoryOrNestedSubmoduleRepository(resource);
		if (newRep != null && newRep != currentRepository)
			reload(newRep);
	}

	private Repository getRepositoryOrNestedSubmoduleRepository(
			final IResource resource) {
		final Repository[] repo = new Repository[1];
		try {
			ModalContext.run(new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) {
					IProject project = resource.getProject();
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(project);
					if (mapping == null)
						return;
					repo[0] = mapping
							.getRepositoryOrNestedSubmoduleRepository(resource);
				}
			}, true, new NullProgressMonitor(), Display.getDefault());
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}
		return repo[0];
