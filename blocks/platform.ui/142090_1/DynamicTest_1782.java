		copySet.remove(enabledIdentifier.getActivityIds().toArray()[0]);
		activityManager.setEnabledActivityIds(copySet);
		assertTrue(listenerType == -1);
		listenerType = 0;
		copySet.add("org.eclipse.activity3"); //$NON-NLS-1$
		activityManager.setEnabledActivityIds(copySet);
		assertTrue(listenerType == -1);
		listenerType = 1;
		fixedModelRegistry.addActivityPatternBinding("org.eclipse.activity1", //$NON-NLS-1$
				"org.eclipse.pattern3"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(listenerType == -1);
