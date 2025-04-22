		for (String allFilterName : allFilterNames) {
			ProblemFilter filter = getFilter(allFilterName);
			if(filteredOut) {
				assertNull("Should filter out " + allFilterName ,filter);
			} else{
			assertNotNull("No filter for " + allFilterName ,filter);
			assertTrue(allFilterName + failureMessage, filter.isFilteredOutByActivity() == filteredOut);
