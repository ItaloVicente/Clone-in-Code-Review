        int selectedResourceCount = selections.size();
        if (selectedResourceCount == 1) {
            IResource resource = (IResource) selections.get(0);
            setResourceToDisplay(resource);
        } else {
            selectedResources = selections;
            exportAllTypesRadio.setSelection(true);
            exportSpecifiedTypesRadio.setSelection(false);
            resourceNameField.setText(CURRENT_SELECTION);
            exportCurrentSelection = true;
            displayResourcesSelectedCount(selectedResourceCount);
        }
    }

    /**
     * Updates the enablements of this page's controls. Subclasses may extend.
     */
    @Override
