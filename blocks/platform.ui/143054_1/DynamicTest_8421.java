				.getActivityPatternBindings();
		String pattern = "org\\.eclipse\\.ui\\.myPattern/.*"; //$NON-NLS-1$
		fixedModelRegistry.addActivityPatternBinding(first_activity.getId(),
				pattern);
		assertFalse(initialPatternBindings.size() == first_activity
				.getActivityPatternBindings().size());
		fixedModelRegistry.removeActivityPatternBinding(pattern);
		assertTrue(initialPatternBindings.size() == first_activity
				.getActivityPatternBindings().size());
	}

	public void testEnabledActivities() {
