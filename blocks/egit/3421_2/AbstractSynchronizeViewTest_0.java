	protected void launchSynchronization(String projectName, String srcRef,
			String dstRef, boolean includeLocal) throws InterruptedException,
			IOException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		Repository repo = RepositoryMapping.getMapping(project).getRepository();
