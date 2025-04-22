    @Override
	public void handleEvent(Event event) {
        if (event.widget == addResourceTypeButton) {
            promptForResourceType();
        } else if (event.widget == removeResourceTypeButton) {
            removeSelectedResourceType();
        } else if (event.widget == addEditorButton) {
            promptForEditor();
        } else if (event.widget == removeEditorButton) {
            removeSelectedEditor();
        } else if (event.widget == defaultEditorButton) {
            setSelectedEditorAsDefault();
        } else if (event.widget == resourceTypeTable) {
            fillEditorTable();
        }

        updateEnabledState();

    }

