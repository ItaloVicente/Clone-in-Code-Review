	private void importProjects(IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		switch (importWithDirectoriesPage.getWizardSelection()) {
		case GitSelectWizardPage.EXISTING_PROJECTS_WIZARD: {
			final Set<ProjectRecord> projectsToCreate = new HashSet<ProjectRecord>();
			final List<IWorkingSet> workingSets = new ArrayList<IWorkingSet>();
			final Repository[] repository = new Repository[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					projectsToCreate.addAll(projectsImportPage
							.getCheckedProjects());
					IWorkingSet[] workingSetArray = projectsImportPage
							.getSelectedWorkingSets();
					workingSets.addAll(Arrays.asList(workingSetArray));
					repository[0] = selectRepoPage.getRepository();
				}
			});
			ProjectUtils.createProjects(projectsToCreate, repository[0],
					workingSets.toArray(new IWorkingSet[workingSets.size()]),
					monitor);
			break;
		}
		case GitSelectWizardPage.NEW_WIZARD: {
			final File[] repoDir = new File[1];
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					repoDir[1] = selectRepoPage.getRepository().getDirectory();
				}
			});
			final List<IProject> previousProjects = Arrays
					.asList(ResourcesPlugin.getWorkspace().getRoot()
							.getProjects());
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					new NewProjectAction(PlatformUI.getWorkbench()
