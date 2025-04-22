
		if (node.getType() == RepositoryTreeNodeType.FILE) {

			final File file = (File) node.getObject();

			MenuItem openInTextEditor = new MenuItem(men, SWT.PUSH);
			openInTextEditor
					.setText(UIText.RepositoriesView_OpenInTextEditor_menu);
			openInTextEditor.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					IFileStore store = EFS.getLocalFileSystem().getStore(
							new Path(file.getAbsolutePath()));
					try {
						IDE.openEditor(getSite().getPage(),
								new FileStoreEditorInput(store),
								EditorsUI.DEFAULT_TEXT_EDITOR_ID);

					} catch (PartInitException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_Error_WindowTitle, e1
										.getMessage());
					}

				}

			});
		}

		if (node.getType() == RepositoryTreeNodeType.FOLDER) {
			String path = ((File) node.getObject()).getAbsolutePath();
			createImportProjectItem(men, node.getRepository(), path);
		}

		if (node.getType() == RepositoryTreeNodeType.WORKINGDIR) {
			String path = node.getRepository().getWorkDir().getAbsolutePath();
			createImportProjectItem(men, node.getRepository(), path);
		}

	}

	private void createImportProjectItem(Menu men, final Repository repo,
			final String path) {
		MenuItem importProjects;
		importProjects = new MenuItem(men, SWT.PUSH);
		importProjects
				.setText(UIText.RepositoriesView_ImportExistingProjects_MenuItem);
		importProjects.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Wizard wiz = new ExternalProjectImportWizard(path) {

					@Override
					public void addPages() {
						super.addPages();
					}

					@Override
					public boolean performFinish() {

						final Set<IPath> previousLocations = new HashSet<IPath>();
						for (IProject project : ResourcesPlugin.getWorkspace()
								.getRoot().getProjects()) {
							previousLocations.add(project.getLocation());
						}

						boolean success = super.performFinish();
						if (success) {

							IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

								public void run(IProgressMonitor monitor)
										throws CoreException {
									File gitDir = repo.getDirectory();
									File gitWorkDir = repo.getWorkDir();
									Path workPath = new Path(gitWorkDir
											.getAbsolutePath());

									for (IProject prj : ResourcesPlugin
											.getWorkspace().getRoot()
											.getProjects()) {

										if (workPath.isPrefixOf(prj
												.getLocation())) {
											if (previousLocations.contains(prj
													.getLocation())) {
												continue;
											}
											ConnectProviderOperation connectProviderOperation = new ConnectProviderOperation(
													prj, gitDir);
											connectProviderOperation
													.run(new SubProgressMonitor(
															monitor, 20));

										}
									}

								}
							};

							try {
								ResourcesPlugin.getWorkspace().run(
										wsr,
										ResourcesPlugin.getWorkspace()
												.getRoot(),
										IWorkspace.AVOID_UPDATE,
										new NullProgressMonitor());
								scheduleRefresh();
							} catch (CoreException ce) {
								MessageDialog
										.openError(
												getShell(),
												UIText.RepositoriesView_Error_WindowTitle,
												ce.getMessage());
							}

						}
						return success;
					}

				};

				WizardDialog dlg = new WizardDialog(getSite().getShell(), wiz);
				dlg.open();
			}

		});
