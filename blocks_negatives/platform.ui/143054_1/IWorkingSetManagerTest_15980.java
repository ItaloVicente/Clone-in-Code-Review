    	IWorkingSet [] newSets = WorkingSetConfigurationBlock.filter(sets, setIds);
    	assertEquals(sets.length, newSets.length);

    	for (String setId : setIds) {
    		newSets = WorkingSetConfigurationBlock.filter(sets, new String [] {setId});
    		assertEquals(3, newSets.length);
    		assertEquals(setId, newSets[0].getId());
    		assertEquals(setId, newSets[1].getId());
    		assertEquals(setId, newSets[2].getId());
