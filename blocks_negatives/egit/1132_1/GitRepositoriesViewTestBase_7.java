	protected static void waitInUI() throws InterruptedException {
		Thread.sleep(1000);
	}

	protected void shareProjects(File repositoryDir) throws Exception {
		Repository myRepository = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache().lookupRepository(repositoryDir);
		FilenameFilter projectFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals(".project");
			}
		};
		for (File file : myRepository.getWorkDir().listFiles()) {
			if (file.isDirectory()) {
				if (file.list(projectFilter).length > 0) {
					IProjectDescription desc = ResourcesPlugin.getWorkspace()
							.newProjectDescription(file.getName());
					desc.setLocation(new Path(file.getPath()));
					IProject prj = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(file.getName());
					prj.create(desc, null);
					prj.open(null);

					new ConnectProviderOperation(prj, myRepository
							.getDirectory()).execute(null);
				}
			}
		}
	}

