		projectNames.getTable().setEnabled(!alwaysCleanButton.getSelection());
		selectAllButton.setEnabled(!alwaysCleanButton.getSelection());
		deselectAllButton.setEnabled(!alwaysCleanButton.getSelection());
		filterText.setEnabled(!alwaysCleanButton.getSelection());

		boolean enabled = selection.length > 0 || alwaysCleanButton.getSelection();
