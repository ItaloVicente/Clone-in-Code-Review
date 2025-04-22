	private static NestedProjectManager INSTANCE = new NestedProjectManager();

	private SortedMap<IPath, IProject> locationsToProjects = new TreeMap<IPath, IProject>(new PathComparator());

	private NestedProjectManager() {
		refreshProjectsList();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				if (event.getType() == IResourceChangeEvent.POST_CHANGE
						&& event.getDelta().getResource().getType() == IResource.PROJECT) {
					refreshProjectsList();
				}
			}
		});
	}

	private void refreshProjectsListIfNeeded() {
		if (this.locationsToProjects.size() != ResourcesPlugin.getWorkspace().getRoot().getProjects().length) {
			refreshProjectsList();
		}
	}

	private void refreshProjectsList() {
		this.locationsToProjects.clear();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			this.locationsToProjects.put(project.getLocation(), project);
		}
	}

	public static NestedProjectManager getInstance() {
		synchronized (INSTANCE) {
			if (INSTANCE == null) {
				INSTANCE = new NestedProjectManager();
			}
		}
		return INSTANCE;
	}

