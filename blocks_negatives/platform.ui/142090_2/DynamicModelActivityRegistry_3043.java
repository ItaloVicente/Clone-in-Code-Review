    /**
     * Populate the activity pattern binding definitions.
     *
     */
    private void populateActivityPatternBindingDefinitions() {
        for (int index = 0; index < activityDefinitions.size(); index++) {
            activityPatternBindingDefinitions
                    .add(new ActivityPatternBindingDefinition(
                            ((ActivityDefinition) activityDefinitions.toArray()[index])
                                    .getId(), "org.eclipse.pattern" //$NON-NLS-1$
                                    + Integer.toString(index + 1), sourceId));
        }
    }
