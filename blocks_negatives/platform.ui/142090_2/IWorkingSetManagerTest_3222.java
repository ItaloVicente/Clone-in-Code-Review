        resetChangeData();
        fWorkingSetManager.removeWorkingSet(fWorkingSet);
        assertEquals(IWorkingSetManager.CHANGE_WORKING_SET_REMOVE,
                fChangeProperty);
        assertEquals(fWorkingSet, fChangeOldValue);
        assertEquals(null, fChangeNewValue);
