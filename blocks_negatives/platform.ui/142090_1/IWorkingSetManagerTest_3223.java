        assertEquals(fWorkingSet, fChangeNewValue);
    }

    public void testAddRecentWorkingSet() throws Throwable {
        fWorkingSetManager.addRecentWorkingSet(fWorkingSet);
        fWorkingSetManager.addWorkingSet(fWorkingSet);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { fWorkingSet },
                fWorkingSetManager.getRecentWorkingSets()));

        IWorkingSet workingSet2 = fWorkingSetManager.createWorkingSet(
                WORKING_SET_NAME_2, new IAdaptable[] { fWorkspace.getRoot() });
        fWorkingSetManager.addRecentWorkingSet(workingSet2);
        fWorkingSetManager.addWorkingSet(workingSet2);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { workingSet2,
                fWorkingSet }, fWorkingSetManager.getRecentWorkingSets()));
    }

    public void testAddWorkingSet() throws Throwable {
        fWorkingSetManager.addWorkingSet(fWorkingSet);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { fWorkingSet },
                fWorkingSetManager.getWorkingSets()));

        boolean exceptionThrown = false;
        try {
            fWorkingSetManager.addWorkingSet(fWorkingSet);
        } catch (RuntimeException exception) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { fWorkingSet },
                fWorkingSetManager.getWorkingSets()));
    }

    public void testCreateWorkingSet() throws Throwable {
        IWorkingSet workingSet2 = fWorkingSetManager.createWorkingSet(
                WORKING_SET_NAME_2, new IAdaptable[] { fWorkspace.getRoot() });
        assertEquals(WORKING_SET_NAME_2, workingSet2.getName());
        assertTrue(ArrayUtil.equals(new IAdaptable[] { fWorkspace.getRoot() },
                workingSet2.getElements()));

        workingSet2 = fWorkingSetManager.createWorkingSet("",
                new IAdaptable[] {});
        assertEquals("", workingSet2.getName());
        assertTrue(ArrayUtil.equals(new IAdaptable[] {}, workingSet2
                .getElements()));
    }

    public void testCreateWorkingSetFromMemento() throws Throwable {
        IWorkingSet workingSet2 = fWorkingSetManager.createWorkingSet(
                WORKING_SET_NAME_2, new IAdaptable[] { fWorkspace.getRoot() });
        workingSet2.saveState(memento);
        IWorkingSet restoredWorkingSet2 = fWorkingSetManager
                .createWorkingSet(memento);
        assertEquals(WORKING_SET_NAME_2, restoredWorkingSet2.getName());
        assertTrue(ArrayUtil.equals(new IAdaptable[] { fWorkspace.getRoot() },
                restoredWorkingSet2.getElements()));
    }

    public void testCreateWorkingSetSelectionDialog() throws Throwable {
        IWorkbenchWindow window = openTestWindow();
        IWorkingSetSelectionDialog dialog = fWorkingSetManager
                .createWorkingSetSelectionDialog(window.getShell(), true);

        assertNotNull(dialog);
    }

    public void testGetRecentWorkingSets() throws Throwable {
        assertEquals(0, fWorkingSetManager.getRecentWorkingSets().length);

        fWorkingSetManager.addRecentWorkingSet(fWorkingSet);
        fWorkingSetManager.addWorkingSet(fWorkingSet);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { fWorkingSet },
                fWorkingSetManager.getRecentWorkingSets()));

        IWorkingSet workingSet2 = fWorkingSetManager.createWorkingSet(
                WORKING_SET_NAME_2, new IAdaptable[] { fWorkspace.getRoot() });
        fWorkingSetManager.addRecentWorkingSet(workingSet2);
        fWorkingSetManager.addWorkingSet(workingSet2);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { workingSet2,
                fWorkingSet }, fWorkingSetManager.getRecentWorkingSets()));

        fWorkingSetManager.removeWorkingSet(workingSet2);
        assertTrue(ArrayUtil.equals(new IWorkingSet[] { fWorkingSet },
                fWorkingSetManager.getRecentWorkingSets()));
    }

    public void testRecentWorkingSetsLength() throws Throwable {
        int oldMRULength =  fWorkingSetManager.getRecentWorkingSetsLength();
        try {
	        fWorkingSetManager.setRecentWorkingSetsLength(10);

	        IWorkingSet[] workingSets = new IWorkingSet[10];
	        for (int i = 0 ; i < 10; i++) {
	            IWorkingSet workingSet = fWorkingSetManager.createWorkingSet(
	                    "ws_" + Integer.toString(i + 1), new IAdaptable[] { fWorkspace.getRoot() });
	            fWorkingSetManager.addRecentWorkingSet(workingSet);
	            fWorkingSetManager.addWorkingSet(workingSet);
	            workingSets[9 - i] = workingSet;
	        }
	        assertTrue(ArrayUtil.equals(workingSets, fWorkingSetManager.getRecentWorkingSets()));

	        fWorkingSetManager.setRecentWorkingSetsLength(7);
	        IWorkingSet[] workingSets7 = new IWorkingSet[7];
	        System.arraycopy(workingSets, 0, workingSets7, 0, 7);
	        assertTrue(ArrayUtil.equals(workingSets7, fWorkingSetManager.getRecentWorkingSets()));

	        fWorkingSetManager.setRecentWorkingSetsLength(9);
	        IWorkingSet[] workingSets9 = new IWorkingSet[9];
	        System.arraycopy(workingSets, 0, workingSets9, 2, 7);

	        for (int i = 7 ; i < 9; i++) {
	            IWorkingSet workingSet = fWorkingSetManager.createWorkingSet(
	                    "ws_addded_" + Integer.toString(i + 1), new IAdaptable[] { fWorkspace.getRoot() });
	            fWorkingSetManager.addRecentWorkingSet(workingSet);
	            fWorkingSetManager.addWorkingSet(workingSet);
	            workingSets9[8 - i] = workingSet;
	        }

	        assertTrue(ArrayUtil.equals(workingSets9, fWorkingSetManager.getRecentWorkingSets()));
        } finally {
        	if (oldMRULength > 0) {
