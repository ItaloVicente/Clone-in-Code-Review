		assertTrue(filterName + "not selecting by severity", filter
				.getSelectBySeverity());
		assertTrue(filterName + "should be on error",
				filter.getSeverity() == ProblemFilter.SEVERITY_ERROR);
		assertTrue(filterName + "should be on any",
				filter.getOnResource() == MarkerFilter.ON_ANY);
