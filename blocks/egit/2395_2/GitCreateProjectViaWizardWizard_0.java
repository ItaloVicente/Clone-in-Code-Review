	private void importProjects(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		switch (mySelectionPage.getWizardSelection()) {
		case GitSelectWizardPage.EXISTING_PROJECTS_WIZARD: {
			final Set<ProjectRecord> projectsToCreate = new HashSet<ProjectRecord>();
			final List<IWorkingSet> workingSets = new ArrayList<IWorkingSet>();
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					projectsToCreate.addAll(myProjectsImportPage
							.getCheckedProjects());
					IWorkingSet[] workingSetArray = myProjectsImportPage
							.getSelectedWorkingSets();
					workingSets.addAll(Arrays.asList(workingSetArray));
				}
			});
			ProjectUtils.createProjects(projectsToCreate, myRepository,
					workingSets.toArray(new IWorkingSet[workingSets.size()]),
					monitor);
			break;
		}
		case GitSelectWizardPage.NEW_WIZARD: {
			final List<IProject> previousProjects = Arrays
					.asList(ResourcesPlugin.getWorkspace().getRoot()
							.getProjects());
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
