		activityManager.setEnabledActivityIds(copySet);
		compareSet = activityManager.getEnabledActivityIds();
		assertTrue(compareSet.size() == copySet.size());
		copySet.remove(activityManager.getDefinedActivityIds().toArray()[0]);
		activityManager.setEnabledActivityIds(copySet);
		compareSet = activityManager.getEnabledActivityIds();
		assertTrue(compareSet.size() == copySet.size());
	}

	public void testIdentifiersListener() {
		final IIdentifier enabledIdentifier = activityManager
				.getIdentifier("org.eclipse.pattern3"); //$NON-NLS-1$
		assertTrue(enabledIdentifier.isEnabled());
