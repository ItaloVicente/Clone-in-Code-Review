                .getActivityPatternBindings();
        fixedModelRegistry.addActivityPatternBinding(first_activity.getId(),
                pattern);
        assertFalse(initialPatternBindings.size() == first_activity
                .getActivityPatternBindings().size());
        fixedModelRegistry.removeActivityPatternBinding(pattern);
        assertTrue(initialPatternBindings.size() == first_activity
                .getActivityPatternBindings().size());
    }

    /**
     * Test the enabled activities.
     *
     */
    public void testEnabledActivities() {
