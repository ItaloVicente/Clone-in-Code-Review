	@Test
	public void testImportProjectWithExistingName()
			throws IOException, OperationCanceledException, InterruptedException {
		URL url = FileLocator
				.toFileURL(getClass()
						.getResource("/data/org.eclipse.datatransferArchives/sameNameProject1/sameNameProject"));
		File file = new File(url.getFile());
		runSmartImport(file);

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		assertEquals(1, projects.length);

		url = FileLocator
				.toFileURL(getClass()
						.getResource("/data/org.eclipse.datatransferArchives/sameNameProject2/sameNameProject"));
		file = new File(url.getFile());

		SmartImportWizard wizard = new SmartImportWizard();
		wizard.setInitialImportSource(file);
		this.dialog = new WizardDialog(getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		dialog.setBlockOnOpen(false);
		dialog.open();
		processEvents();
		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return !dialog.getErrorMessage().isEmpty();
			}
		}, -1);
		SmartImportRootWizardPage page = (SmartImportRootWizardPage) dialog.getCurrentPage();
		CheckboxTreeViewer treeViewer = getTreeViewer((Composite) page.getControl());
		assertNotNull(treeViewer);
		assertEquals(1, treeViewer.getTree().getItemCount());
		assertEquals(0, treeViewer.getCheckedElements().length);
		assertEquals("Project with same name already imported", treeViewer.getTree().getItems()[0].getText(1));
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

