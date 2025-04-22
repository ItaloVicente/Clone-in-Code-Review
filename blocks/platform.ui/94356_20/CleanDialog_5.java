		createClearTextNew(filterTextArea);
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(false);
		}

		createProjectSelectionTable(area);
		if (!alwaysCleanButton.getSelection()) {
			setInitialFilterText();
		}
