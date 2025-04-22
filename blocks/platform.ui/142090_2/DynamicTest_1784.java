		enabledSet.add("org.eclipse.activity19"); //$NON-NLS-1$
		activityManager.setEnabledActivityIds(enabledSet);
		assertTrue(listenerType == -1);
		listenerType = 2;
		enabledSet.remove("org.eclipse.activity19"); //$NON-NLS-1$
		activityManager.setEnabledActivityIds(enabledSet);
		assertTrue(listenerType == -1);
		listenerType = 3;
		fixedModelRegistry.addCategory("org.eclipse.category7", "Category 7"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(listenerType == -1);
		listenerType = 3;
		fixedModelRegistry.removeCategory("org.eclipse.category7", //$NON-NLS-1$
				"Category 7"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(listenerType == -1);
		listenerType = 4;
		fixedModelRegistry.addActivity("org.eclipse.activity19", "Activity 19"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(listenerType == -1);
		listenerType = 4;
		fixedModelRegistry.removeActivity("org.eclipse.activity19", //$NON-NLS-1$
				"Activity 19"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(listenerType == -1);
	}

	public void testActivityListener() {
		final String activity_to_listen_name = "Activity 18"; //$NON-NLS-1$
		final IActivity activity_to_listen = activityManager
				.getActivity("org.eclipse.activity18"); //$NON-NLS-1$
