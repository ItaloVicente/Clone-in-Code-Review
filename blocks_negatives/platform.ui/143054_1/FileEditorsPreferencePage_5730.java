        }
    }

    /**
     * Update the enabled state.
     */
    public void updateEnabledState() {
    	int selectedResources = resourceTypeTable.getSelectionCount();
        int selectedEditors = editorTable.getSelectionCount();

        removeResourceTypeButton.setEnabled(selectedResources != 0);
