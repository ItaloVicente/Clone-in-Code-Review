                .getActivityPatternBindings();
        assertTrue(!activityPatternBindings.isEmpty());
        IActivityPatternBinding activityPatternBinding = (IActivityPatternBinding) activityPatternBindings
                .toArray()[0];
        assertTrue(activityPatternBinding.getActivityId().equals(
                first_activity.getId()));
        assertTrue(activityPatternBinding.getPattern().pattern().equals(
                "org.eclipse.pattern1"));
        try {
            assertTrue(first_activity.getDescription().equals("description"));
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
        assertTrue(first_activity.getId().equals("org.eclipse.activity1"));
        try {
            assertTrue(first_activity.getName().equals("Activity 1"));
        } catch (NotDefinedException e) {
            e.printStackTrace();
        }
    }
