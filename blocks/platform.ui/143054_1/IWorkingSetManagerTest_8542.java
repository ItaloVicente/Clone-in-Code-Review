	}

	public void testRemovePropertyChangeListener() throws Throwable {
		IPropertyChangeListener listener = new TestPropertyChangeListener();

		fWorkingSetManager.removePropertyChangeListener(listener);

		fWorkingSetManager.addPropertyChangeListener(listener);
		fWorkingSetManager.removePropertyChangeListener(listener);

		resetChangeData();
		fWorkingSet.setName(WORKING_SET_NAME_1);
		assertEquals("", fChangeProperty);
	}

	public void testRemoveWorkingSet() throws Throwable {
		fWorkingSetManager.removeWorkingSet(fWorkingSet);
		assertTrue(ArrayUtil.equals(new IWorkingSet[] {}, fWorkingSetManager
				.getWorkingSets()));

		fWorkingSetManager.addWorkingSet(fWorkingSet);
		IWorkingSet workingSet2 = fWorkingSetManager.createWorkingSet(
				WORKING_SET_NAME_2, new IAdaptable[] { fWorkspace.getRoot() });
		fWorkingSetManager.addWorkingSet(workingSet2);
		fWorkingSetManager.removeWorkingSet(fWorkingSet);
		assertTrue(ArrayUtil.equals(new IWorkingSet[] { workingSet2 },
				fWorkingSetManager.getWorkingSets()));
	}

	public void testRemoveWorkingSetAfterRename() throws Throwable {
		IWorkingSetManager workingSetManager =
			fWorkbench.getWorkingSetManager();

		workingSetManager.addWorkingSet(fWorkingSet);
		String origName=fWorkingSet.getName();

		assertTrue(ArrayUtil.equals(
				new IWorkingSet[] {  fWorkingSet },
				workingSetManager.getWorkingSets()));

		fWorkingSet.setName(" ");
		assertEquals(" ", fWorkingSet.getName());

		workingSetManager.removeWorkingSet(fWorkingSet);

		if(!ArrayUtil.equals(new IWorkingSet[] {},
				workingSetManager.getWorkingSets())){
			fWorkingSet.setName(origName);
			workingSetManager.removeWorkingSet(fWorkingSet);
			fail("expected that fWorkingSet has been removed");
		}

	}
	public void testListenerSafety() throws Throwable {
