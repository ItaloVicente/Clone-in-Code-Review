    /**
     * Populate the category activity binding definitions.
     *
     */
    private void populateCategoryActivityBindingDefinitions() {
        int counter = 1;
        for (int index = 1; index <= categoryDefinitions.size(); index++) {
            categoryActivityBindingDefinitions
                    .add(new CategoryActivityBindingDefinition(
                            "org.eclipse.activity" + Integer.toString(counter), //$NON-NLS-1$
                            "org.eclipse.category" + Integer.toString(index), //$NON-NLS-1$
                            sourceId));
            counter++;
            categoryActivityBindingDefinitions
                    .add(new CategoryActivityBindingDefinition(
                            "org.eclipse.activity" + Integer.toString(counter), //$NON-NLS-1$
                            "org.eclipse.category" + Integer.toString(index), //$NON-NLS-1$
                            sourceId));
            counter++;
            categoryActivityBindingDefinitions
                    .add(new CategoryActivityBindingDefinition(
                            "org.eclipse.activity" + Integer.toString(counter), //$NON-NLS-1$
                            "org.eclipse.category" + Integer.toString(index), //$NON-NLS-1$
                            sourceId));
            counter++;
        }
    }
