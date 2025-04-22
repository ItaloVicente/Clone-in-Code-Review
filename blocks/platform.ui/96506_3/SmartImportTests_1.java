
	@Test
	public void testInitialWorkingSets() throws Exception {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.createWorkingSet("testWorkingSet", new IAdaptable[0]);
		workingSet.setId("org.eclipse.ui.resourceWorkingSetPage");
		workingSetManager.addWorkingSet(workingSet);
		File directoryToImport = Files.createTempDirectory(getClass().getSimpleName()).toFile();
		try {
			SmartImportWizard wizard = new SmartImportWizard();
			wizard.setInitialImportSource(directoryToImport);
			wizard.setInitialWorkingSets(Collections.singleton(workingSet));
			proceedSmartImportWizard(wizard);
			assertEquals("Projects were not added to working set", 1, workingSet.getElements().length);
		} finally {
			for (File child : directoryToImport.listFiles()) {
				child.delete();
			}
			directoryToImport.delete();
			workingSetManager.removeWorkingSet(workingSet);
		}
	}

	@Test
	public void testChangedWorkingSets() throws Exception {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.createWorkingSet("testWorkingSet", new IAdaptable[0]);
		workingSet.setId("org.eclipse.ui.resourceWorkingSetPage");
		workingSetManager.addWorkingSet(workingSet);
		IWorkingSet workingSet2 = workingSetManager.createWorkingSet("testWorkingSet2", new IAdaptable[0]);
		workingSet2.setId("org.eclipse.ui.resourceWorkingSetPage");
		workingSetManager.addWorkingSet(workingSet2);
		WorkbenchPlugin.getDefault().getDialogSettings().put("workingset_selection_history",
				new String[] { workingSet.getName(), workingSet2.getName() });
		File directoryToImport = Files.createTempDirectory(getClass().getSimpleName()).toFile();
		try {
			SmartImportWizard wizard = new SmartImportWizard();
			wizard.setInitialImportSource(directoryToImport);
			wizard.setInitialWorkingSets(Collections.singleton(workingSet));
			this.dialog = new WizardDialog(getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
			dialog.setBlockOnOpen(false);
			dialog.open();
			processEvents();
			SmartImportRootWizardPage page = (SmartImportRootWizardPage) dialog.getCurrentPage();
			Combo combo = getComboWithSelection(workingSet.getName(), (Composite) page.getControl());
			combo.select(1);
			Event e = new Event();
			e.widget = combo;
			e.display = combo.getDisplay();
			e.type = SWT.Selection;
			e.text = workingSet2.getName();
			e.index = 1;
			combo.notifyListeners(SWT.Selection, e);
			processEvents();
			wizard.performFinish();
			waitForJobs(100, 1000); // give the job framework time to schedule
			wizard.getImportJob().join();
			waitForJobs(100, 5000); // give some time for asynchronous workspace
			assertEquals("WorkingSet2 should be selected", Collections.singleton(workingSet2),
					page.getSelectedWorkingSets());
			assertEquals("Projects were not added to working set", 1, workingSet2.getElements().length);
		} finally {
			for (File child : directoryToImport.listFiles()) {
				child.delete();
			}
			directoryToImport.delete();
			workingSetManager.removeWorkingSet(workingSet);
			workingSetManager.removeWorkingSet(workingSet2);
		}
	}

	private static Combo getComboWithSelection(String selection, Composite parent) {
		for (Control control : parent.getChildren()) {
			if (control instanceof Combo && ((Combo) control).getText().equals(selection)) {
				return (Combo) control;
			} else if (control instanceof Composite) {
				Combo res = getComboWithSelection(selection, (Composite) control);
				if (res != null) {
					return res;
				}
			}
		}
		return null;
	}
