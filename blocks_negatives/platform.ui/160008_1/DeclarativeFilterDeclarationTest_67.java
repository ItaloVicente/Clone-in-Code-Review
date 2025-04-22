		assertTrue(filterName + "not selecting by severity", filter
				.getSelectBySeverity());
		assertTrue(filterName + "should be on warning",
				filter.getSeverity() == ProblemFilter.SEVERITY_WARNING);
		assertTrue(filterName + "should be on selected only", filter
				.getOnResource() == MarkerFilter.ON_SELECTED_ONLY);
