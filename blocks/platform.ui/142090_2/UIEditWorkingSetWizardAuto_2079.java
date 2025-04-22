		assertTrue(page.getClass() == fDefaultEditPage.getClass());
		assertTrue(page.canFlipToNextPage() == false);
		assertTrue(fWizard.canFinish() == false);
		assertNull(page.getErrorMessage());
		IWorkingSetManager workingSetManager = fWorkbench
				.getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.createWorkingSet(
				WORKING_SET_NAME_1, new IAdaptable[] { p1, f2 });
		((WorkingSetEditWizard) fWizard).setSelection(workingSet);
