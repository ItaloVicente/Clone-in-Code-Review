        activityManager.setEnabledActivityIds(enabledSet);
        assertTrue(listenerType == -1);
        listenerType = 2;
        activityManager.setEnabledActivityIds(enabledSet);
        assertTrue(listenerType == -1);
        listenerType = 3;
        fixedModelRegistry.addCategory("org.eclipse.category7", "Category 7"); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue(listenerType == -1);
        listenerType = 3;
        fixedModelRegistry.removeCategory("org.eclipse.category7", //$NON-NLS-1$
        assertTrue(listenerType == -1);
        listenerType = 4;
        fixedModelRegistry.addActivity("org.eclipse.activity19", "Activity 19"); //$NON-NLS-1$ //$NON-NLS-2$
        assertTrue(listenerType == -1);
        listenerType = 4;
        fixedModelRegistry.removeActivity("org.eclipse.activity19", //$NON-NLS-1$
        assertTrue(listenerType == -1);
    }

    /**
     * Test the activity listener.
     *
     */
    public void testActivityListener() {
        final IActivity activity_to_listen = activityManager
