
	private void addToWorkspace(String[] references) throws TeamException {
		capability.addToWorkspace(references,
				new ProjectSetSerializationContext(),
				new NullProgressMonitor());
	}
