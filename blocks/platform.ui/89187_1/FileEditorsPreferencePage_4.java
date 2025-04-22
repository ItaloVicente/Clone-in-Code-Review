        Listener resourceTypeTableListener = event -> {
            fillEditorTable();
            updateEnabledState();
		};
		resourceTypeTable.addListener(SWT.Selection, resourceTypeTableListener);
		resourceTypeTable.addListener(SWT.DefaultSelection, resourceTypeTableListener);
