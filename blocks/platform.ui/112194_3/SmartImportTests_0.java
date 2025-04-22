	@Test
	public void testImportProjectWithExistingName()
			throws IOException, OperationCanceledException, InterruptedException {
		URL url = FileLocator
				.toFileURL(getClass().getResource("/data/org.eclipse.datatransferArchives/project/module2"));
		File file = new File(url.getFile());
		runSmartImport(file);

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		assertEquals(3, projects.length); // projects: module2, impl, test

		url = FileLocator
				.toFileURL(getClass().getResource("/data/org.eclipse.datatransferArchives/project/module3"));
		file = new File(url.getFile());

		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(file);
		this.dialog = new WizardDialog(getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.setBlockOnOpen(false);
		dialog.open();
		processEvents();
		final Button okButton = getFinishButton(dialog.buttonBar);
		assertNotNull(okButton);
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return okButton.isEnabled();
			}
		}, -1);
		SmartImportRootWizardPage page = (SmartImportRootWizardPage) dialog.getCurrentPage();
		CheckboxTreeViewer treeViewer = getTreeViewer((Composite) page.getControl());
		assertNotNull(treeViewer);
		assertEquals(3, treeViewer.getTree().getItemCount()); // projects: module3, impl, api
		assertEquals(2, treeViewer.getCheckedElements().length); // projects: module3, api

		wizard.performFinish();
		waitForJobs(100, 1000); // give the job framework time to schedule the
		wizard.getImportJob().join();
		waitForJobs(100, 5000); // give some time for asynchronous workspace

		projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		assertEquals(5, projects.length);// projects: module2, impl, test, module3, api
	}

	private CheckboxTreeViewer getTreeViewer(Composite parent) {
		for (Control control : parent.getChildren()) {
			if (control instanceof FilteredTree) {
				return (CheckboxTreeViewer) ((FilteredTree) control).getViewer();
			} else if (control instanceof Composite) {
				CheckboxTreeViewer res = getTreeViewer((Composite) control);
				if (res != null) {
					return res;
				}
			}
		}
		return null;
	}

