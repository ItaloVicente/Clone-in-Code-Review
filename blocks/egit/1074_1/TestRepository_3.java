
	public void connect(IProject project) throws CoreException {
		ConnectProviderOperation op = new ConnectProviderOperation(project,
				this.getRepository().getDirectory());
		op.execute(null);
	}
