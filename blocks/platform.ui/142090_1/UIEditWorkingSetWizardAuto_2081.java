		fWizard.performFinish();
		workingSet = ((WorkingSetEditWizard) fWizard).getSelection();
		IAdaptable[] workingSetItems = workingSet.getElements();
		assertEquals(WORKING_SET_NAME_2, workingSet.getName());
		assertTrue(ArrayUtil.contains(workingSetItems, p1));
		assertTrue(ArrayUtil.contains(workingSetItems, p2));
