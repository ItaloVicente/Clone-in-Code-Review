		boolean importableProjectsOnly = true;

		for (Object node : sel.toArray()) {
			RepositoryTreeNode tnode = (RepositoryTreeNode) node;
			importableProjectsOnly = tnode.getType() == RepositoryTreeNodeType.PROJ;
			if (!importableProjectsOnly)
				break;
		}

		if (importableProjectsOnly) {
			MenuItem sync = new MenuItem(men, SWT.PUSH);
			sync.setText(UIText.RepositoriesView_ImportProject_MenuItem);

			sync.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

						public void run(IProgressMonitor monitor)
								throws CoreException {

							for (Object selected : sel.toArray()) {
								RepositoryTreeNode<File> projectNode = (RepositoryTreeNode<File>) selected;
								File file = projectNode.getObject();

								IProjectDescription pd = ResourcesPlugin
										.getWorkspace().newProjectDescription(
												file.getName());
								IPath locationPath = new Path(file
										.getAbsolutePath());

								pd.setLocation(locationPath);

								ResourcesPlugin.getWorkspace().getRoot()
										.getProject(pd.getName()).create(pd,
												monitor);
								IProject project = ResourcesPlugin
										.getWorkspace().getRoot().getProject(
												pd.getName());
								project.open(monitor);

								File gitDir = projectNode.getRepository()
										.getDirectory();

								ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
										project, gitDir);
								connectProviderOperation
										.execute(new SubProgressMonitor(
												monitor, 20));

							}

						}
					};

					try {

						ResourcesPlugin.getWorkspace().run(wsr,
								ResourcesPlugin.getWorkspace().getRoot(),
								IWorkspace.AVOID_UPDATE,
								new NullProgressMonitor());

						scheduleRefresh();
					} catch (CoreException e1) {
						Activator.logError(e1.getMessage(), e1);
					}

				}

			});
		}

