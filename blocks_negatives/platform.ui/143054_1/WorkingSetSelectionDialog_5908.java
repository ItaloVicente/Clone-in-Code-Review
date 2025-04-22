        Control control = super.createContents(parent);
        List selections = getInitialElementSelections();
        if (!selections.isEmpty()) {
            listViewer.setSelection(new StructuredSelection(selections), true);
        }
        updateButtonAvailability();
        return control;
    }

    /**
     * Returns the selected working sets.
     *
     * @return the selected working sets
     */
    @Override
