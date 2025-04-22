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
    	/* get workingSetManager */
    	IWorkingSetManager workingSetManager =
    		fWorkbench.getWorkingSetManager();

    	workingSetManager.addWorkingSet(fWorkingSet);
    	String origName=fWorkingSet.getName();

    	/* check that workingSetManager contains "fWorkingSet"*/
    	assertTrue(ArrayUtil.equals(
    			new IWorkingSet[] {  fWorkingSet },
    			workingSetManager.getWorkingSets()));

    	fWorkingSet.setName(" ");
    	assertEquals(" ", fWorkingSet.getName());

    	/* remove "fWorkingSet" from working set manager */
    	workingSetManager.removeWorkingSet(fWorkingSet);

    	/* check that "fWorkingSet" was removed  after rename*/
    	if(!ArrayUtil.equals(new IWorkingSet[] {},
    			workingSetManager.getWorkingSets())){
    		/*Test Failure, report after restoring state*/
    		fWorkingSet.setName(origName);
    		workingSetManager.removeWorkingSet(fWorkingSet);
    		fail("expected that fWorkingSet has been removed");
    	}

    }
    /**
     * Tests to ensure that a misbehaving listener does not bring down the manager.
     *
     * @throws Throwable
     */
    public void testListenerSafety() throws Throwable {
