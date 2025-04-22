	private void refreshProjects() {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		final File parentFile = repository.getWorkDir();
		for (IProject p : projects) {
			final File file = p.getLocation().toFile();
			if (file.getAbsolutePath().startsWith(parentFile.getAbsolutePath())) {
				try {
					System.out.println("Refreshing " + p);
					p.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}
	}

