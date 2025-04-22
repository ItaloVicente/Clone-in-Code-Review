		for (int i = 0; i < allFilterNames.length; i++) {
			ProblemFilter filter = getFilter(allFilterNames[i]);
			if(filteredOut)
				assertNull("Should filter out " + allFilterNames[i] ,filter);
			else{
			assertNotNull("No filter for " + allFilterNames[i] ,filter);
			assertTrue(allFilterNames[i] + failureMessage, filter.isFilteredOutByActivity() == filteredOut);
