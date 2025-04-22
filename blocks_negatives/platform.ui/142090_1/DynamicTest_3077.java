        enabledSet.add(activity_to_listen.getId());
        activityManager.setEnabledActivityIds(enabledSet);
        assertTrue(listenerType == -1);
        listenerType = 6;
        enabledSet.remove(activity_to_listen.getId());
        activityManager.setEnabledActivityIds(enabledSet);
        assertTrue(listenerType == -1);
        listenerType = 8;
        fixedModelRegistry.addActivityPatternBinding("org.eclipse.activity18", //$NON-NLS-1$
        assertTrue(listenerType == -1);
        listenerType = 8;
        assertTrue(listenerType == -1);
        listenerType = 9;
        fixedModelRegistry.addActivityRequirementBinding(
                "org.eclipse.activity9", //$NON-NLS-1$
        assertTrue(listenerType == -1);
        listenerType = 9;
        fixedModelRegistry.removeActivityRequirementBinding(
                "org.eclipse.activity9", activity_to_listen.getId());//$NON-NLS-1$
        assertTrue(listenerType == -1);
        listenerType = 7;
        fixedModelRegistry.updateActivityName(activity_to_listen.getId(),
        assertTrue(listenerType == -1);
        listenerType = 10;
        fixedModelRegistry.updateActivityDescription(
                activity_to_listen.getId(), "description_change"); //$NON-NLS-1$
        assertTrue(listenerType == -1);

        listenerType = DEFAULT_ENABLED_CHANGED;
        fixedModelRegistry.addDefaultEnabledActivity(activity_to_listen.getId());
        assertTrue(listenerType == -1);
        try {
            assertTrue(activity_to_listen.isDefaultEnabled());
        } catch (NotDefinedException e1) {
            fail(e1.getMessage());
        }

        listenerType = DEFAULT_ENABLED_CHANGED;
        fixedModelRegistry.removeDefaultEnabledActivity(activity_to_listen.getId());
        assertTrue(listenerType == -1);
        try {
            assertFalse(activity_to_listen.isDefaultEnabled());
        } catch (NotDefinedException e1) {
            fail(e1.getMessage());
        }
    }

    /**
     * Test the category listener.
     *
     */
    public void testCategoryListener() {
        final ICategory category_to_listen = activityManager
                .getCategory((String) activityManager.getDefinedCategoryIds()
                        .toArray()[0]);
