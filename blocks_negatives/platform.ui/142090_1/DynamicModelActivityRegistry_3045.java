    /**
     * Populate the activity definitions.
     *
     */
    private void populateActivityDefinitions() {
        String stringToAppend = null;
        for (int index = 1; index <= categoryDefinitions.size() * 3; index++) {
            stringToAppend = Integer.toString(index);
            activityDefinitions.add(new ActivityDefinition(
                    "org.eclipse.activity" + stringToAppend, "Activity " //$NON-NLS-1$ //$NON-NLS-2$
                            + stringToAppend, sourceId, "description")); //$NON-NLS-1$
        }
    }
