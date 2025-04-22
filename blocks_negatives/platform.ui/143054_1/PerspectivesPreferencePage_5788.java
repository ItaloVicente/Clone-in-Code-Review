        TableItem item = new TableItem(perspectivesTable, SWT.NULL, index);
        if (image != null) {
            Descriptors.setImage(item, image);
        }
        String label=persp.getLabel();
        if (persp.getId().equals(defaultPerspectiveId)){
			label = NLS.bind(WorkbenchMessages.PerspectivesPreference_defaultLabel, label );
