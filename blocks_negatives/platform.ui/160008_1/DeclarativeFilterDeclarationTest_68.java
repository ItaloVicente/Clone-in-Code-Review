		assertTrue(filterName + "not selecting by severity", filter
				.getSelectBySeverity());
		assertTrue(filterName + "should be on info",
				filter.getSeverity() == ProblemFilter.SEVERITY_INFO);
		assertTrue(filterName + "should be on selected and children", filter
				.getOnResource() == MarkerFilter.ON_SELECTED_AND_CHILDREN);
