
	private void addSynchronizeItem(Menu men, final RepositoryTreeNode node,
			final Ref ref) {
		final Repository repo = node.getRepository();
		final Set<IProject> repoProjects = new HashSet<IProject>();
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repo) {
				repoProjects.add(project);
			}
		}

		MenuItem sync = new MenuItem(men, SWT.PUSH);
		sync.setText(UIText.RepositoriesView_Synchronize_MenuItem);

		boolean projectExist = !repoProjects.isEmpty();

		sync.setEnabled(projectExist);

		if (projectExist) {
			sync.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					GitSynchronizeData gsd = new GitSynchronizeData(repo,
							Constants.HEAD, ref.getName(), repoProjects, false);

					new GitSynchronize(gsd);
				}
			});
		}
	}

