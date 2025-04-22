
	private void addSynchtonizeItem(Menu men, final RepositoryTreeNode node,
			final Ref ref) {
		final Repository repo = node.getRepository();
		String projectName = repo.getDirectory().getParentFile().getName();
		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);

		MenuItem sync = new MenuItem(men, SWT.PUSH);
		sync.setText(UIText.RepositoriesView_Synchronize_MenuItem);

		boolean projectExist = project.exists();
		sync.setEnabled(projectExist);

		if (projectExist) {
			sync.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Map<Repository, String> branches = new HashMap<Repository, String>();
					branches.put(repo, ref.getName());

					new GitSynchronize(branches, new IResource[] { project });
				}
			});
		}
	}
