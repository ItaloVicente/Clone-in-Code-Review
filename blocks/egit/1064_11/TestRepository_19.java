
	public void disconnect(IProject project) throws CoreException {
		Collection<IProject> projects = Collections.singleton(project
				.getProject());
		DisconnectProviderOperation disconnect = new DisconnectProviderOperation(
				projects);
		disconnect.execute(null);
	}

