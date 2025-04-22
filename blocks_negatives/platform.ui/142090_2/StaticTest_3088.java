                .getActivityRequirementBindings();
        for (int index = 2; index <= 7; index++) {
            assertTrue(activityRequirementBindings
                    .contains(new ActivityRequirementBinding(
                            "org.eclipse.activity" + Integer.toString(index),
                            "org.eclipse.activity1")));
        }
