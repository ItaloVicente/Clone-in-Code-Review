	/**
	 * Create the selected projects
	 * @param repository
	 *
	 * @return boolean <code>true</code> if all project creations were
	 *         successful.
	 */
	boolean createProjects(final Repository repository) {
		final Set<ProjectRecord> selected = getCheckedProjects();
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask("", selected.size()); //$NON-NLS-1$
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					for (ProjectRecord projectRecord : selected) {
						IProject project = createExistingProject(projectRecord,
								new SubProgressMonitor(monitor, 1));
						ConnectProviderOperation cpo = new ConnectProviderOperation(
								project, repository.getDirectory());
						try {
							cpo.execute(new NullProgressMonitor());
						} catch (CoreException e) {
							throw new InvocationTargetException(e);
						}
					}
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			Activator.handleError(UIText.WizardProjectImportPage_errorMessage,
					t, true);
			return false;
		}
		addProjectsToWorkingSet(selected);
		return true;
	}

	private void addProjectsToWorkingSet(Set<ProjectRecord> selected) {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (ProjectRecord projectRecord : selected) {
			IWorkingSet[] selectedWorkingSets = workingSetGroup
					.getSelectedWorkingSets();
			String projectName = projectRecord.getProjectName();
			IProject project = root.getProject(projectName);
			workingSetManager.addToWorkingSets(project, selectedWorkingSets);
		}
	}

	/**
	 * Create the project described in record. If it is successful return true.
	 *
	 * @param record
	 * @param monitor
	 * @return boolean <code>true</code> if successful
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	private IProject createExistingProject(final ProjectRecord record,
			IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		String projectName = record.getProjectName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);
		if (record.description == null) {
			record.description = workspace.newProjectDescription(projectName);
			IPath locationPath = new Path(record.projectSystemFile
					.getAbsolutePath());

			if (Platform.getLocation().isPrefixOf(locationPath)) {
				record.description.setLocation(null);
			} else {
				record.description.setLocation(locationPath);
			}
		} else {
			record.description.setName(projectName);
		}

		try {
			monitor.beginTask(
					UIText.WizardProjectsImportPage_CreateProjectsTask, 100);
			project.create(record.description, new SubProgressMonitor(monitor,
					30));
			project.open(IResource.BACKGROUND_REFRESH, new SubProgressMonitor(
					monitor, 50));
			return project;
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
		}
	}

