        copySet.remove(enabledIdentifier.getActivityIds().toArray()[0]);
        activityManager.setEnabledActivityIds(copySet);
        assertTrue(listenerType == -1);
        listenerType = 0;
        activityManager.setEnabledActivityIds(copySet);
        assertTrue(listenerType == -1);
        listenerType = 1;
        fixedModelRegistry.addActivityPatternBinding("org.eclipse.activity1", //$NON-NLS-1$
        assertTrue(listenerType == -1);
