				"org.eclipse.pattern3").getActivityIds(); //$NON-NLS-1$
		assertTrue(manipulatedIdentifiers.size() == 2);
		listenerType = 1;
		fixedModelRegistry.removeActivityPatternBinding("org.eclipse.pattern3"); //$NON-NLS-1$
		assertTrue(listenerType == -1);
		manipulatedIdentifiers = activityManager.getIdentifier(
				"org.eclipse.pattern3").getActivityIds(); //$NON-NLS-1$
		assertTrue(manipulatedIdentifiers.size() == 1);
	}

	public void testActivityManagerListener() {
