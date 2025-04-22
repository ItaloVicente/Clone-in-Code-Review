	public Map<IProject, File> getProjects() {
		final TreeItem[] selection = tree.getSelection();
		Map<IProject, File> ret = new HashMap<IProject, File>(selection.length);
		for (int i = 0; i < selection.length; ++i) {
			final TreeItem treeItem = selection[i];
			final IProject project = (IProject) treeItem.getData();
			final File selectedRepo = new File(treeItem.getText(2));
			File localPathToRepo = selectedRepo;
			if (selectedRepo.isAbsolute()) {
				final URI projectLocation = project.getLocationURI();
				localPathToRepo = new File(projectLocation.relativize(selectedRepo.toURI()).getPath());
			}
			ret.put(project, localPathToRepo);
		}
